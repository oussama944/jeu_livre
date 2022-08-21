package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var gameScoreTextView : TextView
        lateinit var timeLeftTextView : TextView
        lateinit var tapMebutton : Button
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun incrementScore(){

    }
    private fun resetGame(){

    }
    private fun startGame(){

    }
    private fun endGame(){
        
    }
}