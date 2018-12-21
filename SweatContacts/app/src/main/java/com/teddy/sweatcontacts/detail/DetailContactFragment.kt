package com.teddy.sweatcontacts.detail


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.teddy.sweatcontacts.R
import com.teddy.sweatcontacts.common.extensions.load
import com.teddy.sweatcontacts.common.extensions.rightImage
import com.teddy.sweatcontacts.common.view.bindView
import com.teddy.sweatcontacts.model.Contact

private const val CONTACT_KEY = "detailFragment.CONTACT_KEY"

class DetailContactFragment : Fragment() {

    private val contactImage by bindView<ImageView>(R.id.contact_image)
    private val contactFullname by bindView<TextView>(R.id.contact_name_gender)
    private val contactAge by bindView<TextView>(R.id.contact_age)
    private val contactEmail by bindView<TextView>(R.id.contact_email)
    private val contactPhoneNumber by bindView<TextView>(R.id.contact_phone)

    companion object {
        fun newInstance(contact: Contact): DetailContactFragment {
            return DetailContactFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable(CONTACT_KEY, contact)
                arguments = bundle
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_contact, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contact: Contact? = arguments?.getParcelable(CONTACT_KEY)

        if (contact != null) {
            contactImage.load(contact.picture.large)
            contactFullname.text = "${contact.name.first} ${contact.name.last}"
            contactFullname.rightImage(contact.getGenderRes())
            contactEmail.text = contact.email
            contactPhoneNumber.text = contact.phone
        }

    }

}
