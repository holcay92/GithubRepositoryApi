package com.devtides.githubrepos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtides.githubrepos.model.GitHubService
import com.devtides.githubrepos.model.GitHubToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val tokenLD = MutableLiveData<String>()
    val errorLD = MutableLiveData<String>()

    fun getToken(clientId: String, clientSecret: String, code: String) {
        compositeDisposable.add(
            GitHubService.getUnauthorizedApi().getAuthToken(clientId, clientSecret, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<GitHubToken>() {
                    override fun onSuccess(t: GitHubToken) {
                        tokenLD.value = t.accessToken
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        errorLD.value = "Can not load token"
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}