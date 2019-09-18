package com.huesosco.cinecorellaapp

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment


class OpcionesDialog(pn: String) : DialogFragment() {

    private val packageName = pn

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val dialogView = inflater.inflate(R.layout.dialog_opciones, container, false)
        val dialogButton = dialogView.findViewById(R.id.button_opciones_dialog_cerrar) as Button

        val valoraButton = dialogView.findViewById<Button>(R.id.button_opciones_valora)
        val masAppsButton = dialogView.findViewById<Button>(R.id.button_opciones_masapps)
        val sitioWebButton = dialogView.findViewById<Button>(R.id.button_opciones_sitioweb)

        valoraButton.setOnClickListener {
            try{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
            }
            catch (e: ActivityNotFoundException){
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.store.com/store/apps/details?id=$packageName")))
            }
        }

        masAppsButton.setOnClickListener {
            val marketUri = Uri.parse("market://search?q=pub:huesos Co.")
            try {
                startActivity(Intent(Intent.ACTION_VIEW, marketUri))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(dialogView.context, "Error al intentar abrir Google Play", Toast.LENGTH_SHORT).show()
            }
        }

        sitioWebButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cinecorella.es")))
        }


        dialogButton.setOnClickListener {
            dismiss()
        }

        this.isCancelable = false //Para evitar que el dialog se cierre al pulsar fuera, o pulsar la tecla de atras.

        return dialogView
    }

}