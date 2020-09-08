package com.cruzdavid.contactoappcruzdavid.ejercicios

class listas {
    fun EjemploListaMutableEnteros(){
        fun MutableList<Int>.swap(index0: Int, index1: Int) {
            val tmp = this[index0]
            this[index0] = this[index1]
            this[index1] = tmp
        }
        var myListInt : MutableList<Int> = mutableListOf(1,2)
        myListInt.swap(1,0)
        println("$myListInt[0],myListInt[1]")
    }


}