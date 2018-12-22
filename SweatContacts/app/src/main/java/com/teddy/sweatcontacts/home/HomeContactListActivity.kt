package com.teddy.sweatcontacts.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.Toast
import com.teddy.sweatcontacts.R
import com.teddy.sweatcontacts.common.view.Resource
import com.teddy.sweatcontacts.common.view.Status
import com.teddy.sweatcontacts.common.view.bindView
import com.teddy.sweatcontacts.common.widget.InfiniteRecyclerView
import com.teddy.sweatcontacts.detail.DetailContactActivity
import com.teddy.sweatcontacts.detail.DetailContactFragment
import com.teddy.sweatcontacts.home.search.ContactSearchAdapter
import com.teddy.sweatcontacts.home.search.EmptyContactSearchAdapter
import com.teddy.sweatcontacts.model.Contact
import org.koin.android.viewmodel.ext.android.viewModel

private const val TAG = "HomeContactListActivity"

class HomeContactListActivity : AppCompatActivity(), ContactListener {

    private val contactSearchInput by bindView<SearchView>(R.id.contact_search_input)
    private val contactSearchResult by bindView<RecyclerView>(R.id.contact_search_result_list)
    private val contactSearchBg by bindView<View>(R.id.contact_search_background)
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

        contactSearchResult.layoutManager = LinearLayoutManager(this)
    }

    private fun setupListeners() {
        viewModel.contacts.listen { handleContacts(it) }

        viewModel.contactSearch.listen { handleContactSearch(it) }

        contactList.reachBottom.listen { handleMoreContactLoading(it) }

        contactSearchInput.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    hideSearchResult()
                } else {
                    viewModel.doContactSearchWith(query)
                }
                return true
            }

        })
//        contactSearchInput.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {}
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.doContactSearchWith(s.toString())
//            }
//
//        })
    }

    private fun handleContacts(contactResource: Resource<List<Contact>>) {
        when(contactResource.status) {
            Status.LOADING -> Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
            Status.SUCCESS -> {
                contactsAdapter.setContacts(contactResource.data ?: listOf())
            }
            Status.ERROR -> Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleContactSearch(search: List<Contact>) {
        if (search.isEmpty()) {
            contactSearchResult.adapter = EmptyContactSearchAdapter()
        } else {
            contactSearchResult.adapter = ContactSearchAdapter(search, this)
        }
        showSearchResult()
    }

    private fun handleMoreContactLoading(isLoading: Boolean) {

    }

    override fun onBackPressed() {
        if (contactSearchResult.visibility == View.VISIBLE) {
            hideSearchResult()
        } else {
            super.onBackPressed()
        }
    }

    private fun showSearchResult() {
        contactSearchResult.visibility = View.VISIBLE
        contactSearchBg.visibility = View.VISIBLE
    }

    private fun hideSearchResult(): Boolean {
        contactSearchResult.adapter = null
        contactSearchResult.visibility = View.GONE
        contactSearchBg.visibility = View.GONE
        return true
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

    private fun <T> LiveData<T>.listen(l: (r: T) -> Unit) {
        observe(this@HomeContactListActivity, Observer { it?.run { l(it) } })
    }
}

