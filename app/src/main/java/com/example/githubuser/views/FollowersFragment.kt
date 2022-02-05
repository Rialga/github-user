package com.example.githubuser.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapters.UserAdapter
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.factory.ViewModelFactory
import com.example.githubuser.viewmodels.DetailViewModel


class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding
    private lateinit var detailViewModel: DetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)

        return binding?.root
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val dataUsername: DetailActivity? = activity as DetailActivity?

        detailViewModel = obtainViewModel(this)
        detailViewModel.getFollower(dataUsername?.getUsername()!!)

        showLoading(true)

        detailViewModel.getDataFollower().observe(viewLifecycleOwner, { followerData ->
            _binding?.rvFollower?.layoutManager = LinearLayoutManager(context)
            val listDataFollower = UserAdapter(followerData)
            _binding?.rvFollower?.adapter = listDataFollower
            showLoading(false)
        })

    }


    private fun obtainViewModel(fragment: Fragment): DetailViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            _binding?.progressBarFollower?.visibility = View.VISIBLE
            _binding?.rvFollower?.visibility = View.GONE
        } else {
            _binding?.progressBarFollower?.visibility = View.GONE
            _binding?.rvFollower?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}