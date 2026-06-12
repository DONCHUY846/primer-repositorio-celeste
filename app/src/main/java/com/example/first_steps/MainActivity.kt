package com.example.first_steps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity(),
    CoroutineScope by MainScope(),
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener
{
    private var deviceConnected: Boolean = false
    private var nodeID: String? = null
    private val MESSAGE_PATH = "/mensaje_desde_reloj"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            Toast.makeText(this, "Hola desde Ventana 1", Toast.LENGTH_SHORT).show()
        }

        val botonNavegar = findViewById<Button>(R.id.button_ir_ventana2)
        botonNavegar.setOnClickListener {
            val intent = Intent(this, SegundaVentana::class.java)
            startActivity(intent)
        }

        val btnBaseDatos = findViewById<Button>(R.id.buttonDatos)
        btnBaseDatos.setOnClickListener {
             val intent = Intent(this, VentanaDatos::class.java)
            startActivity(intent)
        }
    }

    // --- LÓGICA DE BANDERAS PARA ENCONTRAR EL CELULAR (Corregido) ---
    private fun descubrirNodos() {
        launch(Dispatchers.IO) {
            try {
                val nodeListTask = Wearable.getNodeClient(applicationContext).connectedNodes
                val nodes = Tasks.await(nodeListTask)

                for (node in nodes) {
                    Log.d("Nodo", node.displayName)
                    Log.d("Nodo id", node.id)
                    nodeID = node.id
                    deviceConnected = true
                }

                if (deviceConnected) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "Celular conectado", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("Error Nodos", e.message ?: "Error desconocido")
            }
        }
    }

    private fun enviarMensajeACelular(texto: String) {
        launch(Dispatchers.IO) {
            try {
                if (nodeID != null) {

                    Wearable.getMessageClient(applicationContext)
                        .sendMessage(nodeID!!, MESSAGE_PATH, texto.toByteArray()).addOnSuccessListener {
                            Log.d("Mensaje enviado", "Mensaje enviado correctamente")
                        }.addOnFailureListener { Log.e("Error", "Error al enviar mensaje") }.await()


                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "Mensaje enviado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "No hay celular vinculado", Toast.LENGTH_SHORT).show()
                    }
                    descubrirNodos()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == "/vibrar") {
            VibracionHelper(this).iniciarVibracion()
        }
        Log.d("Mensaje recibido", "Mensaje recibido: ${String(messageEvent.data)}")
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {}

    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        descubrirNodos()
    }


    override fun onResume() {
        super.onResume()
        try {
            Wearable.getDataClient(this).addListener(this)
            Wearable.getMessageClient(this).addListener(this)
            Wearable.getCapabilityClient(this)
                .addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_REACHABLE)
            descubrirNodos()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            Wearable.getDataClient(this).removeListener(this)
            Wearable.getMessageClient(this).removeListener(this)
            Wearable.getCapabilityClient(this).removeListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}