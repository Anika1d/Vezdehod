package com.template.foodies

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.template.foodies.ui.theme.Orange

@Preview(showBackground = true)
@Composable
fun ProductScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .fillMaxHeight(0.9f)
                .verticalScroll(ScrollState(0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.photo1),
                    contentDescription = "photoProduct"
                )
                Button(shape = RoundedCornerShape(100),
                    elevation = ButtonDefaults.buttonElevation(5.dp), onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrowback),
                        contentDescription = "arrowBack"
                    )
                }
            }
            Text(text = "Тom ян", color = Color.Black, fontSize = 28.sp)
            Text(
                text = "Кокосовое молоко, кальмары, креветки,\n" +
                        "помидоры черри, грибы вешанки",
                color = Color.Gray, fontSize = 17.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 2.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Вес", color = Color.Gray, fontSize = 17.sp)
                Text(text = "400", color = Color.Black, fontSize = 17.sp)
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 2.dp, bottom = 8.dp),

                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Энерг. ценность", color = Color.Gray, fontSize = 17.sp)
                Text(text = "198,9 ккал", color = Color.Black, fontSize = 17.sp)
            }
            Divider(color = Color.Gray, thickness = 1.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 2.dp, bottom = 8.dp),

                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Белки", color = Color.Gray, fontSize = 17.sp)
                Text(text = "10 г", color = Color.Black, fontSize = 17.sp)
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 2.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Жиры", color = Color.Gray, fontSize = 17.sp)
                Text(text = "8,5 г", color = Color.Black, fontSize = 17.sp)
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 2.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Углеводы", color = Color.Gray, fontSize = 17.sp)
                Text(text = "19,7 г", color = Color.Black, fontSize = 17.sp)
            }
        }
        Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Orange)) {
            Text(text = "Добавить в корзину за ${1200}")
        }
    }
}