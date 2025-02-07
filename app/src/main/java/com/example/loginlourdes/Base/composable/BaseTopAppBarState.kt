package com.example.loginlourdes.Base.composable

import androidx.compose.ui.graphics.vector.ImageVector

data class BaseTopAppBarState (
    val tittle:String,
    val icon:ImageVector,
    val upAction:()->Unit,
    val acctions:List<Actions>

)
data class Actions (
    val nameTitle:String,
    val icon: ImageVector,
    val onCLick:()->Unit,
    val isVisible:Boolean,
    val contentDescription:String
)
