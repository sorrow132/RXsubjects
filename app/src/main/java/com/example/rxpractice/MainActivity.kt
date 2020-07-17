package com.example.rxpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.subjects.BehaviorSubject

class MainActivity : AppCompatActivity(), InvalidationCallback {
    lateinit var editText1: EditText
    lateinit var editText2: EditText
    lateinit var editText3: EditText
    lateinit var button: Button

    private val behavior1: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val behavior2: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val behavior3: BehaviorSubject<String> = BehaviorSubject.createDefault("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText1 = findViewById(R.id.edit1)
        editText2 = findViewById(R.id.edit2)
        editText3 = findViewById(R.id.edit3)
        button = findViewById(R.id.sign_in_button)

        button.isClickable = false

        Observable
            .combineLatest(
                behavior1
                    .map {
                        it.length > 5
                    },
                behavior2
                    .map {
                        it.indexOf("1") != -1
                    },
                behavior3,
                Function3 { t1: Boolean, t2: Boolean, t3: String ->
                    (t1 && t2 && t3.isNotEmpty()) || t3 == "Roma good"
                }
            )
            .subscribe { isEnabled ->
                button.isEnabled = isEnabled
            }

        editText1.addTextChangedListener(MyTextWatcher {
            behavior1.onNext(it)

        })
        editText2.addTextChangedListener(MyTextWatcher {
            behavior2.onNext(it)
        })
        editText3.addTextChangedListener(MyTextWatcher {
            behavior3.onNext(it)
        })

        invalidateForm()
    }

    private fun invalidateForm() {
        val field1: String = editText1.text.toString()
        val field2: String = editText2.text.toString()
        val field3: String = editText3.text.toString()

        button.isEnabled = field1.isNotEmpty() && field2.isNotEmpty() && field3.isNotEmpty()
    }

    override fun invalidation() {
        invalidateForm()
    }

}

interface InvalidationCallback {
    fun invalidation()

}