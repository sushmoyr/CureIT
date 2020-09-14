package com.example.cureit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cureit.model.Injuries
import com.google.firebase.database.FirebaseDatabase

lateinit var injuryNameBtn:EditText
lateinit var solutionMsg:EditText
lateinit var saveDataBtn:Button

class DatabaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        injuryNameBtn = findViewById(R.id.injury_name)
        solutionMsg = findViewById(R.id.solution)
        saveDataBtn = findViewById(R.id.savedata)

        saveDataBtn.setOnClickListener {
            upload()
            injuryNameBtn.text.clear()
            solutionMsg.text.clear()
        }
    }

    fun upload()
    {
        val injuryName:String = injuryNameBtn.text.toString().trim()
        val solutionMsg:String = solutionMsg.text.toString()
        if(injuryName.isEmpty() or solutionMsg.isEmpty())
        {
            Toast.makeText(applicationContext, "Field can't be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("Injuries")
        val inid = ref.push().key.toString()

        val injuryModel = Injuries(inid, injuryName, solutionMsg)
        ref.child(inid).setValue(injuryModel).addOnCompleteListener{
            Toast.makeText(applicationContext, "Data added successfully", Toast.LENGTH_SHORT).show()
        }

    }
}