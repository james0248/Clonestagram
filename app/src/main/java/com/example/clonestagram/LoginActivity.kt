package com.example.clonestagram

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseAuth
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_login_facebook
import kotlinx.android.synthetic.main.activity_login.txt_password
import java.util.*

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var callBackManager : CallbackManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        callBackManager = CallbackManager.Factory.create()
        initColors()

        link_signup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        btn_login_email.setOnClickListener {
            login()
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
                    Toast.makeText(this, "Login Success!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callBackManager?.onActivityResult(requestCode, resultCode, data)
    }

    private fun connect() {
        val uri = MongoClientURI(System.getenv("MONGO_URI"))
        val mongoClient = MongoClient(uri)
        val users = mongoClient.getDatabase("account")
    }

    private fun login() {
        auth?.signInWithEmailAndPassword(txt_login.text.toString(), txt_password.text.toString())
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Login Success!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
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
