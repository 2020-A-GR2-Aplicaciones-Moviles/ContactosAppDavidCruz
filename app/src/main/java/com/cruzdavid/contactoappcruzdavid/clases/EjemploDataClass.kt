package com.cruzdavid.contactoappcruzdavid.clases

import android.widget.TextView
import com.cruzdavid.contactoappcruzdavid.R

class EjemploDataClass {

    data class Product(var name: String, var price: Double)
    //validaciones con sql
    fun ManageProduct() {
        val productA = Product("Spoon", 30.2)
        println(productA) //toString(), prints: Product(name=Spoon, price=30.2)
        val productB = productA.copy()
        print(productB) // prints: Product(name=Spoon, price=30.2)
        val productC = productA.copy(price = 24.0, name = "Knife")
        print(productB) // prints: Product(name=Knife, price=24.0)
    }

    data class Person(val firstName: String, val lastName: String, val height: Int)
    fun ManagePerson(){
        val person = Person("Igor", "Wojda", 180)
        var (firstName, lastName, height) = person
        println(firstName) // prints: "Igor"
        println(lastName) // prints: "Wojda"
        println(height) // prints: 180
    }
    fun ManagePerson2(){
        val person = Person("Igor", "Wojda", 180)
        var (firstName, _, height) = person
        println(firstName) // prints: "Igor"
        println(height) // prints: 180
    }
    //class Person(var name: String, var age: Int)

    class Person1 {
        var name: String
        private var age: Int = 0

        constructor(name: String, age: Int) {
            this.name = name
            this.age = age
        }constructor(name: String) {
            this.name = name
        }
    }
/*
    lateinit var textView1: TextView
    val textView = findViewById(R.id.textView) as TextView

  */


}