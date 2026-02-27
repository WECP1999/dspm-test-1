package com.example.examenparcial1

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class SalaryDeductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_salary_deduction)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val etEmployeeName = findViewById<TextInputEditText>(R.id.etEmployeeName)
        val etBaseSalary = findViewById<TextInputEditText>(R.id.etBaseSalary)
        val tilBaseSalary = findViewById<TextInputLayout>(R.id.tilBaseSalary)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnBack.setOnClickListener {
            finish()
        }

        btnCalculate.setOnClickListener {
            val name = etEmployeeName.text.toString()
            val salaryStr = etBaseSalary.text.toString()

            if (name.isEmpty() || salaryStr.isEmpty()) {
                tvResult.text = getString(R.string.error_missing_fields)
                return@setOnClickListener
            }

            val salary = salaryStr.toDoubleOrNull()
            if (salary == null || salary <= 0) {
                tilBaseSalary.error = getString(R.string.error_negative_salary)
                return@setOnClickListener
            }

            val retention = calculateRetention(salary)
            val afp = salary * 0.0725
            val isss = salary * 0.03
            val netSalary = salary - (retention + afp + isss)

            tvResult.text = getString(
                R.string.result_format_salary_deduction,
                name,
                this.formatAmount(retention),
                this.formatAmount(afp),
                this.formatAmount(isss),
                this.formatAmount(netSalary)
            )
        }
    }

    fun calculateRetention(baseSalary: Number): Double {
        val salary = baseSalary.toDouble()
        val retention = when (salary) {
            in 0.01..472.00 -> 0.0
            in 472.01..895.24 -> 0.1*salary + 17.67
            in 895.25..2038.10 -> 0.2*salary + 60.00
            in 2038.11..Double.MAX_VALUE -> 0.3*salary + 288.5
            else -> 0.00
        }

        return retention
    }

    fun formatAmount(amount: Double, locale: Locale = Locale.getDefault()): String {
        val formatter = NumberFormat.getCurrencyInstance(locale)
        return formatter.format(amount)
    }
}