package com.teddy.sweatcontacts.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.View
import android.widget.ImageView
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
    private val contactLoading by bindView<View>(R.id.contact_progress)
    private val contactSearchResult by bindView<RecyclerView>(R.id.contact_search_result_list)
    private val favoriteList by bindView<RecyclerView>(R.id.favorite_list)
    private val favoriteContainer by bindView<CardView>(R.id.favorite_container)
    private val contactSearchBg by bindView<View>(R.id.contact_search_background)
    private val contactList by bindView<InfiniteRecyclerView>(R.id.contact_list)

    private val viewModel by viewModel<HomeContactViewModel>()

    private val contactsAdapter = HomeContactAdapter(this)
    private val favoriteAdapter = FavoriteAdapter(this)

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

        favoriteList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        favoriteList.adapter = favoriteAdapter
    }

    private fun setupListeners() {
        viewModel.contacts.listen { handleContacts(it) }

        viewModel.contactSearch.listen { handleContactSearch(it) }

        viewModel.favorites.listen { handleFavorites(it) }

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
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavorites()
    }

    private fun handleContacts(contactResource: Resource<List<Contact>>) {
        when(contactResource.status) {
            Status.LOADING -> contactLoading.visibility = View.VISIBLE
            Status.LOADING_MORE -> contactsAdapter.addLoading()
            Status.SUCCESS_MORE -> {
                contactsAdapter.removeLoading()
                contactList.loadingFinished()
                contactsAdapter.addContacts(contactResource.data ?: listOf())
            }
            Status.SUCCESS -> {
                contactLoading.visibility = View.GONE
                contactsAdapter.setContacts(contactResource.data ?: listOf())
            }
            Status.ERROR -> {
                contactLoading.visibility = View.GONE
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
            }
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

    private fun handleFavorites(favorites: List<Contact>) {
        if (favorites.isEmpty()) {
            favoriteContainer.visibility = View.GONE
        } else {
            favoriteContainer.visibility = View.VISIBLE
            favoriteAdapter.setFavorites(favorites)
        }

    }

    private fun handleMoreContactLoading(isLoading: Boolean) {
        if (isLoading) {
            viewModel.fetchMoreContacts()
        }
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

    override fun onContactClicked(contact: Contact, imageView: ImageView?) {
        hideSearchResult()
        contactSearchInput.setQuery("", false)
        contactSearchInput.clearFocus()

        if (resources.getBoolean(R.bool.dual_panel)) {
            val fragment = DetailContactFragment.newInstance(contact)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.contact_detail_container, fragment, TAG)
                .commit()

        } else {

            val detailIntent = DetailContactActivity.newIntent(this, contact)
            if (imageView != null) {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    imageView as View,
                    getString(R.string.contact_img_share_tag)
                )
                startActivity(detailIntent, options.toBundle())
            } else {
                startActivity(detailIntent)
            }
        }
    }

    private fun <T> LiveData<T>.listen(l: (r: T) -> Unit) {
        observe(this@HomeContactListActivity, Observer { it?.run { l(it) } })
    }
}

