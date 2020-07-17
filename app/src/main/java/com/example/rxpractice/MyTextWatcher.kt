package com.example.rxpractice

import android.text.Editable
import android.text.TextWatcher

class MyTextWatcher(val callback: (text: String) -> Unit) : TextWatcher {
    override fun afterTextChanged(s: Editable) {
        callback(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

}
