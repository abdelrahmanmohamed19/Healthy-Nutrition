package com.example.nutrition_app.ui.calculater

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nutritionapp.R
import com.example.nutritionapp.adapter.MealAdapter
import com.example.nutritionapp.data.dataManager.MealDataManager
import com.example.nutrition_app.data.model.Meal
import com.example.nutritionapp.databinding.FragmentTopMealBinding
import com.example.nutrition_app.util.Calculations
import com.example.nutrition_app.util.Constants
import com.example.nutrition_app.util.enum.StateNavigation

class BestMealsFragment :Fragment() {

    private lateinit var adapter: MealAdapter
    private var mealDataManager: Parcelable = MealDataManager()
    private lateinit var mealsList: MutableList<Meal>
    private val mealDetailsFragment = MealDetailsFragment()
    private val calculations = Calculations()
    private var titleFragment: String = ""


    lateinit var binding : FragmentTopMealBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTopMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()

    }

    private fun changeNavigation(state: StateNavigation, to: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        when (state) {
            StateNavigation.Add -> transaction.add(R.id.fragment_container, to)
            StateNavigation.Remove -> transaction.remove(to)
            StateNavigation.Replace -> transaction.replace(R.id.fragment_container, to)
        }
        transaction.addToBackStack(null).commit()
    }

    protected fun navigationTo(to: Fragment) {
        changeNavigation(StateNavigation.Replace, to)
    }

    protected fun backNavigation(to: Fragment) {
        changeNavigation(StateNavigation.Replace, to)
    }

     fun setUp() {
        mealDataManager = requireNotNull(arguments?.getParcelable(Constants.KeyValues.Meal_DATA_MANAGER))
        mealsList = (mealDataManager as MealDataManager).getMeals()
        val bestMealType =  requireNotNull(arguments?.getString(Constants.KeyValues.BEST_MEAL_TYPE))
        binding.textInfo.text = getString(R.string.diabetics_meals_information)
        var newMealList: MutableList<Meal> = mutableListOf()

        when (bestMealType){
            Constants.KeyValues.DIABETICS -> {
                newMealList = calculations.diabeticsBestMeals(mealsList, 100) as MutableList<Meal>
                setTextBestMealsFragment(R.string.best_diabetes_meals,R.string.info_diabetes_meals)
            }
            Constants.KeyValues.GYM -> {
                newMealList = calculations.bodyBuildingBestMeals(mealsList, 100) as MutableList<Meal>
                setTextBestMealsFragment(R.string.best_bodybuilding_meals,R.string.info_bodybuilding_meals)
            }
            Constants.KeyValues.PRESSURE -> {
                newMealList = calculations.bloodPressureBestMeals(mealsList, 100) as MutableList<Meal>
                setTextBestMealsFragment(R.string.best_blood_pressure_meals,R.string.info_blood_pressure_meals)
            }
            Constants.KeyValues.WEIGHT_LOSS -> {
                newMealList = calculations.weightLossBestMeals(mealsList, 100) as MutableList<Meal>
                setTextBestMealsFragment(R.string.best_weight_losing_meals,R.string.info_weight_losing_meals)
            }
        }

        adapter = MealAdapter(newMealList, this)
        binding.recyclerMeal.adapter = adapter
    }


    private fun setTextBestMealsFragment(title: Int, Info: Int){
        titleFragment = getString(title)
        binding.textInfo.text = getString(Info)
    }

    fun onClickItem(meal: Meal) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.KeyValues.MEAL,meal)
        mealDetailsFragment.arguments = bundle
        navigationTo(mealDetailsFragment)
    }
}