package com.meeweel.carlist.util

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.bumptech.glide.Glide
import com.meeweel.carlist.R
import com.meeweel.carlist.domain.CarBrand
import com.meeweel.carlist.domain.CarColor

fun ImageView.loadPicture(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl)
        .error(R.drawable.ic_no_connection)
        .placeholder(R.drawable.ic_car)
        .into(this)
}

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun Spinner.setBrands(context: Context, array: Array<CarBrand>) {
    this.adapter = ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, array)
}

fun Spinner.setColors(context: Context, array: Array<CarColor>) {
    this.adapter = ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, array)
}