package com.huesosco.cinecorellaapp.ui.tabs


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.huesosco.cinecorellaapp.R
import com.huesosco.cinecorellaapp.recycler.RecyclerAdapter
import com.huesosco.cinecorellaapp.recycler.RecyclerItemData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup


class ProximamenteFragment : Fragment() {

    private val urlProximamente = "http://www.cinecorella.es/index.php?id=2"

    private val recyclerList = ArrayList<RecyclerItemData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_proximamente, container, false)

        setUpRecyclerView(view)

        return view
    }

    private fun setUpRecyclerView(v: View){

        val progressBar = v.findViewById<ProgressBar>(R.id.proximamente_progress_bar)
        progressBar.visibility = View.VISIBLE

        val recyclerView = v.findViewById<RecyclerView>(R.id.recycler_view_proximamente)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setItemViewCacheSize(5)

        val adapter = RecyclerAdapter(v.context, recyclerList, fragmentManager!!)

        recyclerView.adapter = adapter

        getFullList(adapter, progressBar)


    }

    private fun getFullList(adapter: RecyclerAdapter, pb: ProgressBar){

        doAsync {
            val urlInfoList = ArrayList<String>()

            val document = Jsoup.connect(urlProximamente).get()
            val array = document.getElementsByClass("horarios_list_peli")

            for(a in array){
                //recorremos los elementos, para ver cual es pelicula de CARTELERA
                //val tipoDePelicula = a.parent().parent().child(0).child(0).text()
                //if(tipoDePelicula == "Cartelera"){
                    val urlParcial = a.child(0).child(0).attr("href")
                    urlInfoList.add(urlParcial) //añadimos las url de la pagina propia de la peli
                //}
            }

            //ahora ya tenemos un array de string, con los link de peliculas en CARTELERA
            //procedemos a acceder a ellos uno a uno
            for(link in urlInfoList){
                val documentPelicula = Jsoup.connect(link).get()
                val tablaDeDatos = documentPelicula.getElementById("tabla_peli").child(0)

                val item = RecyclerItemData("","","", "",
                    "", "", "", "", ArrayList())

                item.title = tablaDeDatos.child(0).text()
                item.imageUrl = "http://www.cinecorella.es/" + tablaDeDatos.child(1).child(0).child(0).attr("src")
                item.price = tablaDeDatos.child(2).child(1).text()
                item.year = tablaDeDatos.child(3).child(1).text()
                item.country = tablaDeDatos.child(4).child(1).text()
                item.length = tablaDeDatos.child(5).child(1).text()
                item.gender = tablaDeDatos.child(6).child(1).text()

                //Separo la lectura de la sinopsis en ifs porque he observado que a veces varia la estructura de la web
                if(tablaDeDatos.child(8).child(1).hasText()){
                    item.sinopsis = tablaDeDatos.child(8).child(1).text()
                }
                else {
                    item.sinopsis = tablaDeDatos.child(8).child(1).child(0).child(1).text()
                }

                val arrayHorarios = tablaDeDatos.child(1).child(2)
                for(i in 0 until arrayHorarios.childNodeSize()/2){
                    item.horarios.add(arrayHorarios.child(i).text())
                }

                recyclerList.add(item)
            }

            //cuando actualizamos toda la lista, avisamos al recycler view de que ya esta
            uiThread {
                adapter.notifyDataSetChanged()
                pb.visibility = View.GONE
            }


        }

    }


}
