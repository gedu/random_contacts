package com.teddy.sweatcontacts.home.search

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.teddy.sweatcontacts.R
import com.teddy.sweatcontacts.common.extensions.inflate
import com.teddy.sweatcontacts.common.view.bindView
import com.teddy.sweatcontacts.home.ContactListener
import com.teddy.sweatcontacts.model.Contact

class ContactSearchAdapter(
    private var contacts: List<Contact>,
    private val listener: ContactListener
) : RecyclerView.Adapter<ContactSearchAdapter.ContactSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactSearchViewHolder {
        val view = parent.inflate(R.layout.contact_search_item_list)
        return ContactSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactSearchViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    fun setSearch(newSearch: List<Contact>) {
        contacts = newSearch
        notifyDataSetChanged()
    }

    override fun getItemCount() = contacts.size

    inner class ContactSearchViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        private val name by bindView<TextView>(R.id.contact_name)

        fun bind(contact: Contact) {
            name.text = contact.fullName
            name.setOnClickListener { listener.onContactClicked(contacts[adapterPosition]) }
        }
    }
}