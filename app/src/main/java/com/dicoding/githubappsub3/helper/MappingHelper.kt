package com.dicoding.githubappsub3.helper

import android.database.Cursor
import com.dicoding.githubappsub3.db.DatabaseContract
import com.dicoding.githubappsub3.model.GithubUser

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<GithubUser> {
        val userList = ArrayList<GithubUser>()

        userCursor?.apply {
            while (moveToNext()) {
                val userGit = GithubUser()
                userGit.id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                userGit.login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
                userGit.photo = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL))
                userList.add(userGit)
            }
        }
        return userList
    }
}