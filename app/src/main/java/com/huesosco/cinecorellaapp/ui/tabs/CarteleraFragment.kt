package com.huesosco.cinecorellaapp.ui.tabs


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.huesosco.cinecorellaapp.R
import com.huesosco.cinecorellaapp.recycler.RecyclerAdapter
import com.huesosco.cinecorellaapp.recycler.RecyclerItemData
import com.huesosco.cinecorellaapp.scraping.ScrapeList
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


class CarteleraFragment : Fragment() {

    private val urlCartelera = "http://www.cinecorella.es"
    private val nombre = "Cartelera"
    private val recyclerList = ArrayList<RecyclerItemData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_cartelera, container, false)

        setUpRecyclerView(view)

        return view
    }

    private fun setUpRecyclerView(v: View){

        val progressBar = v.findViewById<ProgressBar>(R.id.cartelera_progress_bar)
        progressBar.visibility = View.VISIBLE

        val recyclerView = v.findViewById<RecyclerView>(R.id.recycler_view_cartelera)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setItemViewCacheSize(5)

        val adapter = RecyclerAdapter(v.context, recyclerList, fragmentManager!!)

        recyclerView.adapter = adapter

        ScrapeList.getList(nombre, urlCartelera, recyclerList, adapter, progressBar, v.context)

    }

}
