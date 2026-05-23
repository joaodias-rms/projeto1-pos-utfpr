package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var pesoInput: EditText

    private lateinit var alturaInput: EditText

    private lateinit var imcResult: TextView

    private lateinit var calcular: Button

    private lateinit var limpar: Button

    private lateinit var idadeInput: EditText

    private lateinit var tmbResult: TextView

    private lateinit var statusImcResult: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pesoInput = findViewById(R.id.peso_input)

        alturaInput = findViewById(R.id.altura_input)

        imcResult = findViewById(R.id.imcResult)

        calcular = findViewById(R.id.calcular)

        limpar = findViewById(R.id.limpar)

        tmbResult = findViewById(R.id.tmbResult)

        idadeInput = findViewById(R.id.idade_input)

        statusImcResult = findViewById(R.id.statusImcResult)

        calcular.setOnClickListener {
            calcularIMC()
            calcularTaxaMetabolicaBasal()
        }

        calcular.setOnLongClickListener{
            Toast.makeText(
                this,
                getString(R.string.bot_o_para_calcular_o_imc),
                Toast.LENGTH_SHORT)
                .show()
            true
        }

        limpar.setOnClickListener{
           limparInput()
        }

    }

    private fun limparInput() {
        pesoInput.setText("")
        alturaInput.setText("")
        idadeInput.setText("")
        statusImcResult.text = ""
        imcResult.text = getString(R.string._0_0)
        tmbResult.text = getString(R.string.tmb_0_0)
        pesoInput.requestFocus()
    }




    companion object {
    fun calcularIMCLocale(peso: Double, altura: Double): Double {
        val language = Locale.getDefault().language
        var imc = 0.0
        imc = if (language.equals("en")) {
            783 * (peso / altura.pow(2))
        } else {
            peso / altura.pow(2)
        }
        return imc
    }
        fun calcularTMBLocale(peso: Double, altura: Double, idade: Int): Double {
            val language = Locale.getDefault().language
            return if (language.equals("en")){
                66.0 + (6.2 * peso) + (12.7 * altura) - (6.76 * idade)
            } else{
                88.36 + (13.4 * peso) + (4.8 * altura) - (5.7 * idade)
            }
        }
        fun obterStatusImcResId(imc: Double): Int {
            return when {
                imc < 18.5 -> R.string.abaixo_peso
                imc < 25.0 -> R.string.peso_normal
                imc < 30.0 -> R.string.sobrepeso
                imc < 35.0 -> R.string.obesidade_grau_1
                imc < 40.0 -> R.string.obesidade_grau_2
                else -> R.string.obesidade_grau_3
            }
        }
    }
    private fun calcularIMC() {
        val peso = pesoInput.text.toString().toDoubleOrNull()
        val altura = alturaInput.text.toString().toDoubleOrNull()

        if (peso != null && altura != null) {
            val imc = calcularIMCLocale(peso, altura)
            imcResult.text = String.format("%.2f", imc)
            val resIdClassificacao = when {
                imc < 18.5 -> R.string.abaixo_peso
                imc < 25.0 -> R.string.peso_normal
                imc < 30.0 -> R.string.sobrepeso
                imc < 35.0 -> R.string.obesidade_grau_1
                imc < 40.0 -> R.string.obesidade_grau_2
                else -> R.string.obesidade_grau_3
            }

            val textoClassificacao = getString(resIdClassificacao)
            statusImcResult.text = getString(R.string.condicao_label, textoClassificacao)
        }
    }

    private fun calcularTaxaMetabolicaBasal() {
        val peso = pesoInput.text.toString().toDoubleOrNull()
        val altura = alturaInput.text.toString().toDoubleOrNull()
        val idade = idadeInput.text.toString().toIntOrNull()
        if(peso == null ) pesoInput.error = R.string.peso_error.toString()
        if(altura == null) alturaInput.error = R.string.altura_error.toString()
        if(idade == null) idadeInput.error = R.string.idade_error.toString()

        if (peso != null && altura != null && idade != null) {
            val tmb = calcularTMBLocale(peso, altura, idade)
            tmbResult.text = String.format("%.2f", tmb).plus(" kcal/dia")
        }
    }
}