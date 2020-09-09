package com.cruzdavid.contactoappcruzdavid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TeacherListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_list)
        val titulo = "Teachers List"
        val actionBar = supportActionBar
        // Set the action bar title, subtitle and elevation
        if (actionBar != null) {
            actionBar.title = titulo
            actionBar.elevation = 4.0F
        }
    }

    fun getAnimalsLinks(): Array<String> {
        return arrayOf(
            "http://fis.epn.edu.ec/images/Docentes/AdrianEguez.PNG",
            "http://fis.epn.edu.ec/images/Docentes/JosafaAguiar.PNG",
            "http://fis.epn.edu.ec/images/Docentes/AndresLarco.PNG",
            "http://fis.epn.edu.ec/images/Docentes/AndresCevallos.PNG",
            "http://fis.epn.edu.ec/images/Docentes/Carlos-Montenegro.PNG",
            "http://fis.epn.edu.ec/images/Docentes/EdisonLoza.PNG",
            "http://fis.epn.edu.ec/images/Docentes/HernanOrdoez.PNG",
            "http://fis.epn.edu.ec/images/Docentes/JennyTorres.PNG",
            "http://fis.epn.edu.ec/images/Docentes/Jhonatan-Barriga.PNG",
            "http://fis.epn.edu.ec/images/Docentes/JoseLucio.PNG",
            "http://fis.epn.edu.ec/images/Docentes/MarcoBenalcazar.jpg",
            "http://fis.epn.edu.ec/images/Docentes/MarcoSantorum.PNG",
            "http://fis.epn.edu.ec/images/Docentes/Maria-Perez.PNG",
            "http://fis.epn.edu.ec/images/Docentes/MaritzolTenemaza.PNG",
            "http://fis.epn.edu.ec/images/Docentes/mayracarrion.jpg",
            "http://fis.epn.edu.ec/images/Docentes/MonserratIntriago.PNG",
            "http://fis.epn.edu.ec/images/Docentes/foto-MH2.jpg",
            "http://fis.epn.edu.ec/images/Docentes/PAME.png",
            "http://fis.epn.edu.ec/images/Docentes/RaulCordova.PNG",
            "http://fis.epn.edu.ec/images/Docentes/Sandra-Sanchez.PNG",
            "http://fis.epn.edu.ec/images/Docentes/RodrigoChancusig.PNG",
            "http://fis.epn.edu.ec/images/Docentes/RosaNavarrete.PNG",
            "http://fis.epn.edu.ec/images/Docentes/TaniaCalle.jpg",
            "http://fis.epn.edu.ec/images/Docentes/Walter-Fuertes.PNG"

        )
    }
}