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
import java.text.DecimalFormat

class StudentAverageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_average)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etStudentName = findViewById<TextInputEditText>(R.id.etStudentName)
        val etGrade1 = findViewById<TextInputEditText>(R.id.etGrade1)
        val etGrade2 = findViewById<TextInputEditText>(R.id.etGrade2)
        val etGrade3 = findViewById<TextInputEditText>(R.id.etGrade3)
        val etGrade4 = findViewById<TextInputEditText>(R.id.etGrade4)
        val etGrade5 = findViewById<TextInputEditText>(R.id.etGrade5)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        val df = DecimalFormat("#.##")

        btnCalculate.setOnClickListener {
            val name = etStudentName.text.toString()
            val gradeInputs = listOf(etGrade1, etGrade2, etGrade3, etGrade4, etGrade5)
            val grades = mutableListOf<Double>()
            
            if (name.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_missing_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            for (input in gradeInputs) {
                val value = input.text.toString().toDoubleOrNull()
                if (value == null) {
                    Toast.makeText(this, getString(R.string.error_missing_fields), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (value !in 0.0..10.0) {
                    Toast.makeText(this, getString(R.string.error_invalid_grade), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                grades.add(value)
            }

            val average = grades.average()
            val status = if (average >= 6.0) getString(R.string.status_approved) else getString(R.string.status_failed)
            
            tvResult.text = getString(
                R.string.result_format,
                name,
                df.format(average),
                status
            )
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}