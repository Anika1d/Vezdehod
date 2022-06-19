package com.template.foodies.model

import android.media.Image
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource
import com.template.foodies.R
import org.json.JSONArray


data class FoodsApp(
    val id: Int, // — уникальный идентификатор товара;
    val name: String,// — название товара;
    val description: String,// — описание/состав товара;
    val image: String,// — изображение товара;
    val price_current: Int,// — текущая цена в копейках;
    val price_old: Int?, /// — старая цена в копейках;
    val category_id: Int,// — идентификатор категории товара;
    val measure: Int,// — количество в единицах измерения ниже;
    val measure_unit: String,// — единица измерения;
    val energy_per_100_grams: Float,// — количество калорий на 100 г продукта;
    val proteins_per_100_grams: Float,// — количество белков на 100 г продукта;
    val fats_per_100_grams: Float,// — количество жиров на 100 г продукта;
    val carbohydrates_per_100_grams: Float,// — количество углеводов на 100 г продукта;
    val tag_ids: List<Int>,// — массив атрибутов товара для фильтрации.
    var isSelected: MutableState<Boolean> = mutableStateOf(false),
    var countSelected: MutableState<Int> = mutableStateOf(0),
)
