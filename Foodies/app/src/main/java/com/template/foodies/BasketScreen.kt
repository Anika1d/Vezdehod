package com.template.foodies

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.template.foodies.model.FoodsApp
import com.template.foodies.ui.theme.GreyCard
import com.template.foodies.ui.theme.Orange

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun BasketScreen() {
    Column(
        Modifier.background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CenterAlignedTopAppBar(
            title = { Text(text = "Корзина") },
            navigationIcon = {
                IconButton(
                    onClick = { /*TODO*/ },
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron_left),
                        contentDescription = "chevron"
                    )

                }
            }
        )
        Divider(color = Color.Gray, thickness = 1.dp)
        val listSelectedFoods = emptyList<FoodsApp>().toMutableList()
        listSelectedFoods.add(
            FoodsApp(
                id = 1,
                name = "Kartoshka",
                "zhrfdsfposfopsopf",
                "1.png",
                1500,
                1901,
                1,
                10,
                "г",
                (10 / 13).toFloat(),
                (10 / 13).toFloat(),
                (10 / 13).toFloat(),
                (10 / 13).toFloat(),
                listOf(1, 2),
                isSelected = mutableStateOf(true),
                countSelected = mutableStateOf(1)
            ),
        )
        listSelectedFoods.add(
            FoodsApp(
                id = 1,
                name = "Kartoshka",
                "zhrfdsfposfopsopf",
                "1.png",
                1500,
                1901,
                1,
                10,
                "г",
                (10 / 13).toFloat(),
                (10 / 13).toFloat(),
                (10 / 13).toFloat(),
                (10 / 13).toFloat(),
                listOf(1, 2),
                isSelected = mutableStateOf(true),
                countSelected = mutableStateOf(1)
            ),
        )
        var summa by remember {
            mutableStateOf(
                0
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.9f)
        ) {
            items(listSelectedFoods) { food ->
                Row(
                    Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight(1f)
                            .fillMaxWidth(0.2f),
                        painter = painterResource(id = R.drawable.photo1),
                        contentDescription = "product"
                    )
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Text(
                                text = food.name,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }
                        Row(
                            Modifier.fillMaxHeight(1f).fillMaxWidth(1f).padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom,
                        ) {
                            IconButton(
                                modifier = Modifier
                                    .fillMaxHeight(0.8f)
                                    .fillMaxWidth(0.16f)
                                    .padding(4.dp)
                                    .clip(shape = RoundedCornerShape(40)),
                                colors = IconButtonDefaults.iconButtonColors(Color(0x14747480)),
                                onClick = {
                                    if (food.countSelected.value != 0) {
                                        food.countSelected.value--
                                        summa -= food.price_current
//                                        viewModel.updateSelectedFood(
//                                            it.id,
//                                            it.countSelected.value
//                                        )
                                    } else {
                                        food.isSelected.value = false
                                        // viewModel.deleteSelectedFood(it.id)
                                    }
                                },
                            ) {
                                Icon(

                                    painter = painterResource(id = R.drawable.ic_minus),
                                    contentDescription = "minus_orange",
                                    tint = Orange
                                )
                            }
                            Box(modifier = Modifier.fillMaxHeight(1f), contentAlignment = Alignment.BottomCenter){
                                Text(
                                    text = food.countSelected.value.toString(),
                                    fontSize = 36.sp,
                                )
                            }
                            IconButton(
                                modifier = Modifier
                                    .fillMaxHeight(0.8f)
                                    .fillMaxWidth(0.20f)
                                    .padding(4.dp)
                                    .clip(shape = RoundedCornerShape(45)),
                                colors = IconButtonDefaults.iconButtonColors(GreyCard),
                                onClick = {
                                    food.countSelected.value++
                                    summa += food.price_current
//                                    viewModel.updateSelectedFood(
//                                        it.id,
//                                        it.countSelected.value
//                                    )
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_plus),
                                    contentDescription = "plus_orange",
                                    tint = Orange
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(text = "${food.price_current}", fontSize = 17.sp)
                                Text(
                                    text = "${food.price_old}",
                                    fontSize = 14.sp,
                                    textDecoration = TextDecoration.LineThrough
                                )


                            }
                        }
                    }
                }
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(4.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Orange)
            ) {
                Text(text = "Заказать за ${summa}")
            }
        }
    }
}
