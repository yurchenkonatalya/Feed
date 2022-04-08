package com.example.feed

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.feed.db.RoomEntity
import com.example.feed.db.RoomSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class Login:  AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var buttonInput: Button
    private lateinit var mDb: RoomSingleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        editText = findViewById(R.id.editText)
        buttonInput = findViewById(R.id.buttonInputSave)
        buttonInput.setOnClickListener {
            println(editText.text.toString())
            editText.setText("")
        }
////        mDb = RoomSingleton.getInstance(applicationContext)
//
//        editText.setOnClickListener {
//            val roomEntity = RoomEntity(
//                date = format.format(Calendar.getInstance().getTime()),
//                amount = amountTaps
//            )
//            lifecycleScope.launch(Dispatchers.IO){
//                mDb.roomDao().insert(roomEntity)
//                println("1")
//            }
//        }
    }
}