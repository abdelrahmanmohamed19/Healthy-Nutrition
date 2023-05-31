package com.example.nutrition_app.ui.frgment_nave

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.nutrition_app.R
import com.example.nutrition_app.data.dataManager.MealDataManager
import com.example.nutrition_app.data.model.Meal
import com.example.nutrition_app.databinding.FragmentHomeBinding
import com.example.nutrition_app.ui.calculater.BestMealsFragment
import com.example.nutrition_app.util.Constants
import com.example.nutrition_app.util.enum.StateNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var database: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var mealDataManager: Parcelable = MealDataManager()
    private lateinit var mealList: MutableList<Meal>
    private val bundle = Bundle()
    private val bestMealFragment = BestMealsFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataLoad()

        buttonCardDiabetics()
        buttonCardGym()
        buttonCardPressure()
        buttonCardWeightLoss()
        buttonCalculateRequiredCaloriesFragment()

        binding.arrowButton.setOnClickListener { view ->
            if (binding.hiddenView.getVisibility() === View.VISIBLE) {
                TransitionManager.beginDelayedTransition(binding.cardview, AutoTransition())
                binding.hiddenView.setVisibility(View.GONE)
                binding.arrowButton.setImageResource(R.drawable.baseline_expand_more_24)
            } else {
                TransitionManager.beginDelayedTransition(binding.cardview, AutoTransition())
                binding.hiddenView.setVisibility(View.VISIBLE)
                binding.arrowButton.setImageResource(R.drawable.baseline_expand_less_24)
            }
        }

    }
    override fun onStart() {
        super.onStart()
        mealDataManager = requireNotNull(arguments?.getParcelable(Constants.KeyValues.Meal_DATA_MANAGER))
        mealList = (mealDataManager as MealDataManager).getMeals()
        bundle.putParcelable(Constants.KeyValues.Meal_DATA_MANAGER, mealDataManager)
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

    private fun buttonCardDiabetics() {
        binding.cardDiabetics.setOnClickListener {
            bundle.putString(Constants.KeyValues.BEST_MEAL_TYPE, Constants.KeyValues.DIABETICS)
            bestMealFragment.arguments = bundle
            navigationTo(bestMealFragment)

        }
    }

    private fun buttonCardGym() {
        binding.cardGym.setOnClickListener {
            bundle.putString(Constants.KeyValues.BEST_MEAL_TYPE, Constants.KeyValues.GYM)
            bestMealFragment.arguments = bundle
            navigationTo(bestMealFragment)
        }
    }


    private fun buttonCardPressure() {
        binding.cardPressure.setOnClickListener {
            bundle.putString(Constants.KeyValues.BEST_MEAL_TYPE, Constants.KeyValues.PRESSURE)
            bestMealFragment.arguments = bundle
            navigationTo(bestMealFragment)
        }
    }


    private fun buttonCardWeightLoss() {
        binding.cardWeightLoss.setOnClickListener {
            bundle.putString(Constants.KeyValues.BEST_MEAL_TYPE, Constants.KeyValues.WEIGHT_LOSS)
            bestMealFragment.arguments = bundle
            navigationTo(bestMealFragment)
        }
    }

    private fun buttonCalculateRequiredCaloriesFragment() {
        binding.calculateRequiredCalories.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_containerFragment_to_calculateRequiredCaloriesFragment)
        }
    }

    private fun dataLoad(){
        database = Firebase.database.reference
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("User").child(userId).get().addOnSuccessListener {

            val name = it.child("name").value.toString()
            val cal = it.child("calories").value.toString()
            val pro = it.child("protien").value.toString()
            val fats = it.child("fats").value.toString()
            val carb = it.child("carb").value.toString()
            binding.username.text="Welcome ${name} !"
            binding.dailyCalories.text="Your Daily Calories: ${cal} KCL"
            binding.CarbIntake.text="Your Daily Carb Intake: ${carb} grams"
            binding.protienIntake.text="Your Daily Protein Intake: ${pro} grams"
            binding.FatsIntake.text="Your Daily Fats Intake: ${fats} grams"
            database.keepSynced(true)
        }
    }


    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()
    }
}