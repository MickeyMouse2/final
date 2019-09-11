package com.raywenderlich.githubrepolist

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import com.raywenderlich.githubrepolist.di.networkModule
import com.raywenderlich.githubrepolist.di.viewModelModule

class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(androidCoreModule(this@App))
        import(networkModule)
        import(viewModelModule)
    }

}