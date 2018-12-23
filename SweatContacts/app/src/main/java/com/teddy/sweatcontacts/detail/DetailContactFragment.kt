package com.teddy.sweatcontacts.detail


import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
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
import com.teddy.sweatcontacts.common.widget.ClickableLottieView
import com.teddy.sweatcontacts.model.Contact
import org.koin.android.viewmodel.ext.android.viewModel

private const val CONTACT_KEY = "detailFragment.CONTACT_KEY"

class DetailContactFragment : Fragment() {

    private val contactImage by bindView<ImageView>(R.id.contact_image)
    private val contactFullName by bindView<TextView>(R.id.contact_name_gender)
    private val contactAge by bindView<TextView>(R.id.contact_age)
    private val contactEmail by bindView<TextView>(R.id.contact_email)
    private val contactPhoneNumber by bindView<TextView>(R.id.contact_phone)
    private val contactFavoriteBtn by bindView<ClickableLottieView>(R.id.contact_favorite_btn)

    private val viewModel by viewModel<DetailContactViewModel>()

    private var setupFinished = false

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

        setupListener()

        setupContact()
    }

    private fun setupContact() {
        val contact: Contact? = arguments?.getParcelable(CONTACT_KEY)
        if (contact != null) {
            viewModel.addContact(contact)

        }
    }

    private fun setupListener() {

        viewModel.favoriteState.listen { isFavorite ->
            if (setupFinished) {
                if (isFavorite) {
                    contactFavoriteBtn.playForward()
                } else {
                    contactFavoriteBtn.playReverse()
                }
            }
            setupFinished = true
        }

        viewModel.currentContact.listen { contact ->

            contactImage.load(contact.picture.large)
            contactFullName.text = contact.fullName
            contactFullName.rightImage(contact.getGenderRes())
            contactAge.text = contact.age
            contactEmail.text = contact.email
            contactPhoneNumber.text = contact.phone

            if (contact.favorite) {
                contactFavoriteBtn.startFromEnd()
            } else {
                contactFavoriteBtn.startFromStart()
            }

        }

        contactFavoriteBtn.setOnClickListener { viewModel.updateFavoriteState() }
    }

    private fun <T> LiveData<T>.listen(l: (r: T) -> Unit) {
        observe(this@DetailContactFragment, Observer { it?.run { l(it) } })
    }
}
