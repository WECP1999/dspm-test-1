package com.example.examenparcial1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnStudentAverage = findViewById<Button>(R.id.btnStudentAverage)
        val btnSalaryDeduction = findViewById<Button>(R.id.btnSalaryDeduction)
        val btnCalculator = findViewById<Button>(R.id.btnCalculator)

        btnStudentAverage.setOnClickListener {
            val intent = Intent(this, StudentAverageActivity::class.java)
            startActivity(intent)
        }

        btnSalaryDeduction.setOnClickListener {
            val intent = Intent(this, SalaryDeductionActivity::class.java)
            startActivity(intent)
        }

        btnCalculator.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }
    }
}