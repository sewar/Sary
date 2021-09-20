package com.sewar.sary.data

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sewar.sary.data.models.Banner
import com.sewar.sary.data.models.BannersResponse
import com.sewar.sary.data.models.Catalog
import com.sewar.sary.data.models.CatalogsResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class FakeDataRepository @Inject constructor(
    private val context: Context,
) : DataRepository {
    private val gson = GsonBuilder().create()

    override fun banners(): Single<List<Banner>> {
        val reader = context.assets.open("banners.json").bufferedReader()

        val type = object : TypeToken<BannersResponse>() {}.type

        val banners = gson.fromJson<BannersResponse>(reader, type)
            .toBanners()

        return Single.just(banners)
    }

    override fun catalogs(): Single<List<Catalog>> {
        val reader = context.assets.open("catalogs.json").bufferedReader()

        val type = object : TypeToken<CatalogsResponse>() {}.type

        val catalogs = gson.fromJson<CatalogsResponse>(reader, type)
            .toCatalogs()

        return Single.just(catalogs)
    }
}
