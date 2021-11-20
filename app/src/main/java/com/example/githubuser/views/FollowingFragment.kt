package com.example.githubuser.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapters.UserAdapter
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.viewmodels.DetailViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private val detailViewModel by viewModels<DetailViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        return binding?.root
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)



        val dataUsername: DetailActivity? = activity as DetailActivity?

        detailViewModel.getFollowing(dataUsername?.getUsername()!!)

        showLoading(true)

        detailViewModel.getDataFollowing().observe(viewLifecycleOwner, { followerData ->
            _binding?.rvFollowing?.layoutManager = LinearLayoutManager(context)
            val listDataFollower = UserAdapter(followerData)
            _binding?.rvFollowing?.adapter = listDataFollower
            showLoading(false)
        })


    }

    private fun showLoading(state: Boolean) {
        if (state) {
            _binding?.progressBarFollowing?.visibility = View.VISIBLE
            _binding?.rvFollowing?.visibility = View.GONE
        } else {
            _binding?.progressBarFollowing?.visibility = View.GONE
            _binding?.rvFollowing?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}