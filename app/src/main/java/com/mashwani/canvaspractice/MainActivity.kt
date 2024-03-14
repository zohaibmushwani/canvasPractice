package com.mashwani.canvaspractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomBar = findViewById<BottomBar>(R.id.bottomBar)
//        bottomBar.setOnItemSelectedListener(this)

        val items = listOf(
            BottomBar.BottomBarItem("Analog", R.drawable.ic_bottom_bar_analog, R.drawable.ic_bottom_bar_analog),
            BottomBar.BottomBarItem("Digital", R.drawable.ic_bottom_bar_digital, R.drawable.ic_bottom_bar_digital),
            BottomBar.BottomBarItem("Home", R.drawable.ic_bottom_bar_home, R.drawable.ic_bottom_bar_home),
            BottomBar.BottomBarItem("Map", R.drawable.ic_bottom_bar_map, R.drawable.ic_bottom_bar_map),
            BottomBar.BottomBarItem("History", R.drawable.ic_bottom_bar_history, R.drawable.ic_bottom_bar_history)
        )
        bottomBar.setBoxItems(items)
    }

//    override fun onItemSelected(position: Int) {
//        // Handle item selection event here
//    }
}
