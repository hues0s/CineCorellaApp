package com.huesosco.cinecorellaapp.scraping

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.huesosco.cinecorellaapp.recycler.RecyclerAdapter
import com.huesosco.cinecorellaapp.recycler.RecyclerItemData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


class ScrapeList {

    companion object {

        private fun addElementToList(a: Element, urlInfoList: ArrayList<String>){
            val urlParcial = a.child(0).child(0).attr("href")
            urlInfoList.add(urlParcial) //añadimos las url de la pagina propia de la peli
        }

        fun getList(section: String, url: String, recyclerList: ArrayList<RecyclerItemData>, adapter: RecyclerAdapter, pb: ProgressBar, context: Context){

            doAsync {
                val urlInfoList = ArrayList<String>()

                val document = Jsoup.connect(url).get()
                val array = document.getElementsByClass("horarios_list_peli")

                for(a in array){
                    //recorremos los elementos, para ver cual es pelicula de CARTELERA
                    if(section == "Cartelera") {
                        val tipoDePelicula = a.parent().parent().child(0).child(0).text()
                        if (tipoDePelicula == "Cartelera") {
                            addElementToList(a, urlInfoList)
                        }
                    }
                    else{
                        //Si la seccion no es cartelera, NO HAY QUE FILTRAR, por lo que añadimos todas las peliculas que encontremos
                        addElementToList(a, urlInfoList)
                    }
                }

                //ahora ya tenemos un array de string, con los link de peliculas en CARTELERA
                //procedemos a acceder a ellos uno a uno
                var contadorDeErrores = 0
                for(link in urlInfoList){
                    try {
                        val documentPelicula = Jsoup.connect(link).get()
                        val tablaDeDatos = documentPelicula.getElementById("tabla_peli").child(0)

                        val item = RecyclerItemData(
                            "", "", "", "",
                            "", "", "", "", ArrayList()
                        )

                        item.title = tablaDeDatos.child(0).text()
                        item.imageUrl =
                            "http://www.cinecorella.es/" + tablaDeDatos.child(1).child(0).child(0).attr(
                                "src"
                            )
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
                        for (i in 0 until arrayHorarios.childNodeSize() / 2) {
                            item.horarios.add(arrayHorarios.child(i).text())
                        }

                        recyclerList.add(item)
                    }
                    catch (e: Exception){
                        ++contadorDeErrores
                    }
                }

                //cuando actualizamos toda la lista, avisamos al recycler view de que ya esta
                uiThread {
                    adapter.notifyDataSetChanged()
                    pb.visibility = View.GONE
                    if(contadorDeErrores > 0){
                        if(contadorDeErrores == 1) Toast.makeText(context, "Error: No se ha podido cargar 1 película", Toast.LENGTH_LONG).show()
                        else Toast.makeText(context, "Error: No se han podido cargar $contadorDeErrores películas", Toast.LENGTH_LONG).show()
                    }
                }


            }

        }

    }

}