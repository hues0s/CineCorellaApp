package com.huesosco.cinecorellaapp.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.huesosco.cinecorellaapp.R
import android.app.SearchManager
import android.content.Intent




class InfoDialog(s: String, h: ArrayList<String>, t: String): DialogFragment() {

    private val sinopsis = s
    private val horarios = h
    private val tituloPelicula = t

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val dialogView = inflater.inflate(R.layout.dialog_info, container, false)
        val dialogTextHorarios = dialogView.findViewById(R.id.text_info_dialog_horarios) as TextView
        val dialogTextSinopsis = dialogView.findViewById(R.id.text_info_dialog_sinopsis) as TextView
        val dialogButton = dialogView.findViewById(R.id.button_info_dialog_ok) as Button
        val moreInfoButton = dialogView.findViewById(R.id.button_info_dialog_masinfo) as Button

        dialogTextHorarios.text = procesarTextoDelHorario()
        dialogTextSinopsis.text = sinopsis

        dialogButton.setOnClickListener {
            dismiss()
        }

        moreInfoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, tituloPelicula)
            startActivity(intent)
        }

        this.isCancelable = false //Para evitar que el dialog se cierre al pulsar fuera, o pulsar la tecla de atras.

        return dialogView
    }

    private fun procesarTextoDelHorario(): String{
        var stringFinal = ""

        for(i in 0 until horarios.size - 1){
            stringFinal += horarios[i] + "\n"
        }
        stringFinal += horarios[horarios.size - 1]

        return stringFinal
    }

}