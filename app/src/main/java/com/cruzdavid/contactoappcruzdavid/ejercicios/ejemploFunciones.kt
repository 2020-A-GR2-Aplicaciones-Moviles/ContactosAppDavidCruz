package com.cruzdavid.contactoappcruzdavid.ejercicios

class ejemploFunciones {
    fun sum(a: Int, b:Int) : Int {
        return a + b
    }
    /*
    fun ejemploLlamadasFunciones(){
        presentGently(“World") // Hello. I would like to present you: World
        presentGently(42)   // Hello. I would like to present you: 42

    }
*/
    fun sum1(a: Int, b: Int) = a + b

        fun Imprimir(){

        }
    fun presentGently(v: Any) {
        println("Hello. I would like to present you: $v")
    }

//funciones aninadas
fun printValue(value: String, inBracket: Boolean = true,
               prefix: String = "", suffix: String = "") {
    print(prefix)
    if (inBracket) {
        print("(${value})")
    } else {
        print(value)
    }
    println(suffix)
}
    // printValue("str", suffix = "!") // Prints: (str)!



}