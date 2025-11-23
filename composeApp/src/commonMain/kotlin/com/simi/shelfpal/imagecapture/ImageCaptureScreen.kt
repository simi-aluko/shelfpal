package com.simi.shelfpal.imagecapture

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
import com.kashif.imagesaverplugin.ImageSaverConfig
import com.kashif.imagesaverplugin.ImageSaverPlugin
import com.kashif.imagesaverplugin.rememberImageSaverPlugin
import com.simi.shelfpal.imagecapture.composables.CameraControls
import com.simi.shelfpal.imagecapture.composables.PermissionDenied
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.resources.stringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.camera_permission_denied_message
import shelfpal.composeapp.generated.resources.camerapermission
import shelfpal.composeapp.generated.resources.shelfpal
import shelfpal.composeapp.generated.resources.storage_permission_denied_message
import shelfpal.composeapp.generated.resources.storagesettings
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val IMAGE_PREFIX = "ShelfPal"

@Composable
fun ImageCaptureScreen(
    navigateBack: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

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
        val permissionDeniedDrawable = remember { mutableStateOf(Res.drawable.camerapermission) }

        val imageSaverPlugin = rememberImageSaverPlugin(
            config = ImageSaverConfig(
                isAutoSave = false,
                prefix = stringResource(Res.string.shelfpal),
                directory = Directory.DCIM,
                customFolderName = stringResource(Res.string.shelfpal)
            )
        )

        PermissionsHandler(
            permissions = permissions,
            cameraPermissionState = cameraPermissionState,
            storagePermissionState = storagePermissionState,
            onCameraPermissionDenied = {
                coroutineScope.launch {
                    permissionDeniedDrawable.value = Res.drawable.camerapermission
                    snackBarHostState.showSnackbar(cameraPermissionDeniedMessage)
                    navigateBack()
                }
            },
            onStoragePermissionDenied = {
                coroutineScope.launch {
                    permissionDeniedDrawable.value = Res.drawable.storagesettings
                    snackBarHostState.showSnackbar(storagePermissionDeniedMessage)
                    navigateBack()
                }
            }
        )

        ScreenContent(
            cameraController = cameraController,
            imageSaverPlugin = imageSaverPlugin,
            cameraPermissionState = cameraPermissionState.value,
            storagePermissionState = storagePermissionState.value,
            permissionDeniedDrawable = permissionDeniedDrawable.value,
            snackBarHostState = snackBarHostState
        )
    }
}

@Composable
private fun ScreenContent(
    cameraController: MutableState<CameraController?>,
    imageSaverPlugin: ImageSaverPlugin,
    cameraPermissionState: Boolean,
    storagePermissionState: Boolean,
    permissionDeniedDrawable: DrawableResource,
    snackBarHostState: SnackbarHostState
) {
    if (cameraPermissionState && storagePermissionState) {
        CameraContent(
            cameraController = cameraController,
            imageSaverPlugin = imageSaverPlugin,
            snackBarHostState = snackBarHostState
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
    imageSaverPlugin: ImageSaverPlugin,
    snackBarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()
    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

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
                addPlugin(imageSaverPlugin)
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
                            imageSaverPlugin = imageSaverPlugin,
                            onImageCaptured = {
                                imageBitmap.value = it
                            },
                            snackBarHostState = snackBarHostState
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
    imageSaverPlugin: ImageSaverPlugin,
    snackBarHostState: SnackbarHostState,
    onImageCaptured: (ImageBitmap) -> Unit
) {
    when (val result = cameraController.takePicture()) {
        is ImageCaptureResult.Success -> {
            val bitmap = result.byteArray.decodeToImageBitmap()
             onImageCaptured(bitmap)

            if (!imageSaverPlugin.config.isAutoSave) {
                val customName = "${IMAGE_PREFIX}_${Uuid.random().toHexString()}"
                imageSaverPlugin.saveImage(
                    byteArray = result.byteArray,
                    imageName = customName
                )?.let { path ->
                    snackBarHostState.showSnackbar("Image saved at: $path")
                }
            }
        }

        is ImageCaptureResult.Error -> {
            snackBarHostState.showSnackbar("An error occurred. Please try again later.")
            result.exception.printStackTrace()
        }
    }
}


@Composable
private fun PermissionsHandler(
    permissions: Permissions,
    cameraPermissionState: MutableState<Boolean>,
    storagePermissionState: MutableState<Boolean>,
    onCameraPermissionDenied: () -> Unit,
    onStoragePermissionDenied: () -> Unit
) {
    when {
        !cameraPermissionState.value && !storagePermissionState.value -> {
            permissions.RequestCameraPermission(
                onGranted = { cameraPermissionState.value = true },
                onDenied = onCameraPermissionDenied
            )
            permissions.RequestStoragePermission(
                onGranted = { storagePermissionState.value = true },
                onDenied = onStoragePermissionDenied
            )
        }
        !cameraPermissionState.value -> {
            permissions.RequestCameraPermission(
                onGranted = { cameraPermissionState.value = true },
                onDenied = onCameraPermissionDenied
            )
        }
        !storagePermissionState.value -> {
            permissions.RequestStoragePermission(
                onGranted = { storagePermissionState.value = true },
                onDenied = onStoragePermissionDenied
            )
        }
    }
}