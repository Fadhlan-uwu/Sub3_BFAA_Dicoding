package com.dicoding.githubappsub3.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubappsub3.R
import com.dicoding.githubappsub3.adapter.GithubUserAdapter
import com.dicoding.githubappsub3.databinding.ActivityMainBinding
import com.dicoding.githubappsub3.model.ListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listViewModel: ListViewModel
    private lateinit var userAdapter: GithubUserAdapter
    private var title = "Github Users"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarTitle(title)

        userAdapter = GithubUserAdapter(this)

        userAdapter.notifyDataSetChanged()

        listViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ListViewModel::class.java)

        binding.btnUser.setOnClickListener {
            val user = binding.editUser.text.toString()
            if (user.isEmpty()) return@setOnClickListener
            showLoading(true)

            listViewModel.setDataUser(user)
        }

        listViewModel.getDataUser().observe(this, { userItems ->
            if (userItems != null) {
                userAdapter.setData(userItems)
                showLoading(false)

                binding.rvList.layoutManager = LinearLayoutManager(this)
                binding.rvList.adapter = userAdapter
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.menu_setting -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }
}