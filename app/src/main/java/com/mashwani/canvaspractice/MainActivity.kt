package com.mashwani.canvaspractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomBar = findViewById<BottomBar>(R.id.bottomBar)
        val items = listOf(
            BottomBarItem("Home",R.drawable.ic_home_black_24dp, R.drawable.ic_home_black_24dp,),
            BottomBarItem("Dashboard",R.drawable.ic_dashboard_black_24dp, R.drawable.ic_home_black_24dp,),
            BottomBarItem("Notification",R.drawable.ic_notifications_black_24dp, R.drawable.ic_home_black_24dp,)
        )
        bottomBar.setBoxItems(items)
    }
}