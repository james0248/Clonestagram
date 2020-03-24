package com.example.clonestagram

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_login_facebook
import kotlinx.android.synthetic.main.activity_login.txt_password
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initColors()

        link_signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        auth?.signInWithEmailAndPassword(txt_login.text.toString(), txt_password.text.toString())
            ?.addOnCompleteListener {
            }

    }

    private fun moveMainPage() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun initColors() {
        val darkGray = ContextCompat.getColor(applicationContext, R.color.dark_gray)
        val gray = ContextCompat.getColor(applicationContext, R.color.gray)
        val facebook = ContextCompat.getColor(applicationContext, R.color.login_facebook)

        (txt_login.background as GradientDrawable).setStroke(1, darkGray)
        (txt_login.background as GradientDrawable).setColor(gray)

        (txt_password.background as GradientDrawable).setStroke(1, darkGray)
        (txt_password.background as GradientDrawable).setColor(gray)

        (btn_login_email.background as GradientDrawable).setStroke(1, darkGray)

        (btn_login_facebook.background as GradientDrawable).setStroke(1, facebook)
        (btn_login_facebook.background as GradientDrawable).setColor(facebook)
    }
}
