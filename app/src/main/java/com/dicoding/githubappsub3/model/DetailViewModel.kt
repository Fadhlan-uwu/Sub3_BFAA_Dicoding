package com.dicoding.githubappsub3.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel : ViewModel() {

    companion object {
        private const val TOKEN = "token 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"
    }

    val detailUser = MutableLiveData<GithubUser>()

    fun setUserDetail(username: String?) {
        val url = "https://api.github.com/users/$username"
        val detailItems = GithubUser()

        val client = AsyncHttpClient()
        client.addHeader("Authorization", TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObjects = JSONObject(result)
                    detailItems.apply {
                        login = responseObjects.getString("login")
                        id = responseObjects.getInt("id")
                        name = responseObjects.getString("name")
                        company = responseObjects.getString("company")
                        location = responseObjects.getString("location")
                        repository = responseObjects.getInt("public_repos")
                        following = responseObjects.getInt("following")
                        follower = responseObjects.getInt("followers")
                        photo = responseObjects.getString("avatar_url")
                    }
                    detailUser.postValue(detailItems)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getUserDetail() : LiveData<GithubUser> {
        return detailUser
    }
}