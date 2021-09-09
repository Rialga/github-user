package com.example.githubuser.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.adapters.UserAdapter
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.models.GithubUserDetailModel
import com.example.githubuser.viewmodels.DetailViewModel
import com.example.githubuser.viewmodels.MainViewModel


class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding
    private lateinit var detailViewModel: DetailViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val view = binding?.root

        return view
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val activity:DetailActivity = (activity as DetailActivity?)!!
        val userData: GithubUserDetailModel = activity.userData()

        detailViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        detailViewModel.getFollower(userData.login!!)

        detailViewModel.getDataFollower().observe(viewLifecycleOwner, { gitHubUserData ->
            _binding?.rvFollower?.layoutManager = LinearLayoutManager(context)
            val listDataFollower = UserAdapter(gitHubUserData)
            _binding?.rvFollower?.adapter = listDataFollower
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}