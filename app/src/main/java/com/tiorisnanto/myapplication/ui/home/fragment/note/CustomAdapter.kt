package com.tiorisnanto.myapplication.ui.home.fragment.note

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.tiorisnanto.myapplication.R

class CustomAdapter(private val context: Context,
                    private val dataList: ArrayList<HashMap<String, String>>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int { return dataList.size }
    override fun getItem(position: Int): Int { return position }
    override fun getItemId(position: Int): Long { return position.toLong() }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dataitem = dataList[position]

        val rowView = inflater.inflate(R.layout.list_row, parent, false)
        rowView.findViewById<TextView>(R.id.row_name).text = dataitem["name"]
        rowView.findViewById<TextView>(R.id.row_age).text = "Age: " + dataitem["age"]
        rowView.findViewById<TextView>(R.id.row_email).text = "Email: " + dataitem["email"]
        rowView.findViewById<TextView>(R.id.row_date).text = "Date: " + dataitem["date"]

        rowView.tag = position
        return rowView
    }
}