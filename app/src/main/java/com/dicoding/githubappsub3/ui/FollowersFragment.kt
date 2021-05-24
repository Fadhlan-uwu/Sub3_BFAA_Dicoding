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
import com.dicoding.githubappsub3.databinding.FragmentFollowersBinding
import com.dicoding.githubappsub3.model.FollowersViewModel

class FollowersFragment : Fragment() {

    companion object {
        private const val EXTRA_USERNAME = "username"
        @JvmStatic
        fun newInstance(username: String?) : FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var adapter: GithubUserAdapter
    private lateinit var followersViewModel: FollowersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            adapter = GithubUserAdapter(activity!!)

            binding.rvListFollowers.layoutManager = LinearLayoutManager(context)
            binding.rvListFollowers.adapter = adapter
            binding.rvListFollowers.setHasFixedSize(true)

            followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(FollowersViewModel::class.java)

            val username = arguments?.getString(EXTRA_USERNAME)
            showLoading(true)
            if (username != null) {
                followersViewModel.setFollowersData(username)
            }

            followersViewModel.getFollowersData().observe(viewLifecycleOwner, Observer { followersItems ->
                if (followersItems != null) {
                    adapter.setData(followersItems)
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
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }


}