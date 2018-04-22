package com.kanade.ushio.arch.repository

import com.kanade.ushio.arch.AppDatabase
import com.kanade.ushio.arch.room.CrtDao
import com.kanade.ushio.entity.Actor
import com.kanade.ushio.entity.Crt
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class CrtRepository(private var crtDao: CrtDao,
                    private var actorRepo: ActorRepository) {
    fun queryCrt(id: Long): Maybe<Crt> {
        return crtDao.queryCrt(id)
                .observeOn(Schedulers.io())
                // 每3天更新一次
                .filter { System.currentTimeMillis() - it.updateTime < (3 * 24 * 60 * 60 * 1000) }
                .doOnSuccess {
                    if (it.actorIds.isBlank()) {
                        return@doOnSuccess
                    }
                    AppDatabase.getInstance().beginTransaction()
                    val ids = it.actorIds.split(",")
                    val list = arrayListOf<Actor>()
                    ids.forEach {
                        list.add(actorRepo.queryActorSync(it.toLong()))
                    }
                    it.actors = list
                    AppDatabase.getInstance().setTransactionSuccessful()
                    AppDatabase.getInstance().endTransaction()
                }
    }

    // 保存的时候会将actor数组变为字符串保存到数据库中
    fun insertCrt(crt: Crt): Long {
        crt.updateTime = System.currentTimeMillis()
        crt.actors?.forEach {
            actorRepo.insertActor(it)
        }
        crt.actorIds = crt.actors?.joinToString(",", transform = {
            it.id.toString()
        }) ?: ""
        return crtDao.insertCrt(crt)
    }
}