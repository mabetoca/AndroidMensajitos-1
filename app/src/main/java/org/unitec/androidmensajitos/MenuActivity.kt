package org.unitec.androidmensajitos

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var estatus = Estatus()
    var mensaje=Mensaje()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        //Mostramos solo el contenido0 principalk
        ocultarTodo()
        val principal = findViewById(R.id.contenido_principal) as ConstraintLayout
        principal.visibility = View.VISIBLE


        //Para guardar
       var botonGuardar=findViewById<Button>(R.id.botonGuardar).setOnClickListener{
           TareaMensaje().execute(null, null, null)

           Toast.makeText(applicationContext,"Mensaje guardardo", Toast.LENGTH_LONG).show()

        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_principal -> {

                ocultarTodo()
                val principal = findViewById(R.id.contenido_principal) as ConstraintLayout
                principal.visibility = View.VISIBLE

            }
            R.id.nav_guardar -> {

                ocultarTodo()
                val principal = findViewById(R.id.guardar_mensaje) as ConstraintLayout
                principal.visibility = View.VISIBLE

            }
            R.id.nav_buscartodos -> {

            }
            R.id.nav_buscarporid -> {

            }
            R.id.nav_actualizar -> {

            }
            R.id.nav_borrar -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    fun ocultarTodo(){

        val principal = findViewById(R.id.contenido_principal) as ConstraintLayout
        val mensajes = findViewById(R.id.guardar_mensaje) as ConstraintLayout
        principal.visibility = View.INVISIBLE
        mensajes.visibility = View.INVISIBLE

    }

    inner class TareaMensaje : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg p0: Void?): Void? {




            var url2="https://jc-elementos.herokuapp.com/api/mensaje"

            val restTemplate = RestTemplate()
            restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())


            val maper = ObjectMapper()
            //  usuarios = maper.readValue(estring, object : TypeReference<ArrayList<Usuario>>() {})

            val respuesta = restTemplate.postForObject(url2, mensaje, String::class.java)
            estatus = maper.readValue(respuesta,Estatus::class.java )
            print(estatus.mensaje)

            println("DESPUES DE REST");
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            //Se piden las componentes
        var eCuerpo=     findViewById<EditText>(R.id.textoCuerpo)
        mensaje.cuerpo=    eCuerpo.text.toString();
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Toast.makeText(applicationContext,estatus.mensaje, Toast.LENGTH_LONG).show();
           findViewById<EditText>(R.id.textoCuerpo).text=null
        }
    }
}
