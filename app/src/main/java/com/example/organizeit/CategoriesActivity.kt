package com.example.organizeit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

    private lateinit var navContrtroler: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        setSupportActionBar(toolbar)

        navContrtroler=Navigation.findNavController(this, R.id.fragment)

        NavigationUI.setupWithNavController(navigation_view, navContrtroler)
        NavigationUI.setupActionBarWithNavController(this, navContrtroler, prueba_layout)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navContrtroler,
            prueba_layout
        )
    }

}