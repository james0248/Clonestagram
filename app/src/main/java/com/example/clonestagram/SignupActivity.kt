package com.example.clonestagram

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initColors()
    }

    private fun initColors() {
        val darkGray = ContextCompat.getColor(applicationContext, R.color.dark_gray)
        val facebook = ContextCompat.getColor(applicationContext, R.color.login_facebook)

        (txt_email.background as GradientDrawable).setStroke(1, darkGray)
        (txt_email.background as GradientDrawable).setColor(0)

        (txt_password.background as GradientDrawable).setStroke(1, darkGray)
        (txt_password.background as GradientDrawable).setColor(0)

        (txt_name.background as GradientDrawable).setStroke(1, darkGray)
        (txt_name.background as GradientDrawable).setColor(0)

        (txt_username.background as GradientDrawable).setStroke(1, darkGray)
        (txt_username.background as GradientDrawable).setColor(0)

        (btn_login_facebook.background as GradientDrawable).setStroke(1, facebook)
        (btn_login_facebook.background as GradientDrawable).setColor(facebook)

        (btn_signup.background as GradientDrawable).setStroke(1, facebook)
        (btn_signup.background as GradientDrawable).setColor(facebook)
    }
}
