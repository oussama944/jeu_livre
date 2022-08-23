package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.system.measureTimeMillis


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

    private val TAG =MainActivity::class.java.simpleName

    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT__KEY = "TIME_LEFT__KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapMebutton = findViewById(R.id.tap_me_button)

        if(savedInstanceState != null){
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeft = savedInstanceState.getInt(TIME_LEFT__KEY)
            restoreGame()
        }else{
            resetGame()
        }


        tapMebutton.setOnClickListener{ v->
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            v.startAnimation(bounceAnimation)
            incrementScore()

        }

        Log.d(TAG,"onCreate called . $score")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.about_item){
            //showInfo()
        }
        return true
    }


    private fun restoreGame() {
        val restoredScore = getString(R.string.your_score, score)
        gameScoreTextView.text = restoredScore

        val restoredTime = getString(R.string.time_left, timeLeft)

        timeLeftTextView.text = restoredTime

        countDownTimer = object : CountDownTimer((timeLeft*1000).toLong(),countDownInterval){
            override fun onTick(p0: Long) {
                timeLeft = p0.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, timeLeft)
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish(){
                endGame()
            }
        }
        countDownTimer.start()
        gameStarted = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT__KEY, timeLeft)
        countDownTimer.cancel()

        Log.d(TAG, "onSaveInstance Saving SCORE: $score & TIME : $timeLeft")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy called.")
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