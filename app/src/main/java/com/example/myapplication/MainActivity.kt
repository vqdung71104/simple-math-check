package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {

    private lateinit var etNumberRange: EditText
    private lateinit var listViewNumbers: ListView
    private lateinit var btnOdd: Button
    private lateinit var btnPrime: Button
    private lateinit var btnPerfect: Button
    private lateinit var btnEven: Button
    private lateinit var btnSquare: Button
    private lateinit var btnFibonacci: Button

    private var currentNumberType = "odd"
    private var currentRange = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout)

        initViews()
        setupClickListeners()
        setupTextChangeListener()
        showNumbers(currentNumberType)
    }

    private fun initViews() {
        etNumberRange = findViewById(R.id.etNumberRange)
        listViewNumbers = findViewById(R.id.listViewNumbers)
        btnOdd = findViewById(R.id.btnOdd)
        btnPrime = findViewById(R.id.btnPrime)
        btnPerfect = findViewById(R.id.btnPerfect)
        btnEven = findViewById(R.id.btnEven)
        btnSquare = findViewById(R.id.btnSquare)
        btnFibonacci = findViewById(R.id.btnFibonacci)
    }

    private fun setupClickListeners() {
        btnOdd.setOnClickListener {
            currentNumberType = "odd"
            showNumbers("odd")
        }
        btnPrime.setOnClickListener {
            currentNumberType = "prime"
            showNumbers("prime")
        }
        btnPerfect.setOnClickListener {
            currentNumberType = "perfect"
            showNumbers("perfect")
        }
        btnEven.setOnClickListener {
            currentNumberType = "even"
            showNumbers("even")
        }
        btnSquare.setOnClickListener {
            currentNumberType = "square"
            showNumbers("square")
        }
        btnFibonacci.setOnClickListener {
            currentNumberType = "fibonacci"
            showNumbers("fibonacci")
        }
    }

    private fun setupTextChangeListener() {
        etNumberRange.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                try {
                    currentRange = s.toString().toIntOrNull() ?: 100
                    showNumbers(currentNumberType)
                } catch (e: Exception) {
                    // Giữ nguyên range cũ nếu nhập không hợp lệ
                }
            }
        })
    }

    private fun showNumbers(type: String) {
        val numbers = when (type) {
            "odd" -> getOddNumbers()
            "prime" -> getPrimeNumbers()
            "perfect" -> getPerfectNumbers()
            "even" -> getEvenNumbers()
            "square" -> getSquareNumbers()
            "fibonacci" -> getFibonacciNumbers()
            else -> getOddNumbers()
        }

        val numberStrings = numbers.map { it.toString() }

        val adapter = ArrayAdapter(
            this,
            R.layout.item_number,
            R.id.tvNumber,
            numberStrings
        )

        listViewNumbers.adapter = adapter
    }

    // Các hàm tính toán số thực tế
    private fun getOddNumbers(): List<Int> {
        return (1..currentRange).filter { it % 2 != 0 }
    }

    private fun getEvenNumbers(): List<Int> {
        return (1..currentRange).filter { it % 2 == 0 }
    }

    private fun getPrimeNumbers(): List<Int> {
        return (1..currentRange).filter { isPrime(it) }
    }

    private fun getPerfectNumbers(): List<Int> {
        return (1..currentRange).filter { isPerfectNumber(it) }
    }

    private fun getSquareNumbers(): List<Int> {
        return (1..currentRange).filter { isSquareNumber(it) }
    }

    private fun getFibonacciNumbers(): List<Int> {
        return generateFibonacciSequence(currentRange)
    }

    // Các hàm helper
    private fun isPrime(number: Int): Boolean {
        if (number < 2) return false
        for (i in 2 until number) {
            if (number % i == 0) return false
        }
        return true
    }

    private fun isPerfectNumber(number: Int): Boolean {
        if (number < 2) return false
        var sum = 0
        for (i in 1 until number) {
            if (number % i == 0) {
                sum += i
            }
        }
        return sum == number
    }

    private fun isSquareNumber(number: Int): Boolean {
        val sqrt = kotlin.math.sqrt(number.toDouble()).toInt()
        return sqrt * sqrt == number
    }

    private fun generateFibonacciSequence(limit: Int): List<Int> {
        val result = mutableListOf<Int>()
        var a = 0
        var b = 1
        while (a <= limit) {
            result.add(a)
            val temp = a + b
            a = b
            b = temp
        }
        return result
    }
}