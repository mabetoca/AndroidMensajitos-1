package org.unitec.androidmensajitos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //manejamos el evento de boton igual de manera funcional como en spring

        var boton=findViewById<Button>(R.id.ingresar).setOnClickListener{
      var   intent= Intent(applicationContext, MenuActivity::class.java)
            startActivity(intent);
        }
    }
}
