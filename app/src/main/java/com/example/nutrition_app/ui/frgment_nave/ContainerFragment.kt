package com.example.nutrition_app.ui.frgment_nave

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.nutrition_app.R
import com.example.nutrition_app.data.dataManager.MealDataManager
import com.example.nutrition_app.data.local.LocalStorage
import com.example.nutrition_app.data.model.Meal
import com.example.nutrition_app.databinding.FragmentContainerBinding
import com.example.nutrition_app.util.Constants
import com.example.nutrition_app.util.parsers.CSVParser
import com.example.nutrition_app.util.parsers.CSVParserHealthAdvice
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.InputStreamReader

class ContainerFragment : Fragment() {

    private lateinit var bottomNavigationView: BottomNavigationView

    lateinit var binding: FragmentContainerBinding
    private val maelParser = CSVParser()
    private val healthAdviceParser = CSVParserHealthAdvice()
    val bundle = Bundle()
    private lateinit var dataManager: Parcelable
    private lateinit var healthAdviceDataManger: Parcelable
    private lateinit var mealsList: MutableList<Meal>

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContainerBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        val localStorage = LocalStorage(requireContext())
        localStorage.put<String>(Constants.Data.LocalStorage.MY_DATA, "Hello from local storage")

        Log.v("ADD", openFile(Constants.FilePath.NUTRITION_CSV).toString())
        dataManager = maelParser.getMealsFromCSV(openFile(Constants.FilePath.NUTRITION_CSV))
        Log.v("BDD", openFile(Constants.FilePath.HEALTH_ADVICES_CSV).toString())
        healthAdviceDataManger =
            healthAdviceParser.getHealthAdvicesFromCSV(openFile(Constants.FilePath.HEALTH_ADVICES_CSV))
        Log.v("ASD", healthAdviceDataManger.toString())
        bundle.putParcelable(Constants.KeyValues.Meal_DATA_MANAGER, dataManager)
        mealsList = (dataManager as MealDataManager).getMeals()

        localStorage.get<String>(Constants.Data.LocalStorage.MY_DATA)

        firstFragmentView()
        bottomNavigationBar()


    }

    override fun onStart() {
        super.onStart()
        bottomNavigationBar()
    }

    private fun bottomNavigationBar() {

        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.navigation_dashboard -> {
                    replaceFragment(SearchFragment())
                    true
                }

                R.id.navigation_result -> {
                    replaceFragment(MealsFragment())
                    true
                }

                R.id.navigation_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }

    }


    private fun firstFragmentView() {
        val homeFragment = HomeFragment()
        homeFragment.arguments = bundle
        addFragment(homeFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        fragment.arguments = bundle
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun addFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, fragment)
        transaction.commit()
    }

    fun openFile(filePath: String): InputStreamReader {
        val assetManager = requireContext().assets
        val inputStream = assetManager.open(filePath)
        Log.v("XDD", inputStream.toString())
        return InputStreamReader(inputStream)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbarmenu, menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Firebase.auth.signOut()
                navController.navigate(R.id.action_containerFragment_to_signInFragment)
                Toast.makeText(context, "Logout success", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}