package com.simi.shelfpal.ui.imagecapture

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.kashif.cameraK.controller.CameraController
import com.kashif.cameraK.enums.CameraLens
import com.kashif.cameraK.enums.Directory
import com.kashif.cameraK.enums.FlashMode
import com.kashif.cameraK.enums.ImageFormat
import com.kashif.cameraK.enums.QualityPrioritization
import com.kashif.cameraK.enums.TorchMode
import com.kashif.cameraK.permissions.Permissions
import com.kashif.cameraK.permissions.providePermissions
import com.kashif.cameraK.result.ImageCaptureResult
import com.kashif.cameraK.ui.CameraPreview
import com.simi.shelfpal.ui.imagecapture.composables.CameraControls
import com.simi.shelfpal.ui.imagecapture.composables.PermissionDenied
import com.simi.shelfpal.ui.shared.IMAGE_CAPTURE_RESULT
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.camera_permission_denied_message
import shelfpal.composeapp.generated.resources.camerapermission
import shelfpal.composeapp.generated.resources.generic_error
import shelfpal.composeapp.generated.resources.storage_permission_denied_message
import shelfpal.composeapp.generated.resources.storagesettings
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ImageCaptureScreen(
    navController: NavController
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) {
        val coroutineScope = rememberCoroutineScope()

        val permissions: Permissions = providePermissions()
        val cameraPermissionState = remember { mutableStateOf(permissions.hasCameraPermission()) }
        val storagePermissionState = remember { mutableStateOf(permissions.hasStoragePermission()) }
        val cameraController = remember { mutableStateOf<CameraController?>(null) }

        val cameraPermissionDeniedMessage = stringResource(Res.string.camera_permission_denied_message)
        val storagePermissionDeniedMessage = stringResource(Res.string.storage_permission_denied_message)
        val permissionDeniedDrawable = remember {
            mutableStateOf(
                if (!permissions.hasCameraPermission()) {
                    Res.drawable.camerapermission
                } else {
                    Res.drawable.storagesettings
                }
            )
        }

        CameraPermissionHandler(
            permissions = permissions,
            cameraPermissionState = cameraPermissionState,
            onCameraPermissionDenied = {
                coroutineScope.launch {
                    permissionDeniedDrawable.value = Res.drawable.camerapermission
                    snackBarHostState.showSnackbar(cameraPermissionDeniedMessage)
                }
            }
        )

        StoragePermissionHandler(
            permissions = permissions,
            storagePermissionState = storagePermissionState,
            cameraPermissionState = cameraPermissionState,
            onStoragePermissionDenied = {
                coroutineScope.launch {
                    permissionDeniedDrawable.value = Res.drawable.storagesettings
                    snackBarHostState.showSnackbar(storagePermissionDeniedMessage)
                }
            }
        )
        savedStateHandle?.let {
            ScreenContent(
                cameraController = cameraController,
                cameraPermissionState = cameraPermissionState.value,
                storagePermissionState = storagePermissionState.value,
                permissionDeniedDrawable = permissionDeniedDrawable.value,
                snackBarHostState = snackBarHostState,
                savedStateHandle = it,
                navController = navController
            )
        }
    }
}

@Composable
private fun ScreenContent(
    cameraController: MutableState<CameraController?>,
    cameraPermissionState: Boolean,
    storagePermissionState: Boolean,
    permissionDeniedDrawable: DrawableResource,
    snackBarHostState: SnackbarHostState,
    savedStateHandle: SavedStateHandle,
    navController: NavController
) {
    if (cameraPermissionState && storagePermissionState) {
        CameraContent(
            cameraController = cameraController,
            snackBarHostState = snackBarHostState,
            savedStateHandle = savedStateHandle,
            navigateBack = {
                navController.popBackStack()
            }
        )
    } else {
        PermissionDenied(
            permissionDeniedDrawable = permissionDeniedDrawable
        )
    }
}

@Composable
private fun CameraContent(
    cameraController: MutableState<CameraController?>,
    snackBarHostState: SnackbarHostState,
    savedStateHandle: SavedStateHandle,
    navigateBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    val genericErrorMessage = stringResource(Res.string.generic_error)

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            cameraConfiguration = {
                setCameraLens(CameraLens.BACK)
                setFlashMode(FlashMode.OFF)
                setImageFormat(ImageFormat.JPEG)
                setDirectory(Directory.PICTURES)
                setTorchMode(TorchMode.OFF)
                setQualityPrioritization(QualityPrioritization.QUALITY)
            },
            onCameraControllerReady = {
                cameraController.value = it
            }
        )

        cameraController.value?.let { controller ->
            CameraControls(
                cameraController = controller,
                imageBitmap = imageBitmap.value,
                onCapture = {
                    coroutineScope.launch {
                        handleImageCapture(
                            cameraController = controller,
                            onErrorOccurred = {
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar(genericErrorMessage)
                                }
                                it.printStackTrace()
                            },
                            onCaptureImageSuccess = {
                                savedStateHandle[IMAGE_CAPTURE_RESULT] = it.byteArray
                                navigateBack()
                            }
                        )
                    }
                },
                onDismissImagePreview = {
                    imageBitmap.value = null
                }
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalUuidApi::class)
private suspend fun handleImageCapture(
    cameraController: CameraController,
    onErrorOccurred: (Exception) -> Unit,
    onCaptureImageSuccess: (ImageCaptureResult.Success) -> Unit
) {
    when (val result = cameraController.takePicture()) {
        is ImageCaptureResult.Success -> onCaptureImageSuccess(result)
        is ImageCaptureResult.Error -> onErrorOccurred(result.exception)
    }
}

@Composable
private fun CameraPermissionHandler(
    permissions: Permissions,
    cameraPermissionState: MutableState<Boolean>,
    onCameraPermissionDenied: () -> Unit,
) {
    if(!cameraPermissionState.value) {
        permissions.RequestCameraPermission(
            onGranted = {
                cameraPermissionState.value = true
            },
            onDenied = onCameraPermissionDenied
        )
    }
}

@Composable
private fun StoragePermissionHandler(
    permissions: Permissions,
    storagePermissionState: MutableState<Boolean>,
    cameraPermissionState: MutableState<Boolean>,
    onStoragePermissionDenied: () -> Unit,
) {
    if(cameraPermissionState.value && !storagePermissionState.value) {
        permissions.RequestStoragePermission(
            onGranted = { storagePermissionState.value = true },
            onDenied = onStoragePermissionDenied
        )
    }
}