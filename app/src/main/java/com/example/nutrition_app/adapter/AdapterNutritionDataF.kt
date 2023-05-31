package com.example.nutrition_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrition_app.data.NutritionDataF
import com.example.nutrition_app.databinding.ItemBinding
import java.util.ArrayList

class AdapterNutritionDataF(private val nutritionDataList: ArrayList<NutritionDataF>) :
    RecyclerView.Adapter<AdapterNutritionDataF.MyViewHolder>() {

    class MyViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val tvNameFood = binding.foodName
        val tvCal = binding.textCalories
        val tvCarb = binding.textCarb
        val tvFat = binding.textFat
        val tvProtein = binding.textProtein
        val tvDateTime = binding.textDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = nutritionDataList[position]
        holder.tvNameFood.text = currentItem.foodName
        holder.tvCal.text = currentItem.calories
        holder.tvCarb.text = currentItem.carbs
        holder.tvFat.text = currentItem.fat
        holder.tvProtein.text = currentItem.protein
        holder.tvDateTime.text = currentItem.timestamp
    }

    override fun getItemCount(): Int {
        return nutritionDataList.size
    }

}