package com.tiorisnanto.myapplication.ui.home.fragment.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.tiorisnanto.myapplication.R

class DataActivity : AppCompatActivity() {

    val dbHandler = DBHelper(this, null)
    var dataList = ArrayList<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
    }

    fun fabClicked(view: View) {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    public override fun onResume() {
        super.onResume()
        loadIntoList()
    }

    fun loadIntoList() {
        dataList.clear()
        val cursor = dbHandler.getAllRow()
        cursor!!.moveToFirst()

        while (!cursor.isAfterLast) {
            val map = HashMap<String, String>()
            map["id"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID))
//            map["name"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME))
//            map["age"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_AGE))
//            map["email"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_EMAIL))
            map["date"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE))
            map["count"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COUNT))
            map["price"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRICE))
            dataList.add(map)

            cursor.moveToNext()
        }
        findViewById<ListView>(R.id.listView).adapter =
            CustomAdapter(this@DataActivity, dataList)
        findViewById<ListView>(R.id.listView).setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", dataList[+i]["id"])
//            intent.putExtra("name", dataList[+i]["name"])
//            intent.putExtra("age", dataList[+i]["age"])
//            intent.putExtra("email", dataList[+i]["email"])
            intent.putExtra("time", dataList[+i]["date"])
            intent.putExtra("count", dataList[+i]["count"])
            intent.putExtra("price", dataList[+i]["price"])

            startActivity(intent)
        }
    }
}