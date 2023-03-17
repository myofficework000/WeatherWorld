package com.example.weather_world.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_world.model.remote.data.news.NewsResponse
import com.example.weather_world.model.repositories.news.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    val responseFromApi = MutableLiveData<NewsResponse>()

    fun getNewsAccordingToRegion(country: String) {
        compositeDisposable.add(
            repository.fetchNewsAPI("CN")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    responseFromApi.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

