package com.teddy.sweatcontacts.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.teddy.sweatcontacts.R
import com.teddy.sweatcontacts.model.Contact

private const val CONTACT_KEY = "detailActivity.CONTACT_KEY"
private const val TAG = "DetailContactActivity"

class DetailContactActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, contact: Contact): Intent {
            return Intent(context, DetailContactActivity::class.java)
                .apply { putExtra(CONTACT_KEY, contact) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_contact)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.hasExtra(CONTACT_KEY) && savedInstanceState == null) {

            val fragment = DetailContactFragment.newInstance(intent.getParcelableExtra(CONTACT_KEY))

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.contact_detail_container, fragment, TAG)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
