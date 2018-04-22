package com.kanade.ushio.arch.repository

import com.kanade.ushio.arch.room.ActorDao
import com.kanade.ushio.entity.Actor
import io.reactivex.Maybe

class ActorRepository(private var actorDao: ActorDao) {
    fun queryActor(id: Long): Maybe<Actor> {
        return actorDao.queryActor(id)
    }

    fun queryActorSync(id: Long): Actor {
        return actorDao.queryActorSync(id)
    }

    fun insertActor(actor: Actor): Long {
        return actorDao.insertActor(actor)
    }
}