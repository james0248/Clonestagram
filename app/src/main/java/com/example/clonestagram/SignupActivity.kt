package com.example.clonestagram

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.btn_login_facebook
import kotlinx.android.synthetic.main.activity_signup.txt_password
import java.util.*

class SignupActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private var callBackManager : CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()
        callBackManager = CallbackManager.Factory.create()
        initColors()

        btn_signup.setOnClickListener {
            signupWithEmail()
        }
        btn_login_facebook.setOnClickListener {
            facebookLogin()
        }
    }

    private fun facebookLogin() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))

        LoginManager.getInstance()
            .registerCallback(callBackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    handleFacebookAccessToken(result?.accessToken)
                }

                override fun onError(error: FacebookException?) {
                    TODO("Not yet implemented")
                }

                override fun onCancel() {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun handleFacebookAccessToken(token : AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(token?.token!!)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Signup with Facebook success!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun signupWithEmail() {
        auth?.createUserWithEmailAndPassword(txt_email.text.toString(), txt_password.text.toString())
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Signup Successful!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
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
