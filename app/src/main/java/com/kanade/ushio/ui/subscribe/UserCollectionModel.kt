package com.kanade.ushio.ui.subscribe

import com.kanade.ushio.api.ApiManager
import com.kanade.ushio.api.CollectionService
import com.kanade.ushio.entity.AniCollection
import com.kanade.ushio.entity.UserCollection
import com.kanade.ushio.model.IBaseModel
import com.kanade.ushio.utils.RxJavaUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import io.realm.RealmList
import java.util.*

class UserCollectionModel : IBaseModel {
    private lateinit var realm: Realm
    override fun start() {
        realm = Realm.getDefaultInstance()
    }

    override fun stop() {
        realm.close()
    }

    fun getCollections(userId: Int) =
            Observable.concat(
                    getCollectionsFromLocal(userId),
                    getCollectionsFromServer(userId))
                    .filter { it.isNotEmpty() }
                    .take(1)

    fun getCollectionsFromServer(userId: Int) =
            ApiManager.getRetrofit()
                    .create(CollectionService::class.java)
                    .get(userId)
                    .compose(RxJavaUtils.IO2MainThread())
                    .map { response ->
                        val realmList = RealmList<AniCollection>()
                        realmList.addAll(response)
                        val userCollections = UserCollection(userId, realmList)
                        realm.beginTransaction()
                        realm.delete(AniCollection::class.java)
                        realm.copyToRealmOrUpdate(userCollections)
                        realm.commitTransaction()
                        response
                    }
                    // 网络不通或者返回null导致解析错误时，返回一个空串
                    .onErrorReturn { realm.executeTransaction { it.delete(AniCollection::class.java) }; Collections.emptyList() }

    fun getCollectionsFromLocal(userId: Int) =
            Observable.just(realm.where(UserCollection::class.java)
                    .equalTo("id", userId)
                    .findAll())
                    .map { return@map if (it.size > 0) it[0].collections else Collections.emptyList() }
                    .subscribeOn(AndroidSchedulers.mainThread())
}