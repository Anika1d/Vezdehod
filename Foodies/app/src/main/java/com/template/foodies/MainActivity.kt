@file:OptIn(ExperimentalAnimationApi::class, ExperimentalAnimationApi::class)

package com.template.foodies

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.template.foodies.model.FoodsApp
import com.template.foodies.ui.theme.FoodiesTheme
import com.template.foodies.ui.theme.Orange
import com.template.foodies.ui.theme.GreyCard
import com.template.foodies.navigation.Route
import com.template.navigation.StartNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    lateinit var foodAPP: FoodsApp;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodiesTheme {
                val navController = rememberAnimatedNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StartNavigation(navController = navController, this)
                }
            }
        }
    }

    @Composable
    fun SplashAnimation(navController: NavController) {
        LaunchedEffect(key1 = true) {
            delay(1500)
            navController.navigate(Route.Menu.route)
        }
        SplashScreen()
    }

    @Composable
    fun MenuScreen(navController: NavHostController) {
        var listFoods = viewModel.getListFoodOnCategories()
        if (viewModel.getDeleteFoods()) {
            listFoods = viewModel.getListFoodOnCategories()
        }
        val tagFoods = viewModel.getListTags()
        Column(
            modifier = Modifier.padding(16.dp, bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val summa by remember {
                mutableStateOf(viewModel.getSumm())
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = {
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "settings",
                    )
                }
                Image(
                    modifier = Modifier.padding(top = 8.dp),
                    painter = painterResource(id = R.drawable.ic_logo_orange),
                    contentDescription = "logo_menu"
                )
                IconButton(
                    onClick = {
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search",
                    )
                }
            }
            CategoriesFood()
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.90f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                columns = GridCells.Fixed(2),
            ) {
                items(listFoods) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(10))
                            .background(GreyCard)
                    ) {
                        Column() {
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        foodAPP = it
                                        navController.navigate(Route.Product.route)
                                    },
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.photo1),
                                    contentDescription = "avatarFoods ${it.id}"
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .padding(2.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    if (it.price_old != null)
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_discount),
                                            contentDescription = "discount"
                                        )
                                    for (tag in it.tag_ids) {
                                        tagFoods.forEach {
                                            if (tag == it.value.id) {
                                                if (it.value.name == "Острое") {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.ic_sharp),
                                                        contentDescription = "sharp"
                                                    )
                                                } else if (it.value.name == "Вегетарианское блюдо") {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.ic_vegan),
                                                        contentDescription = "vegan"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                color = Color.Black,
                                text = it.name,
                                fontSize = 15.sp
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                color = Color.Gray,
                                text = "${it.measure} ${it.measure_unit}",
                                fontSize = 15.sp
                            )
                            if (it.isSelected.value) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        modifier = Modifier
                                            .fillMaxSize(0.3f)
                                            .padding(4.dp)
                                            .clip(shape = RoundedCornerShape(40))
                                            .shadow(3.dp, shape = RoundedCornerShape(40)),
                                        colors = IconButtonDefaults.iconButtonColors(Color.White),
                                        onClick = {
                                            if (it.countSelected.value != 0) {
                                                it.countSelected.value--
                                                summa.value -= it.price_current
                                                viewModel.updateSelectedFood(
                                                    it.id,
                                                    it.countSelected.value
                                                )
                                            } else {
                                                it.isSelected.value = false
                                                viewModel.deleteSelectedFood(it.id)
                                            }
                                        },
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_minus),
                                            contentDescription = "plus_orange",
                                            tint = Orange
                                        )
                                    }
                                    Text(text = it.countSelected.value.toString())
                                    IconButton(
                                        modifier = Modifier
                                            .fillMaxSize(0.5f)
                                            .padding(4.dp)
                                            .clip(shape = RoundedCornerShape(40))
                                            .shadow(3.dp, shape = RoundedCornerShape(40)),
                                        colors = IconButtonDefaults.iconButtonColors(Color.White),
                                        onClick = {
                                            it.countSelected.value++
                                            summa.value += it.price_current
                                            viewModel.updateSelectedFood(
                                                it.id,
                                                it.countSelected.value
                                            )
                                        },
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_plus),
                                            contentDescription = "plus_orange",
                                            tint = Orange
                                        )
                                    }
                                }
                            } else
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .padding(8.dp),
                                    shape = RoundedCornerShape(30),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                                    onClick = {
                                        viewModel.addSelectedFood(it)
                                        it.isSelected.value = true
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.White)
                                ) {
                                    if (it.price_old != null && it.price_old != it.price_current)
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(4.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceBetween,

                                            ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                contentAlignment = Alignment.BottomStart,
                                            ) {
                                                Text(
                                                    text = "${it.price_current}  ",
                                                    color = Color.Black,
                                                    fontSize = 15.sp
                                                )
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                contentAlignment = Alignment.TopEnd,
                                            ) {
                                                Text(
                                                    text = " ${it.price_old}",
                                                    textDecoration = TextDecoration.LineThrough,
                                                    color = Color.Gray,
                                                    fontSize = 13.sp
                                                )
                                            }
                                        }
                                    else
                                        Text(
                                            text = "${it.price_current}",
                                            color = Color.Black,
                                            fontSize = 17.sp
                                        )
                                }
                        }
                    }
                }
            }
            Button(onClick = {
                navController.navigate(Route.Basket.route)
            }, colors = ButtonDefaults.buttonColors(Orange)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_basket),
                        contentDescription = "basketMenu"
                    )
                    viewModel.updateSUMM(summa.value)
                    if (summa.value != 0)
                        Text(
                            text = "${summa.value}",
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                }
            }
        }

    }

    @Composable
    fun CategoriesFood() {
        val listCategories = viewModel.getListCategories()
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp)
        ) {
            items(
                listCategories
            ) {
                Button(
                    onClick = {
                        for (element in listCategories) {
                            if (it.id == element.id) {
                                element.isSelected.value = true
                                viewModel.changeIdSelectedCategories(it.id)
                            } else element.isSelected.value = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(if (it.isSelected.value) Orange else Color.White)
                ) {
                    Text(
                        maxLines = 1,
                        text = it.name,
                        color = if (it.isSelected.value) Color.White else Color.Black
                    )
                }
            }
        }
    }

    @Composable
    fun ProductScreen(navController: NavHostController) {
        val food = foodAPP
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
                        elevation = ButtonDefaults.buttonElevation(5.dp),
                        onClick = { navController.navigate(Route.Menu.route) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrowback),
                            contentDescription = "arrowBack"
                        )
                    }
                }
                Text(text = food.name, color = Color.Black, fontSize = 28.sp)
                Text(
                    text = food.description,
                    color = Color.Gray, fontSize = 17.sp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 2.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "Вес", color = Color.Gray, fontSize = 17.sp)
                    Text(
                        text = "${food.measure}" + food.measure_unit,
                        color = Color.Black,
                        fontSize = 17.sp
                    )
                }
                Divider(color = Color.Gray, thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 2.dp, bottom = 8.dp),

                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "Энерг. ценность", color = Color.Gray, fontSize = 17.sp)
                    Text(
                        text = "${food.energy_per_100_grams}",
                        color = Color.Black,
                        fontSize = 17.sp
                    )
                }
                Divider(color = Color.Gray, thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 2.dp, bottom = 8.dp),

                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "Белки", color = Color.Gray, fontSize = 17.sp)
                    Text(
                        text = "${food.carbohydrates_per_100_grams}",
                        color = Color.Black,
                        fontSize = 17.sp
                    )
                }
                Divider(color = Color.Gray, thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 2.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "Жиры", color = Color.Gray, fontSize = 17.sp)
                    Text(text = "${food.fats_per_100_grams}", color = Color.Black, fontSize = 17.sp)
                }
                Divider(color = Color.Gray, thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 2.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "Углеводы", color = Color.Gray, fontSize = 17.sp)
                    Text(
                        text = "${food.carbohydrates_per_100_grams}",
                        color = Color.Black,
                        fontSize = 17.sp
                    )
                }
            }
            Button(onClick = {
                food.isSelected = mutableStateOf(true)
                food.countSelected.value += 1
                var d = true
                val f = viewModel.getSelectedFoods()
                for (i in 0 until f.size) {
                    if (f[i].value.id == food.id) {
                        viewModel.updateSelectedFood(food.id, food.countSelected.value)
                        d = false
                    }
                }
                if (d)
                    viewModel.addSelectedFood(food)
                Toast.makeText(
                    applicationContext,
                    "${food.name} добавлена в корзину",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.updateSUMM(viewModel.getSumm().value + food.price_current)
            }, colors = ButtonDefaults.buttonColors(Orange)) {
                Text(text = "Добавить в корзину за ${food.price_current}")

            }
        }
    }

    @Composable
    fun BasketScreen(navController: NavHostController) {
        Column(
            Modifier.background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CenterAlignedTopAppBar(
                title = { Text(text = "Корзина") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Route.Menu.route) },
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_chevron_left),
                            contentDescription = "chevron"
                        )

                    }
                }
            )
            Divider(color = Color.Gray, thickness = 1.dp)
            val listSelectedFoods = viewModel.getSelectedFoods()
            listSelectedFoods.add(
                remember {
                    mutableStateOf(
                        FoodsApp(
                            id = -1,
                            name = "-1",
                            "-1",
                            "-1",
                            -1,
                            -1,
                            -1,
                            -1,
                            "-1",
                            (-1.0).toFloat(),
                            (-1.0).toFloat(),
                            (-1.0).toFloat(),
                            (-1.0).toFloat(),
                            listOf(-1, -2),
                            isSelected =
                            mutableStateOf(true),
                            countSelected =
                            mutableStateOf(-1)

                        )
                    )
                })
            val summa by remember {
                mutableStateOf(
                    viewModel.getSumm()
                )
            }

            if (summa.value == 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Пусто, выберите блюда\n" +
                                "в каталоге :)", fontSize = 17.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                ) {
                    items(listSelectedFoods) { food ->
                        if (food.value.id != -1) {
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
                                            text = food.value.name,
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                    Row(
                                        Modifier
                                            .fillMaxHeight(1f)
                                            .fillMaxWidth(1f)
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Bottom,
                                    ) {
                                        IconButton(
                                            modifier = Modifier
                                                .fillMaxHeight(0.8f)
                                                .fillMaxWidth(0.16f)
                                                .padding(4.dp)
                                                .clip(shape = RoundedCornerShape(40)),
                                            colors = IconButtonDefaults.iconButtonColors(
                                                Color(
                                                    0x14747480
                                                )
                                            ),
                                            onClick = {
                                                if (food.value.countSelected.value != 0) {
                                                    summa.value -= food.value.price_current
                                                    food.value.countSelected.value--
                                                    if (food.value.countSelected.value == 0) {
                                                        for (i in 0 until listSelectedFoods.size) {
                                                            if (listSelectedFoods[i].value.id == food.value.id) {
                                                                listSelectedFoods.removeAt(i)
                                                                viewModel.deleteSelectedFood(
                                                                    food.value.id
                                                                )
                                                               break
                                                            }
                                                        }


                                                    }
                                                }

                                            },
                                        ) {
                                            Icon(

                                                painter = painterResource(id = R.drawable.ic_minus),
                                                contentDescription = "minus_orange",
                                                tint = Orange
                                            )
                                        }
                                        Box(
                                            modifier = Modifier.fillMaxHeight(1f),
                                            contentAlignment = Alignment.BottomCenter
                                        ) {
                                            Text(
                                                text = food.value.countSelected.value.toString(),
                                                fontSize = 36.sp,
                                            )
                                        }
                                        IconButton(
                                            modifier = Modifier
                                                .fillMaxHeight(0.8f)
                                                .fillMaxWidth(0.20f)
                                                .padding(4.dp)
                                                .clip(shape = RoundedCornerShape(45)),
                                            colors = IconButtonDefaults.iconButtonColors(
                                                GreyCard
                                            ),
                                            onClick = {
                                                food.value.countSelected.value++
                                                summa.value += food.value.price_current
                                                viewModel.updateSelectedFood(
                                                    food.value.id,
                                                    food.value.countSelected.value
                                                )
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
                                            Text(
                                                text = "${food.value.price_current}",
                                                fontSize = 17.sp
                                            )
                                            if (food.value.price_old != null && food.value.price_old != food.value.price_current)
                                                Text(
                                                    text = "${food.value.price_old}",
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
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(4.dp),
                        onClick = {
                            viewModel.init()
                            navController.navigate(Route.Clearance.route)
                        },
                        colors = ButtonDefaults.buttonColors(Orange)
                    ) {
                        viewModel.updateSUMM(summa.value)
                        Text(text = "Заказать за ${summa.value}")
                    }
                }
            }
        }
    }

    @Composable
    fun ClearanceScreen(navController: NavHostController) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column() {

                Text(text = "Поздравляем с оформлением заказа, ваш курьер уже в пути")
                Button(onClick = {
                    navController.navigate(Route.Menu.route)
                }) {
                    Text(text = "Меню")
                }
            }
        }
    }
}




