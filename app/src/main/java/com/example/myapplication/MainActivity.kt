package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var gameScoreTextView : TextView
    private lateinit var timeLeftTextView : TextView
    private lateinit var tapMebutton : Button
    private var score = 0

    private var gameStarted = false

    private lateinit var countDownTimer : CountDownTimer
    private var initialCountDown: Long = 60_000
    private var countDownInterval: Long = 1_000
    private var timeLeft = 60

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapMebutton = findViewById(R.id.tap_me_button)

        resetGame()

        tapMebutton.setOnClickListener{incrementScore()}

    }

    private fun incrementScore(){
        score++

        val newScore = getString(R.string.your_score, score)
        gameScoreTextView.text = newScore

        if (!gameStarted){
            startGame()
        }
    }
    private fun resetGame(){
        //1
        score = 0
        val initialScore = getString(R.string.your_score,score)
        gameScoreTextView.text = initialScore

        val initialTimeLeft = getString(R.string.time_left, 60)
        timeLeftTextView.text =initialTimeLeft

        //2
        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval){
            //3
            override fun onTick(p0: Long) {
                timeLeft = p0.toInt() / 1000
                val timeLeftString = getString(R.string.time_left,timeLeft)
                timeLeftTextView.text = timeLeftString
            }
            override fun onFinish() {
                endGame()
            }
        }
        //4
        gameStarted = false
    }
    private fun startGame(){
        countDownTimer.start()
        gameStarted = true
    }
    private fun endGame(){
        Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
        resetGame()
    }
}