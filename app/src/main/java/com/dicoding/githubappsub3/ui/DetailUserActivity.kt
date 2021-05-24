package com.dicoding.githubappsub3.ui

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubappsub3.model.GithubUser
import com.dicoding.githubappsub3.R
import com.dicoding.githubappsub3.adapter.SectionsPagerAdapter
import com.dicoding.githubappsub3.databinding.ActivityDetailUserBinding
import com.dicoding.githubappsub3.db.DatabaseContract
import com.dicoding.githubappsub3.db.DatabaseContract.CONTENT_URI
import com.dicoding.githubappsub3.helper.MappingHelper
import com.dicoding.githubappsub3.model.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private var statusFavorite: Boolean = false
    private var menuItem: Menu? = null

    private var dataUser: GithubUser? = null
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var uriWithId: Uri

    companion object {
        internal val TAG = DetailUserActivity::class.java.simpleName

        const val EXTRA_USER = "extra_user"

        const val EXTRA_STATE = "extra_state"
        const val EXTRA_FAVORITE = "extra_favorite"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)

        val actionbar = supportActionBar
        actionbar?.title = "Detail User"
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getStringExtra(EXTRA_USER)

        dataUser = intent.getParcelableExtra(EXTRA_STATE)

        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + dataUser?.id)
        val dataUserFavorite = contentResolver?.query(uriWithId, null, null, null, null)
        checkStatusFavorite(dataUserFavorite)

        dataUser?. let { mData ->
            setDetailGithubUser(mData)
        }

        /*TAB LAYOUT-------------------------------------------*/
        val sectionsPagerAdapter = SectionsPagerAdapter(this, user)

        val viewPager = binding.tabLayout.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabLayout.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(TAB_TITLES[0])
                else -> getString(TAB_TITLES[1])
            }
        }.attach()
        supportActionBar?.elevation = 0f
        /*TAB LAYOUT-------------------------------------------*/
    }

    // about favorite =======================================================
    private fun checkStatusFavorite(githubUserFav: Cursor?) {
        val githubUserObject = MappingHelper.mapCursorToArrayList(githubUserFav)
        for (data in githubUserObject) {
            if (this.dataUser?.id == data.id) {
                Log.e(TAG, "cekFavorite dataGitObject: $githubUserObject")
                Log.e(TAG, "cekFavorite data: $data")
                statusFavorite = true
            }
        }
    }

    private fun setStatusFavorite(menu: Menu) {
        if (statusFavorite) {
            menu.getItem(0)?.icon = ContextCompat.getDrawable(this,
                    R.drawable.ic_baseline_favorite_24)
        } else {
            menu.getItem(0)?.icon = ContextCompat.getDrawable(this,
                    R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun setDataFavorite() {
        if (statusFavorite) {
            dataUser?.let {
                contentResolver.delete(uriWithId, null, null)
                showMessage("${it.login} Delete Favorite")
                menuItem?.let { menu ->
                    statusFavorite = false
                    setStatusFavorite(menu)
                }
            }

        } else {
            val values = ContentValues()
            values.put(DatabaseContract.UserColumns._ID, dataUser?.id)
            values.put(DatabaseContract.UserColumns.LOGIN, dataUser?.login)
            values.put(DatabaseContract.UserColumns.AVATAR_URL, dataUser?.photo)

            contentResolver.insert(CONTENT_URI, values)
            Log.d(TAG, "Insert Data Favorite: $values")
            showMessage("${dataUser?.login} Favorite")
            menuItem?.let { menu ->
                statusFavorite = true
                setStatusFavorite(menu)
            }

        }
    }

    private fun setDetailGithubUser(githubUser: GithubUser) {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        detailViewModel.setUserDetail(githubUser.login)

        detailViewModel.getUserDetail().observe(this, Observer { detailItemUser ->
            if (detailItemUser != null) {
                binding.tvUsername.text = detailItemUser.login
                binding.tvName.text = detailItemUser.name
                binding.tvCompany.text = detailItemUser.company
                binding.tvLocation.text = detailItemUser.location
                binding.tvRepositoryNumber.text = detailItemUser.repository.toString()
                binding.tvFollowingNumber.text = detailItemUser.following.toString()
                binding.tvFollowersNumber.text = detailItemUser.follower.toString()
                Glide.with(this)
                    .load(detailItemUser.photo)
                    .apply(RequestOptions())
                    .into(binding.imgPhoto)
            }
            showLoading(false)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_favorite -> setDataFavorite()
        }
        return true
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        setStatusFavorite(menu)
        this.menuItem = menu
        return super.onCreateOptionsMenu(menu)
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