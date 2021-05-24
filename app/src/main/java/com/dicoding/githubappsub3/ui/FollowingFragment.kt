package com.dicoding.githubappsub3.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubappsub3.adapter.GithubUserAdapter
import com.dicoding.githubappsub3.databinding.FragmentFollowingBinding
import com.dicoding.githubappsub3.model.FollowingViewModel

class FollowingFragment : Fragment() {

    companion object {
        private const val EXTRA_USERNAME = "username"
        @JvmStatic
        fun newInstance(username: String?) : FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: GithubUserAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        if (arguments != null) {

            adapter = GithubUserAdapter(activity!!)

            binding.rvListFollowing.layoutManager = LinearLayoutManager(context)
            binding.rvListFollowing.adapter = adapter
            binding.rvListFollowing.setHasFixedSize(true)

            followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(FollowingViewModel::class.java)

            val username = arguments?.getString(EXTRA_USERNAME)
            showLoading(true)
            if (username != null) {
                followingViewModel.setFollowingData(username)
            }
            followingViewModel.getFollowingData().observe(viewLifecycleOwner, Observer { followingItems ->
                if (followingItems != null) {
                    adapter.setData(followingItems)
                    showLoading(false)
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }
}