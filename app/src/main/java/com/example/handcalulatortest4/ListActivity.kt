package com.example.handcalulatortest4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import com.example.handcalulatortest4.adapters.MyCustomCursorAdapter
import com.example.handcalulatortest4.databinding.ActivityListBinding
import com.example.handcalulatortest4.helpers.MySqlHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListActivity : AppCompatActivity() {
    companion object {
        val KEY_NAME: String = "name"
        val KEY_AGE: String = "age"
        val KEY_BUTTON: String = "button"
    }

    private lateinit var simpleAdapter: SimpleAdapter
    private lateinit var cursorAdapter: SimpleCursorAdapter
    private lateinit var myCustomCursorAdapter: MyCustomCursorAdapter
    private lateinit var myHelper: MySqlHelper
    private lateinit var binding: ActivityListBinding
    var data: ArrayList<String> = ArrayList()
    val datamap: MutableList<Map<String, Any?>> = mutableListOf()

    val from: Array<String> = arrayOf(KEY_NAME, KEY_AGE, KEY_BUTTON)

    val fromCursor: Array<String> =
        arrayOf(MySqlHelper.NAME_COLUMN, MySqlHelper.LAST_RESULT_COLUMN, MySqlHelper.ID_COLUMN)
    val to: IntArray =
        intArrayOf(R.id.textView_item_name, R.id.textView_item_last_result, R.id.textEdit_itmem_sum_of_results)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))


        myHelper = MySqlHelper(applicationContext)

        customCusorAdapter();


        binding.content.listview.setOnItemClickListener{ p, v, pos , id ->
            Toast.makeText(this,"position:${pos}, Id:${id}", Toast.LENGTH_SHORT).show()
        }

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener { view ->
            SaveDataToDatabase()
            clearText()
        }
    }
    fun customCusorAdapter()
    {
        myCustomCursorAdapter = MyCustomCursorAdapter(applicationContext,myHelper.getResult())
        binding.content.listview.adapter = myCustomCursorAdapter
    }

    fun DynamicSimpleCursorAdapter()
    {
        val cursor = myHelper.getResult()

        cursorAdapter = SimpleCursorAdapter(
            applicationContext,
            R.layout.list_item_custom,
            cursor,
            fromCursor,
            to,
            SimpleAdapter.NO_SELECTION
        )

        binding.content.listview.adapter = cursorAdapter
    }

    fun buttonClicked(view: View)
    {
        Toast.makeText(this,"Button clicked", Toast.LENGTH_SHORT).show()
    }

    fun staticDataSource()
    {
        val names = arrayOf("Ahmed","Ali","Mohammed","Fadi","Ahlam","Amal")

        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                names)

        binding.content.listview.adapter = arrayAdapter ;
    }

    fun simpleStringDynamicDataSource()
    {

        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)

        binding.content.listview.adapter = arrayAdapter ;

    }

    fun ComplexDynamicDataSource()
    {

        simpleAdapter = SimpleAdapter(this,datamap, R.layout.list_item_custom,from,to)


    }

    private fun SaveDataToDatabase() {
        binding.content.apply {
            val result = myHelper.addCompany(
                editTextTextPersonName2.text.toString(),
                editTextNumber.text.toString().toInt(),
                "Address",
                1000.0
            )

            // get the data again from the database
            myCustomCursorAdapter.changeCursor(myHelper.getCompany())
            // notify the adpater
            myCustomCursorAdapter.notifyDataSetChanged()

            Log.d("ListActivity", "Add company result ${result}")
        }

    }

    private fun SaveDataIntoMemeory() {
        val map = mutableMapOf<String, Any?>()
        binding.content.apply {
            map.put(KEY_NAME, editTextTextPersonName2.text.toString())
            map.put(KEY_AGE, editTextNumber.text.toString())
            map.put(KEY_BUTTON, editTextTextPersonName2.text.toString())
            datamap.add(map)
        }
        simpleAdapter.notifyDataSetChanged()

    }

    private fun clearText() {
        binding.content.apply {
            editTextTextPersonName2.setText("")
            editTextNumber.setText("")
        }
    }

}
