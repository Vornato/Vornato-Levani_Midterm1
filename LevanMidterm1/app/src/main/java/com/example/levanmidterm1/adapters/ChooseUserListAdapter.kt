package com.example.levanmidterm1.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.levanmidterm1.R

class ChooseUserListAdapter(private val context: Activity, private val emails: ArrayList<String>)
        :ArrayAdapter<String>(context, R.layout.list_view_row, emails) {

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_view_row, null, true)

        val userEmail = rowView.findViewById(R.id.userEmail) as TextView

        userEmail.text = emails[position]

        return rowView
    }

}