package com.raywenderlich.githubrepolist.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import com.raywenderlich.githubrepolist.R
import com.raywenderlich.githubrepolist.databinding.ActivityMainBinding
import com.raywenderlich.githubrepolist.ui.adapters.RepoListAdapter
import com.raywenderlich.githubrepolist.util.viewModel


class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private var context: Context? = this

    private var searchView: SearchView? = null

    private val mainViewModel:MainViewModel by viewModel()

    private val userAdapter by lazy {
        RepoListAdapter {
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this@MainActivity,
                        Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                            Manifest.permission.INTERNET)) {

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.INTERNET),
                        125)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Log.d("error","hi")
        }

        //TODO:using databining for view
        val binding: ActivityMainBinding =
                DataBindingUtil
                        .setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
                            adapter = userAdapter
                            viewModel = mainViewModel
                        }

        binding.lifecycleOwner = this

    }

   /* override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = menu.findItem(R.id.action_searcher)
                .actionView as SearchView
        searchView!!.setSearchableInfo(searchManager
                .getSearchableInfo(componentName))
        searchView!!.maxWidth = Integer.MAX_VALUE

        return true
    }*/
}
