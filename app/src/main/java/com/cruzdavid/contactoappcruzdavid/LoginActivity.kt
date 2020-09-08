package com.cruzdavid.contactoappcruzdavid



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_principal_tmp.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.regex.Pattern

@Suppress("UNUSED_VALUE")
class LoginActivity : AppCompatActivity() {
    //val sharedPref = getPreferences(Context.MODE_PRIVATE)
    private lateinit var auth: FirebaseAuth
    private val TAG = "ACCESO"
    private val password_pattern = Pattern.compile(
        "^" +  "(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&_+=])" +  //at least 1 special character
                "(?=\\S+$)" +  //no white spaces
                ".{4,}" +  //at least 4 characters
                "$"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance();
     //LeerDatosDeArchivoPreferencias()
        LeerDatosDeArchivoPreferenciasEncriptado()
/*
        var obj = funciones()
        obj.EjemploFuncionesTipo1()
      */
        buttonRegistro.setOnClickListener {
            val email = editTextTextEmailAddress.text.toString()
            val password = passwordEt.text.toString()
            SignUpNewUser(email, password)
        }
        //Usuario: david.cruz@epn.edu.ec Password: Kerberos_123   --Use esa contraseña ya que primero valida que tenga mayusculas, caractere4s especiales y numeros--
        login_bt.setOnClickListener {
            //var textContent = editTextTextEmailAddress.text.toString().tr
            if (ValidarCuenta()==true){
                EscribirDatosEnArchivoPreferenciasEncriptado()
                EscribirDatosEnArchivoPreferencias()
                val email = editTextTextEmailAddress.text.toString()
                val password = passwordEt.text.toString()
                AutenticarUsuario(email, password)

                //EscribirDatosEnArchivoInterno4()
                //LeerDatosEnArchivoInterno4()
                //EscribirDatosEnArchivoExterno()
                //LeerDatosEnArchivoExterno()
               /* var intent = Intent(this, PrincipalTmp::class.java)
                intent.putExtra(LOGIN_KEY, editTextTextEmailAddress.text.toString())
                startActivity(intent)
                Toast.makeText(this, "VALIDACION EXITOSA", Toast.LENGTH_SHORT).show()
                finish()
                */
            }else{
                Toast.makeText(this, "NO NO NO", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Toast.makeText(this, textContent, Toast.LENGTH_LONG).show()
        }

    }
    fun AutenticarUsuario(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var intent = Intent(this,PrincipalTmp::class.java)
                    intent.putExtra(LOGIN_KEY,auth.currentUser!!.email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(baseContext, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun SignUpNewUser(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "New user saved.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }

                // ...
            }
    }


    fun ValidarCuenta(): Boolean {
        return if (!Patterns.EMAIL_ADDRESS.matcher(editTextTextEmailAddress.text.toString()).matches() or !password_pattern.matcher(passwordEt.text.toString()).matches()) {
            false
            // editTextTextEmailAddress.setError( getString(R.string ))
        } else if (editTextTextEmailAddress.text.toString().isEmpty() or passwordEt.text.toString().isEmpty()) {
             false
        } else { //Patterns.EMAIL_ADDRESS.matcher(editTextTextEmailAddress.toString()).matches() && password_pattern.matcher(passwordEt.toString()).matches()
            true
    }
    }

    fun EscribirDatosEnArchivoPreferencias() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        if(checkBoxRecordar.isChecked){
            val editor = sharedPref.edit()
            editor.putString(LOGIN_KEY,editTextTextEmailAddress.text.toString())
            editor.putString(PASSWORD_KEY, passwordEt.text.toString())
            editor.commit()
        }
        else{
            val editor = sharedPref.edit()
            editor.putString(LOGIN_KEY,"")
            editor.putString(PASSWORD_KEY,"")
            editor.commit()
        }
    }
    fun LeerDatosDeArchivoPreferencias() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        editTextTextEmailAddress.setText(sharedPref.getString(LOGIN_KEY, ""))
        passwordEt.setText(sharedPref.getString(PASSWORD_KEY, ""))
    }

    fun EscribirDatosEnArchivoPreferenciasEncriptado() {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create( "secret_shared_prefs",
            masterKeyAlias,
            this,//context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        if (checkBoxRecordar.isChecked) {
            sharedPreferences.edit()
                .putString(LOGIN_KEY, editTextTextEmailAddress.text.toString())
                .putString(PASSWORD_KEY, passwordEt.text.toString())
                .apply()
        } else {
            sharedPreferences
                .edit()
                .putString(LOGIN_KEY, "")
                .putString(PASSWORD_KEY, "")
                .apply()
        }
    }
    fun LeerDatosDeArchivoPreferenciasEncriptado() {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",//filename
            masterKeyAlias,
            this,//context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        editTextTextEmailAddress.setText(sharedPreferences.getString(LOGIN_KEY, ""))
        passwordEt.setText(sharedPreferences.getString(PASSWORD_KEY, ""))
    }

    fun EscribirDatosEnArchivoInterno4(){
        val texto = "texto" + System.lineSeparator() + "almacenado"
        openFileOutput(CONTACTS_FILENAME, Context.MODE_PRIVATE).bufferedWriter().use { fos ->
            fos.write(texto)
        }
    }

    fun EscribirDatosEnArchivoInterno1(){
        val texto = "texto almacenado"
        val fos: FileOutputStream = openFileOutput("fichero.txt", Context.MODE_PRIVATE)
        fos.write(texto.toByteArray())
        fos.close()
    }

    fun LeerDatosEnArchivoInterno4() {
        openFileInput(CONTACTS_FILENAME).bufferedReader().use {
            val datoLeido = it.readText()
            val textArray = datoLeido.split(System.lineSeparator())
            val login = textArray[0]
            val clave = textArray[1]
        }
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
    fun EscribirDatosEnArchivoExterno(){
        if (isExternalStorageWritable()) {
            FileOutputStream(File(getExternalFilesDir(null),CONTACTS_FILENAME)).bufferedWriter().use { outputStream ->
                outputStream.write("dato1")
                outputStream.write(System.lineSeparator())
                outputStream.write("dato2")
            }
        }
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
    fun LeerDatosEnArchivoExterno(){
        if (isExternalStorageReadable()) {
            FileInputStream(File(getExternalFilesDir(null),CONTACTS_FILENAME)).bufferedReader().use {
                val datoLeido = it.readText()
                val textArray = datoLeido.split(System.lineSeparator())
                val login = textArray[0]
                val password = textArray[1]
            }
        }
    }


}


