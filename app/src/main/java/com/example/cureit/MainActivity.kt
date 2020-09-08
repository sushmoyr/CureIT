package com.example.cureit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        greetings()
           val startChatBtn:Button = findViewById(R.id.start_chat)
        startChatBtn.setOnClickListener{

            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        val aboutBtn:Button = findViewById(R.id.about)
        aboutBtn.setOnClickListener{
            Toast.makeText(this, "About not set. Stay Tuned", Toast.LENGTH_SHORT).show()
        }

        val exitBtn:Button = findViewById(R.id.exit)
        exitBtn.setOnClickListener{
            finish()
        }
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
        else if(checker>=12 && checker<18)
            greetings.text = "Good Afternoon!!"
        else if(checker>=18 && checker<23)
            greetings.text = "Good Evening!!"
        else
            greetings.text = "Good Night!!"
        welcome.text = welcomeMsg

    }
}