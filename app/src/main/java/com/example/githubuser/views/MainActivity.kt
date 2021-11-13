package com.example.githubuser.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.localdata.SettingPreferences
import com.example.githubuser.viewmodels.MainViewModel
import com.example.githubuser.viewmodels.SettingViewModel
import com.example.githubuser.viewmodels.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var queryTextChangedJob: Job? = null
    private lateinit var searchText: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this,ViewModelFactory(pref))[MainViewModel::class.java]


        // Get current theme in setting
        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

            })

        mainViewModel.getGithubUserList("")

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                showLoading(true)

                binding.searchView.clearFocus()
                if (query != null) mainViewModel.getGithubUserList(query)

                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                showLoading(true)

                val text = newText ?: return false
                searchText = text

                queryTextChangedJob?.cancel()
                queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                    println("async work started...")
                    delay(2000)
                    newText?.let { mainViewModel.getGithubUserList(it) }
                    println("async work done!")
                }

                return false
            }
        })


        mainViewModel.getData().observe(this, { gitHubUserData ->
            binding.rvUser.layoutManager = LinearLayoutManager(this)
            val listData = UserAdapter(gitHubUserData)
            binding.rvUser.adapter = listData
            showLoading(false)
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting) {
            startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

}

