package com.template.foodies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.template.foodies.ui.theme.Orange

@Preview(showBackground = true)
@Composable
fun SplashScreen() {
  //  val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Orange),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(R.drawable.ic_logo_white), contentDescription = "logo")
    }
}


