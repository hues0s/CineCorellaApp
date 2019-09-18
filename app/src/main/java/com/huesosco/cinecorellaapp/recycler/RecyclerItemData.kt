package com.huesosco.cinecorellaapp.recycler


data class RecyclerItemData(var imageUrl: String, var title: String,
                            var price: String, var year: String, var country: String,
                            var length: String, var gender: String, var sinopsis: String, var horarios: ArrayList<String>)