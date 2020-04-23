package com.example.clonestagram

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.example.clonestagram.navigation.*
import com.example.clonestagram.navigation.addPost.AddPhotoActivity
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
                startActivity(Intent(this, AddPhotoActivity::class.java))
                return false
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
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        nav_bottom.itemIconTintList = null
        nav_bottom.setOnNavigationItemSelectedListener(this)
        nav_bottom.selectedItemId = R.id.action_home

        ic_camera.setOnClickListener {
        }
    }
}
