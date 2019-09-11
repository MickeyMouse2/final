package com.raywenderlich.githubrepolist.data

import com.raywenderlich.githubrepolist.data.objects.UserResponse
import com.raywenderlich.githubrepolist.util.exhaustive
import kotlinx.coroutines.*
import com.raywenderlich.githubrepolist.data.objects.main.Result

class DataManager(private val networkManager: NetworkManager) {

    private val parentJob = SupervisorJob()
    private val bgScope = CoroutineScope(Dispatchers.IO + parentJob)

    fun getUsersAsync(onSuccess: (UserResponse) -> Unit, onError: (String) -> Unit) = bgScope.launch {
            val result = networkManager.searchUsers()
                when (result) {
                    is Result.Success -> onSuccess(result.data)
                    is Result.Error -> onError(result.exception.localizedMessage)
                }.exhaustive
        }
}