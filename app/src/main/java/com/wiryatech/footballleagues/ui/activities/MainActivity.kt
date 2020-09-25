package com.wiryatech.footballleagues.ui.activities

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wiryatech.footballleagues.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        val menu = PopupMenu(this,null).menu
        menuInflater.inflate(R.menu.menu, menu)
        bottomBar.setupWithNavController(menu, navController)
    }

}