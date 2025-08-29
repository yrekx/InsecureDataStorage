package com.example.insecuredatastorage

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import com.example.insecuredatastorage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"
    private val KEY_REMEMBER_ME = "remember_me"

    private val DEFAULT_KEY_USERNAME = "default_username"
    private val DEFAULT_KEY_PASSWORD = "default_password"

    private fun checkAndFillLoginInfo() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_WORLD_READABLE)
        val rememberMe = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)

        if (rememberMe) {
            val username = sharedPreferences.getString(KEY_USERNAME, "")
            val password = sharedPreferences.getString(KEY_PASSWORD, "")

            binding.etUsername.setText(username)
            binding.etPassword.setText(password)
            binding.cbRememberMe.isChecked = true
        }
    }

    private fun saveLoginInfo(username: String, password: String, rememberMe: Boolean) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_WORLD_READABLE)
        sharedPreferences.edit {
            putString(KEY_USERNAME, username)
            putString(KEY_PASSWORD, password)
            putBoolean(KEY_REMEMBER_ME, rememberMe)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkAndFillLoginInfo()
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_WORLD_READABLE)
        sharedPreferences.edit {
            putString(DEFAULT_KEY_USERNAME, "admin")
            putString(DEFAULT_KEY_PASSWORD, "12345")
        }
        binding.cbRememberMe.setOnClickListener {
            val rememberMe = binding.cbRememberMe.isChecked
            sharedPreferences.edit {
                putBoolean(KEY_REMEMBER_ME, rememberMe)
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val rememberMe = binding.cbRememberMe.isChecked
            if (username!="" && password!="" && rememberMe) {
                saveLoginInfo(username, password, true)
            }else if (rememberMe) {
                val username = sharedPreferences.getString(DEFAULT_KEY_USERNAME, "")
                val password = sharedPreferences.getString(DEFAULT_KEY_PASSWORD, "")
            }
            var intent = Intent(this, DisplayInfo::class.java)
            startActivity(intent)
        }
    }
}

