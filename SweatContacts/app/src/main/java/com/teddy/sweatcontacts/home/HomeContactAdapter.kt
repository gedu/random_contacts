package com.teddy.sweatcontacts.home

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.teddy.sweatcontacts.R
import com.teddy.sweatcontacts.common.extensions.inflate
import com.teddy.sweatcontacts.common.extensions.load
import com.teddy.sweatcontacts.common.view.bindView
import com.teddy.sweatcontacts.common.widget.VIEW_CONTENT_TYPE
import com.teddy.sweatcontacts.common.widget.VIEW_LOADING_TYPE
import com.teddy.sweatcontacts.model.Contact

interface ContactListener {
    fun onContactClicked(contact: Contact, imageView: ImageView? = null)
}

class HomeContactAdapter(private val listener: ContactListener) : RecyclerView.Adapter<BaseViewHolder>() {

    private var contacts: MutableList<Contact?> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == VIEW_LOADING_TYPE) {
            val rootView = parent.inflate(R.layout.contact_loading_item_list)
            LoadingViewHolder(rootView)
        } else {
            val rootView = parent.inflate(R.layout.contact_item_list)
            ContactViewHolder(rootView)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is ContactViewHolder) {
            holder.bind(contacts[position]!!)
        }
    }

    fun setContacts(newContacts: List<Contact>) {
        contacts = newContacts.toMutableList()
        notifyDataSetChanged()
    }

    fun addContacts(newContacts: List<Contact>) {
        val startSize = contacts.size
        contacts.addAll(newContacts)
        notifyItemRangeInserted(startSize, newContacts.size)
    }

    fun addLoading() {
        contacts.addAtEnd(null)
        notifyItemInserted(contacts.lastIndex)
    }

    fun removeLoading() {
        contacts.removeLast()
        notifyItemRemoved(contacts.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (contacts[position] == null) VIEW_LOADING_TYPE else VIEW_CONTENT_TYPE
    }

    override fun getItemCount() = contacts.size

    inner class ContactViewHolder(rootView: View) : BaseViewHolder(rootView) {

        private val contactImage by bindView<ImageView>(R.id.contact_image)
        private val contactTitle by bindView<TextView>(R.id.contact_name_gender)

        init {
            rootView.setOnClickListener {
                listener.onContactClicked(contacts[adapterPosition]!!, contactImage)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(contact: Contact) {
            contactImage.load(contact.picture.medium)
            contactTitle.text = contact.fullName
        }
    }

    inner class LoadingViewHolder(rootView: View) : BaseViewHolder(rootView)
}

open class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view)

fun <E> MutableList<E>.removeLast() {
    this.removeAt(this.lastIndex)
}

fun <E> MutableList<E>.addAtEnd(item: E) {
    this.add(this.size, item)
}