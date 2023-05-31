package com.example.nutrition_app.ui.startup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import com.example.nutrition_app.databinding.FragmentForgotpasswordBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var binding: FragmentForgotpasswordBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentForgotpasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnForgetPass.setOnClickListener {
            val emailAddress: String = binding.sendCode.text.toString()

            if(emailAddress.isEmpty()){
                Toast.makeText(context, "please enter Email", Toast.LENGTH_SHORT).show()

            }else{
                Firebase.auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "please check your Email", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "Email sent.")
                        }else{
                            Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
    }

    companion object {
        private const val TAG = "ResetPass"
        private const val RC_SIGN_IN = 9001
    }
}