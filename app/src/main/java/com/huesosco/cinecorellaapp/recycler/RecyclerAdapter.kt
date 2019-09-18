package com.huesosco.cinecorellaapp.recycler

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.huesosco.cinecorellaapp.R


class RecyclerAdapter(c: Context, l : ArrayList<RecyclerItemData>, fm : FragmentManager): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val context: Context = c
    private val list = l
    private val fragmentManager = fm


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.progressBar.visibility = View.VISIBLE

        Glide
            .with(context)
            .load(list[position].imageUrl)
            .centerCrop()
            .listener(object: RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false                }

            })
            .into(holder.cartel)

        //resto de elementos
        holder.titulo.text = list[position].title
        holder.precio.text = list[position].price
        holder.año.text = list[position].year
        holder.pais.text = list[position].country
        holder.duracion.text = list[position].length
        holder.genero.text = list[position].gender

        holder.cardView.setOnClickListener {
            val infoDialog = InfoDialog(list[position].sinopsis, list[position].horarios, holder.titulo.text.toString())
            infoDialog.show(fragmentManager, "info dialog")
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val progressBar = view.findViewById<ProgressBar>(R.id.item_progress_bar)
        val cardView = view.findViewById<CardView>(R.id.recycler_item_cardview)

        val cartel = view.findViewById<ImageView>(R.id.item_cartel)
        val titulo = view.findViewById<TextView>(R.id.item_titulo)
        val precio = view.findViewById<TextView>(R.id.item_precio)
        val año = view.findViewById<TextView>(R.id.item_año)
        val pais = view.findViewById<TextView>(R.id.item_pais)
        val duracion = view.findViewById<TextView>(R.id.item_duracion)
        val genero = view.findViewById<TextView>(R.id.item_genero)

    }

}