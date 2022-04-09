package com.example.feed

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.feed.db.RoomSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Table : AppCompatActivity() {

    lateinit var tableLayout: TableLayout
    lateinit var row: TableRow
    lateinit var date: TextView
    lateinit var amount: TextView
    private var id: Long = -1
    private lateinit var mDb: RoomSingleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table)

        mDb = RoomSingleton.getInstance(applicationContext)

        id = intent.getLongExtra(Login.USER_ID_ARG, -1)

        tableLayout = findViewById(R.id.table)

        lifecycleScope.launch(Dispatchers.IO) {
            val list = mDb.roomDao().allComponents(id)
            withContext(Dispatchers.Main) {
                for (item in list) {
                    row = TableRow(this@Table)
                    date = TextView(this@Table).apply {
                        text = item.date
                        width = 100
                    }
                    amount = TextView(this@Table).apply {
                        text = "${item.amount}"
                    }
                    row.addView(date)
                    row.addView(amount)
                    tableLayout.addView(row)
                }
            }
        }
    }
}