package com.example.handcalulatortest4.adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.handcalulatortest4.helpers.MySqlHelper

class MyCustomCursorAdapter(context: Context, cursor: Cursor?): CursorAdapter(context,cursor,0) {
    private class ViewHolder {
        var textViewName: TextView? = null
        var textViewLastSumOfResults: TextView? = null
        var textEditLastResult: EditText? = null
    }
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {

        val layoutInflater = LayoutInflater.from(context)
        val newView = layoutInflater.inflate(R.layout.list_item_custom,parent,false)
        val viewHolder = ViewHolder()
        viewHolder.textViewName = newView.findViewById(R.id.textView_item_name)
        viewHolder.textViewLastSumOfResults = newView.findViewById(R.id.textView_item_age)
        viewHolder.textEditLastResult = newView.findViewById(R.id.textView_item_last_result)

        // store the viewHolder object with the new view using the tag
        newView.tag = viewHolder

        return  newView

    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {

        // get the viewHolder from the newView object via the Tag object
        val viewHolder = view!!.tag as ViewHolder
        viewHolder.textViewName?.text = cursor?.getString(cursor.getColumnIndex(MySqlHelper.NAME_COLUMN))
        viewHolder.textViewAge?.text = cursor?.getString(cursor.getColumnIndex(MySqlHelper.AGE_COLUMN))
        viewHolder.buttonAdd?.text = cursor?.getString(cursor.getColumnIndex(MySqlHelper.ID_COLUMN))

        viewHolder.buttonAdd?.setOnClickListener{
            val btn = it as Button
            Toast.makeText(context,"Button clicked ${btn.text.toString()}", Toast.LENGTH_SHORT).show()
        }

    }

}