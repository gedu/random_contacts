package com.teddy.sweatcontacts.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.teddy.sweatcontacts.R
import com.teddy.sweatcontacts.common.extensions.inflate
import com.teddy.sweatcontacts.common.extensions.load
import com.teddy.sweatcontacts.common.view.bindView
import com.teddy.sweatcontacts.model.Contact

class FavoriteAdapter(private val listener: ContactListener) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favorites: List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val rootView = parent.inflate(R.layout.favorite_item_list)
        return FavoriteViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    fun setFavorites(favorites: List<Contact>) {
        this.favorites = favorites
        notifyDataSetChanged()
    }

    override fun getItemCount() = favorites.size

    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val favoriteImg by bindView<ImageView>(R.id.favorite_img)

        fun bind(contact: Contact) {
            favoriteImg.load(contact.picture.thumbnail)
            favoriteImg.contentDescription = contact.fullName
            favoriteImg.setOnClickListener { listener.onContactClicked(favorites[adapterPosition], favoriteImg) }
        }
    }
}