package com.example.cureit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewParent
import android.widget.*
import androidx.core.view.get
import com.example.cureit.databinding.ItemMessageReceiveBinding
import com.example.cureit.databinding.ItemMessageSendBinding
import com.example.cureit.model.Injuries
import com.example.cureit.model.Message
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.activity_injury.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class Injury : AppCompatActivity() {

    private val messageAdapter = GroupAdapter<GroupieViewHolder>()
    lateinit var ref : DatabaseReference
    lateinit var injuryInfo: MutableList<Injuries>
    lateinit var injuryName: MutableList<String>
    lateinit var sendBtn:Button





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_injury)

        recyclerView.adapter = messageAdapter
        val spinnerOfChoices:Spinner = findViewById(R.id.itemChoice)
        injuryInfo = mutableListOf()
        injuryName = mutableListOf()
        sendBtn = findViewById(R.id.send_msg_button)
        var userMessage:String = ""
        val editText:TextView = findViewById(R.id.editText)
        var temp:Int =-1


        //Initializing Database======================
        ref = FirebaseDatabase.getInstance().getReference("Injuries")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    Toast.makeText(applicationContext, "Database Found", Toast.LENGTH_SHORT).show()
                    for (n in snapshot.children) {
                        val injuriesList = n.getValue(Injuries::class.java)
                        injuryInfo.add(injuriesList!!)
                        injuryName.add(injuriesList!!.name)
                    }

                } else
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //--------------------------------------------------


        //---------------Spinner------------------------
        spinnerOfChoices.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, injuryName)


        spinnerOfChoices.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                userMessage = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



        sendBtn.setOnClickListener {
            val message = Message(msg = userMessage, sendBy = "me")
            val sendMessageItem = SendMessageItem(message)

            messageAdapter.add(sendMessageItem)
            receiveResponse()
        }

        //button.setOnClickListener {
          //  val message = Message(msg = userMessage, sendBy = "me")
            //val sendMessageItem = SendMessageItem(message)

           // messageAdapter.add(sendMessageItem)

            //receiveResponse()
        //}
    }

    //Response Part
    private fun receiveResponse() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(500)
            val receive = Message(
                msg = "Hi! This is a sample message.",
                sendBy = "me"
            )
            val receiveItem = ReceiveMessageItem(receive)

            messageAdapter.add(receiveItem)
        }
    }


}



class SendMessageItem(private val message: Message) : BindableItem<ItemMessageSendBinding>()
{

    override fun getLayout(): Int
    {
        return R.layout.item_message_send
    }

    override fun bind(viewBinding: ItemMessageSendBinding, position: Int)
    {
        if(message.msg.isNullOrEmpty())
            return
        else
            viewBinding.message = message
    }
}

class ReceiveMessageItem(private val message: Message) : BindableItem<ItemMessageReceiveBinding>()
{
    override fun getLayout(): Int {
        return R.layout.item_message_receive
    }

    override fun bind(viewBinding: ItemMessageReceiveBinding, position: Int) {
        viewBinding.message = message
    }
}