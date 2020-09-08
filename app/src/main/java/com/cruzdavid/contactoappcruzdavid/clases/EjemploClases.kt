package com.cruzdavid.contactoappcruzdavid.clases

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class EjemploClases {
    class Outer1 {
        private val bar: Int = 1
        class Nested {
            fun foo() = 2
        }
    }
    val demo1 = Outer1.Nested().foo() // == 2
    class Outer2 {
        private val bar: Int = 1
        inner class Inner {
            fun foo() = bar
        }
    }
    val outer = Outer2()
    val demo2 = outer.Inner().foo() // == 1

    open class Base {
        open fun v() {}
        fun nv() {}
    }
    class Derived() : Base() {
        override fun v() {}
    }

    object DataProviderManager {
        fun registerDataProvider(provider: String) {
            // …
        }
    }

    //Kotlin
    val serviceConnection = object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) { }
        override fun onServiceConnected(name: ComponentName?,
                                        service: IBinder?) { }
    }


}