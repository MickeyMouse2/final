package com.raywenderlich.githubrepolist.data

import com.raywenderlich.githubrepolist.api.GithubService
import com.raywenderlich.githubrepolist.data.objects.UserResponse
import com.raywenderlich.githubrepolist.util.safeApiCall
import com.raywenderlich.githubrepolist.data.objects.main.Result
import java.io.IOException

class NetworkManager(private val gitResponse: GithubService) {

    suspend fun searchUsers() = safeApiCall(
            call = {
                searchUserByFollowers()
            },
            errorMessage = "Error get user data"
    )

    private suspend fun searchUserByFollowers():  Result<UserResponse> {
        val response = gitResponse.searchUser("name").await()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return Result.Error(
                IOException("Error getting github data ${response.code()} ${response.message()}")
        )
    }
}


//    fun getUserByNickName(nick: String, onUpdate: (User) -> Unit, onError: (Any) -> Unit){
//        val resultObservable =
//            gitResponse.getUserByUsername(nick)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe ({
//                        result ->
//                    Log.e(this.javaClass.name, result.toString())
//                    onUpdate(result)
//                }, { error ->
//                    onError(error)
//                })
//    }