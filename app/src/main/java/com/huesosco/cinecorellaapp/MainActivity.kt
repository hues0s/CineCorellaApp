package com.huesosco.cinecorellaapp

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.huesosco.cinecorellaapp.ui.tabs.SectionsPagerAdapter
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import com.huesosco.cinecorellaapp.dialogs.OpcionesDialog


class MainActivity : AppCompatActivity() {


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Si la version lo permite, pintamos la status bar de blanco
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this,R.color.colorPrimary)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }

    fun infoAction(view: View){
        //funcion para el onClick del imageview de info
        val dialog = OpcionesDialog(this.packageName)
        dialog.show(supportFragmentManager, "opciones dialog")
    }


}