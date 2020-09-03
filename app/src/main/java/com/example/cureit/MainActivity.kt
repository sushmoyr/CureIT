package com.example.cureit

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        greetings()
    }

    private fun greetings()
    {
        val welcomeMsg = "Please tell me how can I help you?"
        val welcome:TextView = findViewById(R.id.welcomeMsgView)
        val greetings:TextView = findViewById(R.id.greetings)
        val currentTime = LocalDateTime.now()
        val hours = currentTime.format(DateTimeFormatter.ofPattern("HH"))
        val checker:Int = hours.toInt()
        if(checker>=5 && checker <12)
            greetings.text = "Good Morning!!"
        else if(checker>=12 && checker<6)
            greetings.text = "Good Afternoon!!"
        else if(checker>=6 && checker<12)
            greetings.text = "Good Evening!!"
        else
            greetings.text = "Good Night!!"
        welcome.text = welcomeMsg

    }
}