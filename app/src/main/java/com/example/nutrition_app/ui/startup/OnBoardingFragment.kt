package com.example.nutrition_app.ui.startup

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.nutrition_app.R
import com.example.nutrition_app.data.UserData
import com.example.nutrition_app.databinding.FragmentOnBoardingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class OnBoardingFragment : Fragment() {

    lateinit var binding: FragmentOnBoardingBinding
    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        CreateGoalSpinner()
        CreateActivitySpinner()
        CreateRadioGroup()
        binding.calculate.setOnClickListener {
            if (binding.checkgender.text == "Male") {
                CalulationsforMale()
            } else if (binding.checkgender.text == "Female") {
                CalulationsforFemale()
            }
            saveDataUser()
            Handler().postDelayed(
                {
                    navController.navigate(R.id.action_onBoardingFragment_to_signInFragment)
                },500)

        }
    }

    // Create Activity Spinner
    fun CreateActivitySpinner() {
        var Meals = arrayOf(
            "Select Your Activity",
            "Sedentary: little or no exercise",
            "Light:  exercise 1-3 times/week",
            "Moderate: exercise 4-5 times/week",
            "Very Active : intense exercise 6-7 times/week"
        )
        binding.ActivitySpinner.adapter =
            ArrayAdapter<String>(
                requireContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Meals
            )
        binding.ActivitySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    binding.checkactivity.text = binding.ActivitySpinner.selectedItem.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    binding.checkactivity.text = "Select Your Activity"
                }

            }
    }

    // Create Goal Spinner
    fun CreateGoalSpinner() {
        var Meals = arrayOf(
            "Select Your Goal",
            "Loss 0.25 kg/week",
            "Loss 0.5 kg/week",
            "Loss 1 kg/week",
            "Maintain weight",
            "Gain 0.25 kg/week",
            "Gain 0.5 kg/week",
            "Gain 1 kg/week"
        )
        binding.GoalSpinner.adapter =
            ArrayAdapter<String>(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                Meals
            )
        binding.GoalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.checkgoal.text = binding.GoalSpinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.checkgoal.text = "Select Your Goal"
            }

        }
    }

    //create RadioGroup
    fun CreateRadioGroup() {
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.male -> {
                    binding.checkgender.text = "Male"
                }

                R.id.female -> {
                    binding.checkgender.text = "Female"
                }
            }
        }
    }

    fun CalulationsforMale() {
        val w = binding.weight.editText?.text.toString().toDouble()
        val h = binding.height.editText?.text.toString().toDouble()
        val a = binding.age.editText?.text.toString().toDouble()
        val BMR = 88.362 + (13.397 * w) + (4.799 * h) - (5.877 * a)
        var ac = 0.0
        var ca = 0.0
        var protien = 0.0
        var carb = 0.0
        var fats = 0.0
        if (binding.checkactivity.text == "Sedentary: little or no exercise") {
            val c = BMR * 1.2
            ac = c
        } else if (binding.checkactivity.text == "Light:  exercise 1-3 times/week") {
            val c = BMR * 1.3
            ac = c
        } else if (binding.checkactivity.text == "Moderate: exercise 4-5 times/week") {
            val c = BMR * 1.55
            ac = c
        } else if (binding.checkactivity.text == "Very Active : intense exercise 6-7 times/week") {
            val c = BMR * 1.7
            ac = c
        }

        if (binding.checkgoal.text == "Loss 0.25 kg/week") {
            val Calories = ac - 200
            ca = Calories
            protien = (w * 2.5)
            fats = (ca * 0.2) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }

        if (binding.checkgoal.text == "Loss 0.5 kg/week") {
            val Calories = ac - 300
            ca = Calories
            protien = (w * 2.5)
            fats = (ca * 0.2) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }

        if (binding.checkgoal.text == "Loss 1 kg/week") {
            val Calories = ac - 500
            ca = Calories
            protien = (w * 2.5)
            fats = (ca * 0.2) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4

        }

        if (binding.checkgoal.text == "Maintain weight") {
            val Calories = ac
            ca = Calories
            protien = (w * 2)
            fats = (ca * 0.25) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }

        if (binding.checkgoal.text == "Gain 0.25 kg/week") {
            val Calories = ac + 200
            ca = Calories
            protien = (w * 2)
            fats = (ca * 0.25) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }

        if (binding.checkgoal.text == "Gain 0.5 kg/week") {
            val Calories = ac + 300
            ca = Calories
            protien = (w * 2)
            fats = (ca * 0.25) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }


        if (binding.checkgoal.text == "Gain 1 kg/week") {
            val Calories = ac + 500
            ca = Calories
            protien = (w * 2)
            fats = (ca * 0.3) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }
        binding.cal.text = ca.toString()
        binding.pro.text = protien.toString()
        binding.carb.text = carb.toString()
        binding.fat.text = fats.toString()
    }


    fun CalulationsforFemale() {
        val w = binding.weight.editText?.text.toString().toDouble()
        val h = binding.height.editText?.text.toString().toDouble()
        val a = binding.age.editText?.text.toString().toDouble()
        val BMR = 447.593 + (9.247 * w) + (3.098 * h) - (4.330 * a)
        var ac = 0.0
        var ca = 0.0
        var protien = 0.0
        var carb = 0.0
        var fats = 0.0
        if (binding.checkactivity.text == "Sedentary: little or no exercise") {
            val c = BMR * 1.2
            ac = c
        } else if (binding.checkactivity.text == "Light:  exercise 1-3 times/week") {
            val c = BMR * 1.3
            ac = c
        } else if (binding.checkactivity.text == "Moderate: exercise 4-5 times/week") {
            val c = BMR * 1.55
            ac = c
        } else if (binding.checkactivity.text == "Very Active : intense exercise 6-7 times/week") {
            val c = BMR * 1.7
            ac = c
        }

        if (binding.checkgoal.text == "Loss 0.25 kg/week") {
            val Calories = ac - 200
            ca = Calories
            protien = (w * 2)
            fats = (ca * 0.25) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }

        if (binding.checkgoal.text == "Loss 0.5 kg/week") {
            val Calories = ac - 300
            ca = Calories
            protien = (w * 2)
            fats = (ca * 0.25) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }

        if (binding.checkgoal.text == "Loss 1 kg/week") {
            val Calories = ac - 500
            ca = Calories
            protien = (w * 2)
            fats = (ca * 0.25) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4

        }

        if (binding.checkgoal.text == "Maintain weight") {
            val Calories = ac
            ca = Calories
            protien = (w * 2)
            fats = (ca * 0.3) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }

        if (binding.checkgoal.text == "Gain 0.25 kg/week") {
            val Calories = ac + 200
            ca = Calories
            protien = (w * 1.5)
            fats = (ca * 0.3) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }

        if (binding.checkgoal.text == "Gain 0.5 kg/week") {
            val Calories = ac + 300
            ca = Calories
            protien = (w * 1.5)
            fats = (ca * 0.3) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }


        if (binding.checkgoal.text == "Gain 1 kg/week") {
            val Calories = ac + 500
            ca = Calories
            protien = (w * 1.5)
            fats = (ca * 0.35) / 9
            val k = (protien * 4) + (fats * 9)
            carb = (ca - (k)) / 4
        }
        binding.cal.text = ca.toString()
        binding.pro.text = protien.toString()
        binding.carb.text = carb.toString()
        binding.fat.text = fats.toString()
    }

    private fun saveDataUser() {

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("User").child(userId).get().addOnSuccessListener {

            val email = it.child("email").value.toString()

            val eName = binding.username.editText?.text.toString().trim()
            val eAge = binding.age.editText?.text.toString().trim()
            val eWeight = binding.weight.editText?.text.toString().trim()
            val eHeight = binding.height.editText?.text.toString().trim()
            val eCalories = binding.cal.text.toString().trim()
            val eProtien = binding.pro.text.toString().trim()
            val eFats = binding.fat.text.toString().trim()
            val eCarb = binding.carb.text.toString().trim()

            val user = UserData(email, eName, eAge, eWeight, eHeight, eCalories, eProtien, eFats, eCarb)

            val userId = mAuth.currentUser!!.uid

            database.child("User").child(userId).setValue(user)

        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
    }

}