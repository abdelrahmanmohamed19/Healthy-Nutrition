package com.example.nutrition_app.ui.frgment_nave

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nutritionapp.R
import com.example.nutritionapp.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var database: DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tName = binding.textName
        val eName = binding.eName
        val eAge = binding.eAge
        val eWeight = binding.eWeight
        val eHeight = binding.eHeight
        val eEmail = binding.eEmail

        database = Firebase.database.reference

        binding.btnEdit.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext())
            bottomSheet.requestWindowFeature(Window.FEATURE_NO_TITLE)
            bottomSheet.setContentView(R.layout.edit_profile_data)
            bottomSheet.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            bottomSheet.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val btnCancel: Button? = bottomSheet.findViewById(R.id.btn_cancel)
            val btnEdit: Button? = bottomSheet.findViewById(R.id.btn_save)

            val edName: EditText? = bottomSheet.findViewById(R.id.ed_name)
            val edAge: EditText? = bottomSheet.findViewById(R.id.ed_age)
            val edWeight: EditText? = bottomSheet.findViewById(R.id.ed_weight)
            val edHeight: EditText? = bottomSheet.findViewById(R.id.ed_height)
            val edEmail: EditText? = bottomSheet.findViewById(R.id.ed_email)

            val sName = eName.text.toString()
            val sAge = eAge.text.toString()
            val sWeight = eWeight.text.toString()
            val sHeight = eHeight.text.toString()
            val sEmail = eEmail.text.toString()

            edName!!.setText(sName)
            edAge!!.setText(sAge)
            edWeight!!.setText(sWeight)
            edHeight!!.setText(sHeight)
            edEmail!!.setText(sEmail)

            btnCancel!!.setOnClickListener {
                bottomSheet.dismiss()
            }

            btnEdit!!.setOnClickListener {
                val name = edName!!.text.toString()
                val age = edAge!!.text.toString()
                val weight = edWeight!!.text.toString()
                val height = edHeight!!.text.toString()
                val email = edEmail!!.text.toString()

                val editMap = mapOf<String, Any?>(
                    "name" to name,
                    "age" to age,
                    "weight" to weight,
                    "height" to height,
                    "email" to email
                )

                val userId = FirebaseAuth.getInstance().currentUser!!.uid

                database.child("User").child(userId).updateChildren(editMap)

                Toast.makeText(context, "Edit Success!", Toast.LENGTH_SHORT).show()
            }
            bottomSheet.show()
        }


        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("User").child(userId).get().addOnSuccessListener {

            val tname = it.child("name").value.toString()
            val name = it.child("name").value.toString()
            val age = it.child("age").value.toString()
            val weight = it.child("weight").value.toString()
            val height = it.child("height").value.toString()
            val email = it.child("email").value.toString()

            tName.text = tname
            eName.text = name
            eAge.text = age
            eWeight.text = weight
            eHeight.text = height
            eEmail.text = email


            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener {

            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            Log.e("firebase", "Error getting data", it)
        }

    }

}