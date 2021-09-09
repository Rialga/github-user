package com.example.githubuser.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapters.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)



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
                newText?.let { mainViewModel.getGithubUserList(it) }
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
