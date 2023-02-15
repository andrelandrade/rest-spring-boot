package com.example.math

class SimpleMath {
    fun sum(numberOne: Double, numberTwo: Double) = numberOne + numberTwo
    fun subtraction(numberOne: Double, numberTwo: Double) = numberOne - numberTwo
    fun multiplication(numberOne: Double, numberTwo: Double) = numberOne * numberTwo
    fun division(numberOne: Double, numberTwo: Double) = numberOne / numberTwo
    fun avg(numberOne: Double, numberTwo: Double) = sum(numberOne,numberTwo) / 2
    fun sqrt(number: Double) = kotlin.math.sqrt(number)
}