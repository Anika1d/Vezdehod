package com.template.foodies

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import com.template.foodies.model.CategoriesFoodsApp
import com.template.foodies.model.FoodsApp
import com.template.foodies.model.Tags
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private lateinit var listCategories: List<CategoriesFoodsApp>
    private lateinit var listTags: List<MutableState<Tags>>
    private lateinit var listAllFoods: List<FoodsApp>
    private var selectedCategoriesId: MutableState<Int>
    private var selectedListFoods = emptyList<MutableState<FoodsApp>>().toMutableList()
    private val summaFoods = mutableStateOf(0)
    private val deleteFoods = mutableStateOf(false)
    init {
        val context = app.applicationContext
        initJson(context)
        selectedCategoriesId = mutableStateOf(
            listCategories[0].id
        )
        listCategories[0].isSelected.value = true
    }
    fun init() {
        selectedCategoriesId = mutableStateOf(
            listCategories[0].id
        )
       summaFoods.value = 0
         deleteFoods.value=false
        selectedListFoods = emptyList<MutableState<FoodsApp>>().toMutableList()
        listCategories[0].isSelected.value = true
    }
    fun getSelectedFoods(): MutableList<MutableState<FoodsApp>> {
        if (summaFoods.value==0)
            return emptyList<MutableState<FoodsApp>>().toMutableList()
       else
           return selectedListFoods
    }

    fun updateSUMM(summa: Int) {
        summaFoods.value = summa
    }

    private fun updateDeleteFoods(boolean: Boolean) {
        deleteFoods.value = boolean
    }

    fun getDeleteFoods(): Boolean {
        return deleteFoods.value
    }

    fun updateSelectedFood(id: Int, count: Int) {
        for (i in 0 until selectedListFoods.size) {
            if (selectedListFoods[i].value.id == id) {
                selectedListFoods[i].value.countSelected = mutableStateOf(count)
                break
            }
        }
    }

    fun addSelectedFood(foodsApp: FoodsApp) {
        selectedListFoods.add(mutableStateOf(foodsApp))
    }

    fun deleteSelectedFood(id: Int) {
        for (i in 0 until selectedListFoods.size) {
            if (selectedListFoods[i].value.id == id) {
                selectedListFoods.removeAt(i)
                for (j in 0 until listAllFoods.size) {
                    if (listAllFoods[j].id == id) {
                        listAllFoods[j].isSelected.value = false
                        updateDeleteFoods(true)
                        break
                    }
                }
            }
        }
    }


    fun getListCategories(): List<CategoriesFoodsApp> {
        return listCategories
    }

    fun getListFood(): MutableList<FoodsApp> {
        return listAllFoods.toMutableList()
    }

    fun getListTags(): List<MutableState<Tags>> {
        return listTags
    }

    fun getListFoodOnCategories(): MutableList<FoodsApp> {
        val listTmp = emptyList<FoodsApp>().toMutableList()
        for (element in listAllFoods)
            if (selectedCategoriesId.value == element.category_id)
                listTmp.add(element)
        updateDeleteFoods(false)
        return listTmp.toMutableList()
    }

    fun changeIdSelectedCategories(id: Int) {
        selectedCategoriesId.value = id
    }

    private fun initJson(context: Context) {
        try {
            val mutableListTags = emptyList<MutableState<Tags>>().toMutableList()
            val mutableListCategories = emptyList<CategoriesFoodsApp>().toMutableList()
            val mutableListFoods = emptyList<FoodsApp>().toMutableList()
            val inputStreamCategories = context.assets.open("categories.json")
            val inputStreamFoods = context.assets.open("products.json")
            val inputStreamTags = context.assets.open("tags.json")
            val sizeTags = inputStreamTags.available()
            val bufferTags = ByteArray(sizeTags)
            inputStreamTags.read(bufferTags)
            inputStreamTags.close()
            val jsonTags = String(bufferTags)
            val jsonArrayTags = JSONArray(jsonTags)
            for (i in 0 until jsonArrayTags.length()) {
                val jsonObjectTags = jsonArrayTags.getJSONObject(i)
                val tags = Tags(
                    id = jsonObjectTags.getString("id").toInt(),
                    name = jsonObjectTags.getString("name")
                )
                mutableListTags.add(mutableStateOf(tags))
            }
            val sizeCategories = inputStreamCategories.available()
            val bufferCategories = ByteArray(sizeCategories)
            inputStreamCategories.read(bufferCategories)
            inputStreamCategories.close()
            val jsonCategories = String(bufferCategories)
            val jsonArrayCategories = JSONArray(jsonCategories)
            for (i in 0 until jsonArrayCategories.length()) {
                val jsonObjectCategories = jsonArrayCategories.getJSONObject(i)
                val categories = CategoriesFoodsApp(
                    id = jsonObjectCategories.getString("id").toInt(),
                    name = jsonObjectCategories.getString("name")
                )
                mutableListCategories.add(categories)
            }
            val sizeFood = inputStreamFoods.available()
            val bufferFoods = ByteArray(sizeFood)
            inputStreamFoods.read(bufferFoods)
            inputStreamFoods.close()
            val jsonFoods = String(bufferFoods)
            val jsonArrayFoods = JSONArray(jsonFoods)
            for (i in 0 until jsonArrayFoods.length()) {
                val jsonObjectFoods = jsonArrayFoods.getJSONObject(i)
                val po = jsonObjectFoods.getString("price_old")
                val tagArray = jsonObjectFoods.getString("tag_ids")
                val tmpTagsList = emptyList<Int>().toMutableList()
                tagArray.forEach {
                    val tmp = it.digitToIntOrNull()
                    if (tmp != null) {
                        tmpTagsList.add(tmp)
                    }
                }
                val foods = FoodsApp(
                    id = jsonObjectFoods.getString("id").toInt(),
                    name = jsonObjectFoods.getString("name"),
                    description = jsonObjectFoods.getString("description"),
                    image = jsonObjectFoods.getString("image"),
                    price_current = jsonObjectFoods.getString("price_current").toInt(),
                    price_old = if (po != "null") po.toInt() else null,
                    category_id = jsonObjectFoods.getString("category_id").toInt(),
                    measure = jsonObjectFoods.getString("measure").toInt(),
                    measure_unit = jsonObjectFoods.getString("measure_unit"),
                    energy_per_100_grams = jsonObjectFoods.getString("energy_per_100_grams")
                        .toFloat(),
                    proteins_per_100_grams = jsonObjectFoods.getString("proteins_per_100_grams")
                        .toFloat(),
                    fats_per_100_grams = jsonObjectFoods.getString("fats_per_100_grams").toFloat(),
                    carbohydrates_per_100_grams = jsonObjectFoods.getString("carbohydrates_per_100_grams")
                        .toFloat(),
                    tag_ids = tmpTagsList.toList(),
                )
                mutableListFoods.add(foods)
            }
            listTags = mutableListTags.toList()
            listAllFoods = mutableListFoods.toList()
            listCategories = mutableListCategories.toList()
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getSumm(): MutableState<Int> {
        return summaFoods
    }


}