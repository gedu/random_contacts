package com.teddy.sweatcontacts.common.extensions

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(resId: Int): View {
    return LayoutInflater.from(context).inflate(resId, this, false)
}

fun ImageView.load(url: String) {
    Picasso.get().load(url).into(this)
}

fun TextView.rightImage(resId: Int) {
    val drawable = ContextCompat.getDrawable(context, resId)
    setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
}