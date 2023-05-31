package com.example.nutrition_app.ui.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrition_app.adapter.AdapterNutritionDataF
import com.example.nutrition_app.data.NutritionDataF
import com.example.nutrition_app.databinding.DinnerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class DinnerFragment : Fragment() {

    lateinit var binding: DinnerBinding
    private lateinit var dataNutrientList: ArrayList<NutritionDataF>
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var mAdapterNutritionData: AdapterNutritionDataF
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DinnerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

//        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
//        actionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = binding.RecDinner
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        dataNutrientList = arrayListOf()
        mAdapterNutritionData = AdapterNutritionDataF(dataNutrientList)

        var query: Query = database.orderByKey()
        query = database.orderByChild("meal").startAt("Dinner").endAt("Dinner")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapShot in snapshot.children) {
                        val dataNutrition = dataSnapShot.getValue(NutritionDataF::class.java)
                        dataNutrientList.add(dataNutrition!!)
                        dataNutrientList.sortByDescending {
                            it.timestamp
                        }
                    }
                    recyclerView.adapter = AdapterNutritionDataF(dataNutrientList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun init(view: View) {
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("NutritionData")
            .child(mAuth.currentUser?.uid.toString())
        database.keepSynced(true)

    }
}