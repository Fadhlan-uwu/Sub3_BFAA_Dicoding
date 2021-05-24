package com.dicoding.githubappsub3.db

import android.net.Uri
import android.provider.BaseColumns
import com.dicoding.githubappsub3.db.DatabaseContract.UserColumns.Companion.TABLE_NAME

object DatabaseContract {

    const val AUTHORITY = "com.dicoding.githubappsub3"
    const val SCHEME = "content"

     class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "github_user"
            const val _ID = "id"
            const val LOGIN = "login"
            const val AVATAR_URL = "avatar_url"
        }
    }

    // content://com.dicoding.githubappsub3/github_user
    val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
}