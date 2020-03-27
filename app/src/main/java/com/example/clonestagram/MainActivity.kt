package com.example.clonestagram

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.clonestagram.navigation.AccountFragment
import com.example.clonestagram.navigation.MainViewFragment
import com.example.clonestagram.navigation.NotificationFragment
import com.example.clonestagram.navigation.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_home -> {
                val mainViewFragment = MainViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, mainViewFragment).commit()
                return true
            }
            R.id.action_search -> {
                val searchFragment = SearchFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, searchFragment).commit()
                return true
            }
            R.id.action_add_photo -> {
                return true
            }
            R.id.action_notification -> {
                val notificationFragment = NotificationFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, notificationFragment).commit()
                return true
            }
            R.id.action_account -> {
                val accountFragment = AccountFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, accountFragment).commit()
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_bottom.itemIconTintList = null
        nav_bottom.selectedItemId = R.id.action_home
        nav_bottom.setOnNavigationItemSelectedListener(this)
    }
}
