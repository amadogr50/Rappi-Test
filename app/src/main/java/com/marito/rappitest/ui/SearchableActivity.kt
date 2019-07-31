package com.marito.rappitest.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.marito.rappitest.R
import com.marito.rappitest.databinding.ActivitySearchableBinding

class SearchableActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchableBinding
    private var navController: NavController? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var query: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_searchable)
        handleIntent(intent)

        binding.appBar.outlineProvider = null
        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.host)

        appBarConfiguration = AppBarConfiguration.Builder(navController!!.graph).build()
        setupActionBarWithNavController(navController!!, appBarConfiguration)

        updateSearchMovies()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController!!.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main, menu)

        val searchView = menu!!.findItem(R.id.search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(applicationContext, SearchableActivity::class.java)
                intent.putExtra(SearchManager.QUERY, query)
                intent.action = Intent.ACTION_SEARCH
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchView.apply {
            setQuery(query, false)
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }

        return true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent!!)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                this.query = query
            }
        }

        updateSearchMovies()
    }

    fun updateSearchMovies() {
        if (navController != null) {
            val args = Bundle()
            args.putString("query", query)
            navController!!.popBackStack()
            navController!!.navigate(R.id.searchMoviesFragment, args)
        }
    }
}