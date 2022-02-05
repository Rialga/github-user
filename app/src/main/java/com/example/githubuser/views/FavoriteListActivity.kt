package com.example.githubuser.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.FavoriteListAdapter
import com.example.githubuser.databinding.ActivityFavoriteListBinding
import com.example.githubuser.viewmodels.FavoriteListViewModel
import com.example.githubuser.factory.ViewModelFactory



class FavoriteListActivity : AppCompatActivity() {

    private var _activityFavoriteListBinding: ActivityFavoriteListBinding? = null
    private val binding get() = _activityFavoriteListBinding
    private lateinit var adapter: FavoriteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = getString(R.string.favorite_list)


        _activityFavoriteListBinding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        showLoading(true)
        val favoriteListViewModel = obtainViewModel(this)
        favoriteListViewModel.getAllList().observe(this, { userList ->
            if (userList != null) {
                adapter.setListFavorite(userList)
            }
            showLoading(false)
        })


        adapter = FavoriteListAdapter()

        binding?.rvFavoriteList?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteList?.setHasFixedSize(true)
        binding?.rvFavoriteList?.adapter = adapter


    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteListBinding = null
    }


    private fun obtainViewModel(activity: AppCompatActivity): FavoriteListViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteListViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.favorite)?.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting) {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.loading?.visibility = View.VISIBLE
            binding?.rvFavoriteList?.visibility = View.GONE
        } else {
            binding?.loading?.visibility = View.GONE
            binding?.rvFavoriteList?.visibility = View.VISIBLE
        }
    }
}