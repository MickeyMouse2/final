package com.raywenderlich.githubrepolist.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.raywenderlich.githubrepolist.data.objects.UserResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubService {
  @GET("/search/users")
  fun searchUser(
          @Query("q") q: String
  ): Deferred<Response<UserResponse>>

  companion object Factory {
    fun create(): GithubService {
      val retrofit = Retrofit.Builder()
              .baseUrl("https://api.github.com")
              .addConverterFactory(GsonConverterFactory.create())
              .addCallAdapterFactory(CoroutineCallAdapterFactory())
              .build()

      return retrofit.create(GithubService::class.java)
    }
  }
}