package com.intermedia.challenge.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.intermedia.challenge.ui.main.MainScreenActivity
import com.intermedia.challenge.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.TextView
import android.view.View
import com.intermedia.challenge.R
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        startFirebaseAuth()
    }

    private fun startFirebaseAuth() {
        // TODO complete using Firebase Auth UI
        setupLogin()
        setupRegister()
    }

    private fun setupRegister() {
        button_register.setOnClickListener {
            if(text_input_email.text?.isNotEmpty() == true && text_input_password.text?.isNotEmpty() == true) {
                val email = text_input_email.text.toString()
                val password = text_input_password.text.toString()
                val auth = Firebase.auth

                auth.createUserWithEmailAndPassword(email ,password).addOnCompleteListener(this) { task->
                    if(task.isSuccessful) {
                        startActivity(Intent(this, MainScreenActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        })
                    } else {
                        val error = "The registration of Email and Password is not correct. Please retry."
                        applyToast(error)
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun setupLogin() {
        val auth = Firebase.auth
        var user = auth.currentUser
        val numberCharAllowed = 7
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        //Verificamos si el usuario esta logeado

        //user = null

        text_input_email.addTextChangedListener {
            //Chequea que la cantidad de valores del input sea mayor a 'numberCharAllowed' = 7
            if(text_input_email.length() > numberCharAllowed && text_input_password.length() > numberCharAllowed) {
                applyStateView(button_login)
                applyStateView(button_register)
            }
            else {
                unapplyStateView(button_login)
                unapplyStateView(button_register)
            }
        }
        text_input_password.addTextChangedListener {
            if(text_input_email.length() > numberCharAllowed && text_input_password.length() > numberCharAllowed) {
                applyStateView(button_login)
                applyStateView(button_register)
            }
            else {
                unapplyStateView(button_login)
                unapplyStateView(button_register)
            }
        }

        if(user == null) {
            button_login.setOnClickListener {
                if(text_input_email.text?.isNotEmpty() == true && text_input_password.text?.isNotEmpty() == true) {
                    val email = text_input_email.text.toString()
                    val password = text_input_password.text.toString()
                    user = auth.currentUser
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {

                                with (sharedPref.edit()) {
                                    putString(user?.email, "email")
                                    putString(user?.displayName, "name")
                                    commit()
                                }
                                startActivity(Intent(this, MainScreenActivity::class.java).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                })
                            } else {

                                val error = "The combination of Email and Password is not correct. Please retry."
                                applyToast(error)

                                val elem_email = text_input_email_layout
                                val elem_password = text_input_password_layout
                                applyErrorEffects(elem_email)
                                applyErrorEffects(elem_password)
                            }
                        }
                }
            }

        } else {
                startActivity(Intent(this, MainScreenActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })
            }
    }

    private fun unapplyStateView(view: View) {
        view.isClickable = false
        view.setBackgroundResource(R.drawable.dw_rounded_button)
    }

    private fun applyStateView(view: View) {
        view.isClickable = true
        view.setBackgroundResource(R.drawable.dw_active_button)
    }

    private fun applyToast(text : String) {
        val toast = Toast.makeText(applicationContext,text,Toast.LENGTH_SHORT)
        toast.view.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        toast.view.setBackgroundResource(R.drawable.dw_rounded_toast)
        val text = toast.view.findViewById<View>(android.R.id.message) as TextView
        text.setTextColor(ContextCompat.getColor(this, R.color.white))
        text.textSize = 14f
        toast.show()
    }

    private fun applyErrorEffects(elem : TextInputLayout) {
        elem.background = (ContextCompat.getDrawable(this, R.drawable.dw_red_stroke_error))
        elem.hintTextColor = (ContextCompat.getColorStateList(this, R.color.red))
        elem.defaultHintTextColor = ContextCompat.getColorStateList(this, R.color.red)
    }

}