package com.example.nutrition_app.ui.calculater

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nutrition_app.data.model.Meal
import com.example.nutrition_app.databinding.FragmentMealDetailsBinding
import com.example.nutritionapp.databinding.FragmentMealDetailsBinding
import com.example.nutrition_app.util.Constants
import com.github.mikephil.charting.data.*

class MealDetailsFragment : Fragment() {

    lateinit var binding: FragmentMealDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    @SuppressLint("ResourceAsColor")
    fun setUp() {
        val meal = arguments?.getSerializable(Constants.KeyValues.MEAL) as Meal?
        val pieChart = binding.mealLineChart
        val pieData: ArrayList<PieEntry> = ArrayList()
        pieData.add(PieEntry(meal!!.fiber.toFloat()))
        pieData.add(PieEntry(meal.sugars.toFloat()))
        pieData.add(PieEntry(meal.totalFat.toFloat()))
        pieData.add(PieEntry(meal.protein.toFloat()))
        val dataSet = PieDataSet(pieData, "")
        dataSet.sliceSpace = 7f
        val data = PieData(dataSet)
        data.setDrawValues(false)
        pieChart.holeRadius = 90f
        pieChart.setDrawRoundedSlices(true)
        pieChart.description.isEnabled = false    // Hide the description
        pieChart.legend.isEnabled = false
        pieChart.data = data
        pieChart.setHoleColor(0)

        val myColor = intArrayOf(
            Color.rgb(173, 83, 148),
            Color.rgb(133, 182, 255),
            Color.rgb(235, 87, 87),
            Color.rgb(226, 195, 101)
        )
        val colors = ArrayList<Int>()
        for (c in myColor) colors.add(c)
        dataSet.colors = colors

        dataSet.valueTextColor = Color.BLUE
        dataSet.valueTextSize = 20f
        meal.let(this::bindMeal)
    }

    private fun bindMeal(meal: Meal) {
        binding.apply {
            textMealName.text = meal.name
            caloriesValue.text = meal.calories.toInt().toString()
            fabricQuantity.text = meal.fiber.toString()
            sugarQuantity.text = meal.sugars.toString()
            proteinQuantity.text = meal.protein.toString()
            fatQuantity.text = meal.totalFat.toString()
        }
    }
}