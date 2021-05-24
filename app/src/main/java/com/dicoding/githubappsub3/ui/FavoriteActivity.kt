package com.dicoding.githubappsub3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubappsub3.adapter.FavoriteAdapter
import com.dicoding.githubappsub3.databinding.ActivityFavoriteBinding
import com.dicoding.githubappsub3.db.DatabaseContract
import com.dicoding.githubappsub3.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.title = "Favorite"
        actionbar?.setDisplayHomeAsUpEnabled(true)

        adapter = FavoriteAdapter(this)

        GlobalScope.launch(Dispatchers.Main) {
            val defferedGit = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(DatabaseContract.CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val favorite = defferedGit.await()
            if (favorite.size > 0) {
                adapter.listUserFav = favorite
            } else {
                adapter.listUserFav = ArrayList()
                showSnackbarMessage("Data Kosong!")
            }
        }
        supportActionBar?.elevation = 0f
        showRecycleView()
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvListFav, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showRecycleView() {
        binding.rvListFav.adapter = adapter
        binding.rvListFav.layoutManager = LinearLayoutManager(this)
        binding.rvListFav.setHasFixedSize(true)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}