package com.sewar.sary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sewar.sary.data.DataRepository
import com.sewar.sary.data.models.Banner
import com.sewar.sary.data.models.Catalog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class StoreViewModel(
    savedStateHandle: SavedStateHandle,
    private var dataRepository: DataRepository,
) : ViewModel() {
    private val storeState = MutableLiveData<StoreState>(StoreState.Loading)

    private val errorMessage = MutableLiveData<String?>(null)

    private var banners: List<Banner> = emptyList()
    private var catalogs: List<Catalog> = emptyList()

    private var bannersDisposable: Disposable? = null
    private var catalogsDisposable: Disposable? = null

    init {
        loadBanners()
        loadCatalogs()
    }

    override fun onCleared() {
        bannersDisposable?.dispose()
        catalogsDisposable?.dispose()
    }

    fun storeState(): LiveData<StoreState> = storeState

    fun errorMessage(): LiveData<String?> = errorMessage

    fun onErrorMessageHandled() {
        errorMessage.value = null
    }

    private fun loadBanners() {
        bannersDisposable?.dispose()
        bannersDisposable = dataRepository.banners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                banners = it
                if (banners.isNotEmpty()) {
                    updateContent()
                }
            }, { e ->
                Timber.d(e)
                errorMessage.value = e.message
            })
    }

    private fun loadCatalogs() {
        catalogsDisposable?.dispose()
        catalogsDisposable = dataRepository.catalogs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                catalogs = it
                if (catalogs.isNotEmpty()) {
                    updateContent()
                }
            }, { e ->
                Timber.d(e)
                errorMessage.value = e.message
            })
    }

    private fun updateContent() {
        val items = mutableListOf<CatalogsAdapterItem>()

        if (banners.isNotEmpty()) {
            items.add(CatalogsAdapterItem.Banners(banners))
        }

        catalogs.onEach {
            items.add(CatalogsAdapterItem.CatalogItem(it))
        }

        storeState.value = StoreState.Content(items)
    }
}

sealed class StoreState {
    object Loading : StoreState()

    data class Content(
        val adapterItems: List<CatalogsAdapterItem>,
    ) : StoreState()
}
