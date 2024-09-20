package com.example.currencyconverter

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var amountInput: EditText
    private lateinit var sourceCurrencySpinner: Spinner
    private lateinit var destinationCurrencySpinner: Spinner
    private lateinit var resultText: TextView
    private lateinit var convertButton: Button

    private val exchangeRateUSD = 1.0 // Tasa para USD
    private val exchangeRateEUR = 0.85 // Tasa para EUR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Manejo de la barra de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        amountInput = findViewById(R.id.amount_input)
        sourceCurrencySpinner = findViewById(R.id.source_currency_spinner)
        destinationCurrencySpinner = findViewById(R.id.destination_currency_spinner)
        resultText = findViewById(R.id.result_text)
        convertButton = findViewById(R.id.convert_button)

        // Configurar Spinners
        val adapter = ArrayAdapter.createFromResource(this, R.array.currencies_array, R.layout.spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceCurrencySpinner.adapter = adapter
        destinationCurrencySpinner.adapter = adapter

        // Configurar botón de conversión
        convertButton.setOnClickListener { convertCurrency() }
    }

    private fun convertCurrency() {
        val amount = amountInput.text.toString().toDoubleOrNull()
        if (amount != null) {
            val sourceCurrency = sourceCurrencySpinner.selectedItem.toString()
            val destinationCurrency = destinationCurrencySpinner.selectedItem.toString()
            var result = 0.0

            when (sourceCurrency) {
                "USD" -> result = when (destinationCurrency) {
                    "EUR" -> amount * exchangeRateEUR
                    else -> amount
                }
                "EUR" -> result = when (destinationCurrency) {
                    "USD" -> amount / exchangeRateEUR
                    else -> amount
                }
            }

            resultText.text = String.format("Resultado: %.2f", result)
        } else {
            resultText.text = "Por favor, introduce una cantidad válida."
        }
    }
}
