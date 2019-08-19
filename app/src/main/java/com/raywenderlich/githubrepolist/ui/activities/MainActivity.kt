/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.githubrepolist.ui.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.raywenderlich.githubrepolist.R
import com.raywenderlich.githubrepolist.api.RepositoryRetriever
import com.raywenderlich.githubrepolist.data.RepoResult
import com.raywenderlich.githubrepolist.ui.adapters.RepoListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

  private var searchView: SearchView? = null
  private val repoRetriever = RepositoryRetriever()
    private var context: Context? = this
    private var mainLayout: View? = null

  private val callback = object : Callback<RepoResult> {
    override fun onFailure(call: Call<RepoResult>?, t: Throwable?) {
      Log.e("MainActivity", "Problem calling Github API", t)
    }

    override fun onResponse(call: Call<RepoResult>?, response: Response<RepoResult>?) {
      response?.isSuccessful.let {
        val resultList = RepoResult(response?.body()?.items ?: emptyList())
        repoList.adapter = RepoListAdapter(resultList)
      }
    }
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)

    repoList.layoutManager = LinearLayoutManager(this)

    if (isNetworkConnected()) {
      repoRetriever.getRepositories(callback, "a")
    } else {
      AlertDialog.Builder(this).setTitle("No Internet Connection")
          .setMessage("Please check your internet connection and try again")
          .setPositiveButton(android.R.string.ok) { _, _ -> }
          .setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    refreshButton.setOnClickListener {
      repoRetriever.getRepositories(callback, "a")
    }
  }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_searcher)
                .actionView as SearchView
        searchView!!.setSearchableInfo(searchManager
                .getSearchableInfo(componentName))
        searchView!!.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (isNetworkConnected()) {
                    repoRetriever.getRepositories(callback, query)
                } else {
                    AlertDialog.Builder(context).setTitle("No Internet Connection")
                            .setMessage("Please check your internet connection and try again")
                            .setPositiveButton(android.R.string.ok) { _, _ -> }
                            .setIcon(android.R.drawable.ic_dialog_alert).show()
                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (isNetworkConnected()) {
                    repoRetriever.getRepositories(callback, query)
                } else {
                    AlertDialog.Builder(context).setTitle("No Internet Connection")
                            .setMessage("Please check your internet connection and try again")
                            .setPositiveButton(android.R.string.ok) { _, _ -> }
                            .setIcon(android.R.drawable.ic_dialog_alert).show()
                }
                return false
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_searcher) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView!!.isIconified) {
            searchView!!.isIconified = true
            return
        }
        super.onBackPressed()
    }

  private fun isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager //1
    val networkInfo = connectivityManager.activeNetworkInfo //2
    return networkInfo != null && networkInfo.isConnected //3
  }
}
