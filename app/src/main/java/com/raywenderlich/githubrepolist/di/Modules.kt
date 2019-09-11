package com.raywenderlich.githubrepolist.di

import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.erased.*
import com.raywenderlich.githubrepolist.api.GithubService
import com.raywenderlich.githubrepolist.data.DataManager
import com.raywenderlich.githubrepolist.data.NetworkManager
import com.raywenderlich.githubrepolist.ui.ViewModelFactory
import com.raywenderlich.githubrepolist.ui.activities.MainViewModel
import com.raywenderlich.githubrepolist.util.bindViewModel

val networkModule = Kodein.Module("network") {
    constant("serverURL") with ""

    bind<GithubService>() with singleton { GithubService.create() }
    bind<DataManager>() with singleton { DataManager(instance()) }
    bind<NetworkManager>() with singleton { NetworkManager(instance()) }

    //bind<AuthApi>() with singleton { createWebService<AuthApi>(instance(), instance("serverURL")) }

}

val viewModelModule = Kodein.Module("viewModelModule") {
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }
    bindViewModel<MainViewModel>() with provider { MainViewModel(instance()) }
}