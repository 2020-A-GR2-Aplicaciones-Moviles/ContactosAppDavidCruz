package com.cruzdavid.contactoappcruzdavid

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.cruzdavid.contactoappcruzdavid.dataBase.ContactosSQLiteOpenHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_principal_tmp.*
import org.json.JSONException
import org.json.JSONObject


class PrincipalTmp : AppCompatActivity() {
    var contactos = arrayListOf<ContactoModelClass>()
    var selectedContactPosition = 0
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_tmp)
        val titulo = "Manage Contacts"
        val username = intent.getStringExtra(LOGIN_KEY)
        auth = FirebaseAuth.getInstance();
        // Get the support action bar
        val actionBar = supportActionBar

        // Set the action bar title, subtitle and elevation
        if (actionBar != null) {
            actionBar.title = titulo
            actionBar.subtitle = username.substringBefore("@")
            actionBar.elevation = 4.0F
        }
        ConsultarContactos()


        /*contactos.add(ContactoModelClass(1,"Victor","Velepucha","09911223344", "victor.velepucha@epn.edu.ec"))
        contactos.add(ContactoModelClass(2,"Juan","Perez","0991234567", "juan.perez@epn.edu.ec"))
        contactos.add(ContactoModelClass(3,"Michelle","Salinas","0995225599", "michelle.salinas@epn.edu.ec"))
        contactos.add(ContactoModelClass(4,"Rosa","Vallejo","+593995225100", "rosa.vallejo@epn.edu.ec"))
        val contactoAdaptador = ContactoAdapter(this, contactos)
        listViewContacts.adapter = contactoAdaptador*/
       listViewContacts.setOnItemClickListener { parent, view, position, id ->
           selectedContactPosition = position
           Toast.makeText(this, "Clicked item :"+" "+selectedContactPosition,Toast.LENGTH_SHORT).show()
           //editTextFirstName.setText(contactos[selectedContactPosition].firstName.toString())
           //editTextUserId.setText(contactos[selectedContactPosition].userId.toString())
           //editTextLastName.setText(contactos[selectedContactPosition].lastName.toString())
           //editTextPhoneNumber.setText(contactos[selectedContactPosition].phoneNumber.toString())
           //editTextEmailAddress.setText(contactos[selectedContactPosition].emailAddress.toString())
        }
        buttonSave.setOnClickListener {
            val id = editTextUserId.text.toString().toInt()
            val nombre = editTextFirstName.text.toString()
            val apellido = editTextLastName.text.toString()
            val telefono = editTextPhoneNumber.text.toString()
            val email = editTextEmailAddress.text.toString()
            //contactos.add(ContactoModelClass(id,nombre,apellido,telefono, email))
            //SignUpNewUser(id, name, apellido, telefono, email)
            val respuesta = ContactosSQLiteOpenHelper(this).addContacto(
                ContactoModelClass(
                    id,
                    nombre,
                    apellido,
                    telefono,
                    email
                )
            )
            if (respuesta > -1) {
                //val contactoAdaptador = ContactoAdapter(this, contactos)
                //listViewContacts.adapter = contactoAdaptador
                Toast.makeText(this, "Contacto añadido", Toast.LENGTH_LONG).show()
                limpiarCamposEditables()
            } else {
                Toast.makeText(this, "Error al grabar Contacto", Toast.LENGTH_LONG).show()
            }

        }

        buttonView.setOnClickListener {
            val contacted = ContactosSQLiteOpenHelper(this).readContacto()
            listViewContacts.adapter = ContactoAdapter(this, contacted)
            ConsultarContactos()
        }
        buttonUpdate.setOnClickListener {
            contactos[selectedContactPosition].userId = editTextUserId.text.toString().toInt()
            contactos[selectedContactPosition].firstName = editTextFirstName.text.toString()
            contactos[selectedContactPosition].lastName = editTextLastName.text.toString()
            contactos[selectedContactPosition].phoneNumber = editTextPhoneNumber.text.toString()
            contactos[selectedContactPosition].emailAddress = editTextEmailAddress.text.toString()
            listViewContacts.adapter = ContactoAdapter(this, contactos)
            Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_LONG).show()
            limpiarCamposEditables()
        }

        buttonDelete.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Confirmación de Eliminación")
            dialogBuilder.setMessage("¿Esta seguro que desea eliminar el contacto ${contactos[selectedContactPosition].firstName} ${contactos[selectedContactPosition].lastName}?")
            dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->
                contactos.removeAt(selectedContactPosition)
                val contactoAdaptador = ContactoAdapter(this, contactos)
                listViewContacts.adapter = contactoAdaptador
                Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_LONG).show()
                limpiarCamposEditables()
            })
            dialogBuilder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    //pass
                })
            dialogBuilder.create().show()
        }
        /*
        listViewContacts.setOnItemClickListener { parent, view, position, id ->
            selectedContactPosition = position;
            editTextUserId.setText(contactos[selectedContactPosition].userId)
            editTextFirstName.setText(contactos[selectedContactPosition].firstName)
            editTextLastName.setText(contactos[selectedContactPosition].lastName)
            editTextPhoneNumber.setText(contactos[selectedContactPosition].phoneNumber)
            editTextEmailAddress.setText(contactos[selectedContactPosition].emailAddress)
        }
         */

    }

    fun showDialogAlertSimple() {
        AlertDialog.Builder(this)
            .setTitle("Titulo del diálogo")
            .setMessage("Contenido del diálogo.")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón OK pulsado
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón cancel pulsado
                })
            .show()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_logout -> {
                super.onStart()
                Toast.makeText(this, "Saliendo...", Toast.LENGTH_LONG).show()
                val currentUser = auth.currentUser
                if(currentUser != null)
                {
                    FirebaseAuth.getInstance().signOut()
                    var intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                true
            }
            R.id.action_opcion2 -> {
                Toast.makeText(this, "Cargando...", Toast.LENGTH_LONG).show()
                var intent = Intent(this, TeacherListActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
            }
        }

    /*
    fun SignUpNewUser(id:String, name:String, apellido:String, telefono:String, email:String, password:String){
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
    */

    fun ConsultarContactos() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.androidhive.info/contacts/"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val jsonObj = JSONObject(response.toString())
                val contacts = jsonObj.getJSONArray("contacts")
                for (i in 0 until contacts.length()) {
                    val c = contacts.getJSONObject(i)
                    val id = c.getString("id").substring(1).toInt()
                    val name = c.getString("name")
                    val nombre = name.substringBefore(" ")
                    val apellido = name.substringAfter(" ")
                    val email = c.getString("email")
                    //val address = c.getString("address")
                    //val gender = c.getString("gender")
                    val phone = c.getJSONObject("phone")
                    val telefono = phone.getString("mobile")
                    val home = phone.getString("home")
                    //val office = phone.getString("office")
                    val respuesta = ContactosSQLiteOpenHelper(this).addContacto(
                        ContactoModelClass(
                            id,
                            nombre,
                            apellido,
                            telefono,
                            email
                        )
                    )
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error to read Webservice: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.d("MYTAG",error.message)
            }
        )
        queue.add(jsonObjectRequest)
    }



    private fun limpiarCamposEditables() {
            editTextUserId.setText("")
            editTextFirstName.setText("")
            editTextLastName.setText("")
            editTextPhoneNumber.setText("")
            editTextEmailAddress.setText("")
        }

    }


