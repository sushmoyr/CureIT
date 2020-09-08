package com.example.cureit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cureit.databinding.ItemMessageReceiveBinding
import com.example.cureit.databinding.ItemMessageSendBinding
import com.example.cureit.model.Message
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//class MainActivity2 : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main2)
//
//        val msg:TextView = findViewById(R.id.temptext)
//        msg.text = "Yeah Its working Oniii Chan!!!!"
//    }

class MainActivity2 : AppCompatActivity() {

    private val messageAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        recyclerView.adapter = messageAdapter
        populateData()

        button.setOnClickListener {
            val message = Message(msg = editText.text.toString(), sendBy = "me")
            val sendMessageItem = SendMessageItem(message)

            messageAdapter.add(sendMessageItem)
            editText.text.clear()

            receiveAutoResponse()
        }
    }

    private fun populateData() {
        val data = listOf<Message>()
        data.forEach {
            if (it.sendBy == "me") {
                messageAdapter.add(SendMessageItem(it))
            } else {
                messageAdapter.add(ReceiveMessageItem(it))
            }
        }
    }

    private fun receiveAutoResponse() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
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
