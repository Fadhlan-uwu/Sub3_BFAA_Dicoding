package com.dicoding.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUser (
        var id: Int? = null,
        var login: String? = null,
        var name: String? = null,
        var company: String? = null,
        var location: String? = null,
        var repository: Int? = 0,
        var following: Int? = 0,
        var follower: Int? = 0,
        var photo: String? = null
) : Parcelable