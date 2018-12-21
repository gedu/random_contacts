package com.teddy.sweatcontacts.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.teddy.sweatcontacts.R
import com.teddy.sweatcontacts.common.view.Resource
import com.teddy.sweatcontacts.common.view.Status
import com.teddy.sweatcontacts.model.Contact
import org.koin.android.viewmodel.ext.android.viewModel

class HomeContactListActivity : AppCompatActivity() {

    private val viewModel by viewModel<HomeContactViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_contact_list)

        setupListeners()

        viewModel.fetchContacts()
    }

    private fun setupListeners() {
        println("TEDDY> $viewModel")
        viewModel.contacts.observe(this, Observer { it?.run { handleContacts(it) } })
    }

    private fun handleContacts(contactResource: Resource<List<Contact>>) {
        when(contactResource.status) {
            Status.LOADING -> Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
            Status.SUCCESS -> Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show()
            Status.ERROR -> Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
        }
    }
}
