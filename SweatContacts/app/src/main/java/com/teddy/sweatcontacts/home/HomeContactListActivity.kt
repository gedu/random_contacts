package com.teddy.sweatcontacts.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.teddy.sweatcontacts.R
import com.teddy.sweatcontacts.common.view.Resource
import com.teddy.sweatcontacts.common.view.Status
import com.teddy.sweatcontacts.common.view.bindView
import com.teddy.sweatcontacts.common.widget.InfiniteRecyclerView
import com.teddy.sweatcontacts.detail.DetailContactActivity
import com.teddy.sweatcontacts.detail.DetailContactFragment
import com.teddy.sweatcontacts.model.Contact
import org.koin.android.viewmodel.ext.android.viewModel

private const val TAG = "HomeContactListActivity"

class HomeContactListActivity : AppCompatActivity(), HomeContactAdapter.ContactListener {

    private val contactList by bindView<InfiniteRecyclerView>(R.id.contact_list)

    private val viewModel by viewModel<HomeContactViewModel>()

    private val contactsAdapter = HomeContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_contact_list)

        setupContactRecycler()
        setupListeners()

        viewModel.fetchContacts()
    }

    private fun setupContactRecycler() {
        contactList.layoutManager = GridLayoutManager(this, 3)
        contactList.adapter = contactsAdapter
    }

    private fun setupListeners() {
        println("TEDDY> $viewModel")
        viewModel.contacts.observe(this, Observer { it?.run { handleContacts(it) } })

        contactList.reachBottom.observe(this, Observer { it?.run { handleMoreContactLoading(it) } })
    }

    private fun handleContacts(contactResource: Resource<List<Contact>>) {
        when(contactResource.status) {
            Status.LOADING -> Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
            Status.SUCCESS -> {
                println("TEDDY> ${contactResource.data}")
                contactsAdapter.setContacts(contactResource.data ?: listOf())
            }
            Status.ERROR -> Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleMoreContactLoading(isLoading: Boolean) {

    }

    override fun onContactClicked(contact: Contact) {
        if (resources.getBoolean(R.bool.dual_panel)) {
            val fragment = DetailContactFragment.newInstance(contact)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.contact_detail_container, fragment, TAG)
                .commit()

        } else {
            startActivity(DetailContactActivity.newIntent(this, contact))
        }
    }
}
