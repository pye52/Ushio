package com.kanade.ushio.arch.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.kanade.ushio.entity.Actor
import io.reactivex.Maybe

@Dao
interface ActorDao {
    @Query("select * from actor where id = :id")
    fun queryActor(id: Long): Maybe<Actor>

    @Query("select * from actor where id = :id")
    fun queryActorSync(id: Long): Actor

    @Insert(onConflict = REPLACE)
    fun insertActor(actor: Actor): Long

    @Delete
    fun deleteActor(actor: Actor): Int

    @Update
    fun updateActor(actor: Actor): Int
}