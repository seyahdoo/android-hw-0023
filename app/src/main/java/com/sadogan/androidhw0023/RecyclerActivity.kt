package com.sadogan.androidhw0023

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_test.*


class RecyclerActivity : AppCompatActivity() {

    // Initializing an empty ArrayList to be filled with grades
    private val grades: ArrayList<Grade> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_test)

        // Loads grades into the ArrayList
        addGrades()

        // Creates a vertical Layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        //recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it
        recyclerView.adapter = MyListAdepter(grades, this)

    }

    // Adds grades to the empty grades ArrayList
    fun addGrades() {
        grades.add(Grade("Amac","AA"))
        grades.add(Grade("Amac","AA"))
        grades.add(Grade("Amac","AA"))
        grades.add(Grade("Amac","AA"))
    }



}
