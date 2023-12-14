package com.hans.chuckjokes.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.hans.chuckjokes.R

class SearchActivity : AppCompatActivity() {

    private lateinit var searchButton: SearchButton
    private lateinit var searchEditText: SearchEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        searchButton = findViewById(R.id.search_button)
        searchEditText = findViewById(R.id.search_edit_text)

        setMyButtonEnable()
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        searchButton.setOnClickListener {
            val edtTextQuery: EditText = findViewById(R.id.search_edit_text)

            // Create a new instance of ResultFragment with the query
            val resultFragment = ResultFragment.newInstance(edtTextQuery.text.toString())

            // Replace the existing fragment in the container
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, resultFragment)
                .addToBackStack(null)  // Optional: Add the transaction to the back stack
                .commit()
        }
    }


    private fun setMyButtonEnable() {
        val result = searchEditText.text
        searchButton.isEnabled = result != null && result.toString().isNotEmpty()
    }
}