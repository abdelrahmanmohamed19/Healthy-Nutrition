package com.example.nutrition_app.ui.frgment_nave

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nutrition_app.R
import com.example.nutrition_app.databinding.FragmentResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class ResultFragment : Fragment() {

    lateinit var binding: FragmentResultBinding
    private lateinit var database: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBreakfast.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_resultFragment_to_breakFastFragment2)
        }
        binding.addLunch.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_launchFragment2)
        }
        binding.addDinner.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_dinnerFragment2)
        }
        binding.addSnack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_snacksFragment2)
        }
        updateProgressCalories()
        updateProgressCarb()
        updateProgressProtein()
        updateProgressFat()
        dataLoad()
    }

    fun getEndOfDay(): Calendar {
        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY))
//        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE))
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND))
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND))
        return calendar
    }


    private fun dataLoad() {
        database = Firebase.database.reference
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("User").child(userId).get().addOnSuccessListener {

            val recal = it.child("reCal").value.toString()
            val cal = it.child("calories").value.toString()
            val pro = it.child("rePro").value.toString()
            val fats = it.child("reFats").value.toString()
            val carb = it.child("reCarb").value.toString()


            val ccal = it.child("calories").value.toString()
            val ppro = it.child("protien").value.toString()
            val ffats = it.child("fats").value.toString()
            val ccarb = it.child("carb").value.toString()


            val currentTime = System.currentTimeMillis()
            val endOfDay = getEndOfDay().timeInMillis
            if (currentTime > endOfDay) {

                val editMap = mapOf<String, Any?>(
                    "reCal" to ccal.toString(),
                    "rePro" to ppro.toString(),
                    "reCarb" to ffats.toString(),
                    "reFats" to ccarb.toString()
                )

                val userId = FirebaseAuth.getInstance().currentUser!!.uid
                database.child("User").child(userId).updateChildren(editMap)
            }



            binding.KcalGoalNumber.text = cal
            binding.textViewProgress.text = recal
            binding.carbText.text = carb
            binding.ProText.text = pro
            binding.FatsText.text = fats

            database.keepSynced(true)

        }

    }

    private fun updateProgressCalories() {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the value from Firebase and update the progress
                val caloriesMap = dataSnapshot.getValue<HashMap<String, Any>>()
                if (caloriesMap != null && caloriesMap.containsKey("reCal")) {
                    val caloriesValue = caloriesMap["reCal"]
                    if (caloriesValue is String) {
                        val currentCalories = caloriesValue.toDoubleOrNull()
                        if (currentCalories != null) {
                            val maxCalories = caloriesMap["calories"]
                            if (maxCalories is String) {
                                val maxCaloriesValue = maxCalories.toDoubleOrNull()
                                if (maxCaloriesValue != null) {
                                    val progress =
                                        calculateProgress(currentCalories, maxCaloriesValue)
                                    binding.progressBar.progress = progress
                                    Log.i("TAG", "Progress Calories updated to: $progress")
                                }
                            }
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database errors if needed
            }
        }

        // Attach the listener to the Firebase database reference
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("User")
            .child(mAuth.currentUser?.uid.toString())
        database.addValueEventListener(valueEventListener)
    }

    private fun updateProgressCarb() {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the value from Firebase and update the progress
                val caloriesMap = dataSnapshot.getValue<HashMap<String, Any>>()
                if (caloriesMap != null && caloriesMap.containsKey("reCarb")) {
                    val caloriesValue = caloriesMap["reCarb"]
                    if (caloriesValue is String) {
                        val currentCalories = caloriesValue.toDoubleOrNull()
                        if (currentCalories != null) {
                            val maxCalories = caloriesMap["carb"]
                            if (maxCalories is String) {
                                val maxCaloriesValue = maxCalories.toDoubleOrNull()
                                if (maxCaloriesValue != null) {
                                    val progress =
                                        calculateProgress(currentCalories, maxCaloriesValue)
                                    binding.carbbar.progress = progress
                                    Log.i("TAG", "Progress Carb updated to: $progress")
                                }
                            }
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database errors if needed
            }
        }

        // Attach the listener to the Firebase database reference
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("User")
            .child(mAuth.currentUser?.uid.toString())
        database.addValueEventListener(valueEventListener)
    }

    private fun updateProgressProtein() {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the value from Firebase and update the progress
                val caloriesMap = dataSnapshot.getValue<HashMap<String, Any>>()
                if (caloriesMap != null && caloriesMap.containsKey("rePro")) {
                    val caloriesValue = caloriesMap["rePro"]
                    if (caloriesValue is String) {
                        val currentCalories = caloriesValue.toDoubleOrNull()
                        if (currentCalories != null) {
                            val maxCalories = caloriesMap["protien"]
                            if (maxCalories is String) {
                                val maxCaloriesValue = maxCalories.toDoubleOrNull()
                                if (maxCaloriesValue != null) {
                                    val progress = calculateProgress(currentCalories, maxCaloriesValue)
                                    binding.Probar.progress = progress
                                    Log.i("TAG", "Progress Protein updated to: $progress")
                                }
                            }
                        }
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database errors if needed
            }
        }

        // Attach the listener to the Firebase database reference
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("User")
            .child(mAuth.currentUser?.uid.toString())
        database.addValueEventListener(valueEventListener)
    }

    private fun updateProgressFat() {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the value from Firebase and update the progress
                val caloriesMap = dataSnapshot.getValue<HashMap<String, Any>>()
                if (caloriesMap != null && caloriesMap.containsKey("reFats")) {
                    val caloriesValue = caloriesMap["reFats"]
                    if (caloriesValue is String) {
                        val currentCalories = caloriesValue.toDoubleOrNull()
                        if (currentCalories != null) {
                            val maxCalories = caloriesMap["fats"]
                            if (maxCalories is String) {
                                val maxCaloriesValue = maxCalories.toDoubleOrNull()
                                if (maxCaloriesValue != null) {
                                    val progress = calculateProgress(currentCalories, maxCaloriesValue)
                                    binding.Fatsbar.progress = progress
                                    Log.i("TAG", "Progress Fat updated to: $progress")
                                }
                            }
                        }
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database errors if needed
            }
        }

        // Attach the listener to the Firebase database reference
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("User")
            .child(mAuth.currentUser?.uid.toString())
        database.addValueEventListener(valueEventListener)
    }


    private fun calculateProgress(currentCalories: Double, maxCalories: Double): Int {
        val progress = ((currentCalories / maxCalories) * 100).toInt()
        return progress
    }
}