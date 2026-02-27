package com.example.examenparcial1

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.pow
import kotlin.math.sqrt

class CalculatorActivity : AppCompatActivity() {

    private lateinit var etNumber1: TextInputEditText
    private lateinit var etNumber2: TextInputEditText
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNumber1 = findViewById(R.id.etNumber1)
        etNumber2 = findViewById(R.id.etNumber2)
        tvResult = findViewById(R.id.tvResult)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        findViewById<Button>(R.id.btnSum).setOnClickListener { calculate('+') }
        findViewById<Button>(R.id.btnSub).setOnClickListener { calculate('-') }
        findViewById<Button>(R.id.btnMul).setOnClickListener { calculate('*') }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { calculate('/') }
        findViewById<Button>(R.id.btnSqrt).setOnClickListener { calculate('s') }
        findViewById<Button>(R.id.btnPow).setOnClickListener { calculate('p') }
    }

    private fun calculate(operation: Char) {
        val n1Str = etNumber1.text.toString()
        val n2Str = etNumber2.text.toString()

        if (n1Str.isEmpty() && (operation != 's' || n1Str.isEmpty())) {
            etNumber1.error = getString(R.string.error_invalid_input)
            return
        }

        val n1 = n1Str.toDoubleOrNull() ?: 0.0
        val n2 = n2Str.toDoubleOrNull() ?: 0.0

        val result = when (operation) {
            '+' -> n1 + n2
            '-' -> n1 - n2
            '*' -> n1 * n2
            '/' -> {
                if (n2 == 0.0) {
                    tvResult.text = getString(R.string.error_division_zero)
                    return
                }
                n1 / n2
            }
            's' -> {
                if (n1 < 0) {
                    tvResult.text = getString(R.string.error_negative_sqrt)
                    return
                }
                sqrt(n1)
            }
            'p' -> n1.pow(n2)
            else -> 0.0
        }

        tvResult.text = getString(R.string.result_label, result.toString())
    }
}