package com.template.foodies.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CategoriesFoodsApp(
    val id: Int,
    val name: String,
    var isSelected: MutableState<Boolean> = mutableStateOf(false),
)
