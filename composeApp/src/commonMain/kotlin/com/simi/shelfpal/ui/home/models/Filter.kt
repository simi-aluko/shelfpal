package com.simi.shelfpal.ui.home.models

import org.jetbrains.compose.resources.DrawableResource

sealed class Filter {
    data class Category(
        val title: String,
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

    data class ItemStatus(
        val title: String,
        val rightIcon: DrawableResource? = null,
        val leftIcon: DrawableResource? = null,
        val selected: Boolean = false,
        val onClick: () -> Unit = {}
    ) : Filter()
}