package com.simi.shelfpal.home.models

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class Filter {
    data class Category(
        val title: String,
        val leftIcon: DrawableResource,
        val rightIcon: DrawableResource? = null,
        val selected: Boolean = false,
        val onClick: () -> Unit = {}
    ) : Filter()

    data class Month(
        val title: String,
        val rightIcon: DrawableResource? = null,
        val selected: Boolean = false,
        val onClick: () -> Unit = {}
    ) : Filter()

    data class Year(
        val title: String,
        val rightIcon: DrawableResource? = null,
        val selected: Boolean = false,
        val onClick: () -> Unit = {}
    ) : Filter()
}