package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun calcularIMC_isCorrect(){
        val imc = MainActivity.calcularIMCLocale(70.0, 1.75)
        assertTrue(
            "O valor do IMC ($imc) deveria estar entre 22.85 e 22.87",
            imc in 22.85..22.87
        )
    }

    @Test
    fun calcularIMC_alturaZero_retornaZero() {
        val imc = MainActivity.calcularIMCLocale(70.0, 0.0)
        assertEquals(0.0, imc, 0.0)
    }

    @Test
    fun classificacaoIMC_isCorrect() {

        assertEquals(R.string.abaixo_peso, MainActivity.obterStatusImcResId(18.0))

        assertEquals(R.string.peso_normal, MainActivity.obterStatusImcResId(22.0))

        assertEquals(R.string.sobrepeso, MainActivity.obterStatusImcResId(27.5))

        assertEquals(R.string.obesidade_grau_1, MainActivity.obterStatusImcResId(32.0))

        assertEquals(R.string.obesidade_grau_3, MainActivity.obterStatusImcResId(45.0))
    }

    @Test
    fun calcularTMB_isCorrect() {
        val peso = 80.0
        val altura = 1.80
        val idade = 25

        val tmbEsperado = 88.36 + (13.4 * peso) + (4.8 * altura) - (5.7 * idade)

         val tmbCalculado = MainActivity.calcularTMBLocale(peso, altura, idade)
         assertEquals(tmbEsperado, tmbCalculado, 0.1)
    }
}