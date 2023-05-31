package com.example.nutrition_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrition_app.R
import com.example.nutrition_app.databinding.ItemMealBinding
import com.example.nutrition_app.data.model.Meal
import com.example.nutrition_app.ui.calculater.BestMealsFragment

class MealAdapter(val list: List<Meal>, private val listener: BestMealsFragment) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val currentMeal = list[position]
        holder.binding.apply {
            textMealName.text = currentMeal.name
            textCaloriesNumber.text = currentMeal.calories.toInt().toString()

            root.setOnClickListener { listener.onClickItem(currentMeal) }
        }
    }

    override fun getItemCount(): Int = list.size

    class MealViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = ItemMealBinding.bind(viewItem)
    }

}