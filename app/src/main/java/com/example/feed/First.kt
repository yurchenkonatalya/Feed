package com.example.feed

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class First: AppCompatActivity() {
    lateinit var buttonLog: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first)
        buttonLog = findViewById(R.id.buttonLog)
    }
    fun change(view: View){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    fun changeInform(view: View){
        val intent = Intent(this, Inform::class.java)
        startActivity(intent)
    }
}