package com.raywenderlich.githubrepolist.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.githubrepolist.data.objects.UserResponse
import com.raywenderlich.githubrepolist.data.DataManager
import com.raywenderlich.githubrepolist.ui.adapters.RepoListAdapter

class MainViewModel(dataManager: DataManager) : ViewModel() {

    private val _users = MutableLiveData<UserResponse>()
    val users: LiveData<UserResponse>
        get() = _users
    //private val q = RepoListAdapter.ViewHolder.bindForecast()

    init {
        dataManager.getUsersAsync(
            onSuccess = {
                //MARK: or use  _users.=it if its in main thread
                _users.postValue(it)
                print(it)
            },
            onError = {
                print(it)
            })
    }
}