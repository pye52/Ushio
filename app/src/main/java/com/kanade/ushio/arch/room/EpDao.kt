package com.kanade.ushio.arch.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.kanade.ushio.entity.Ep
import io.reactivex.Flowable

@Dao
interface EpDao {
    @Query("select * from Ep where subjectId = :subjectId")
    fun queryEps(subjectId: Long): Flowable<List<Ep>>

    @Insert(onConflict = REPLACE)
    fun insertEps(list: List<Ep>): Array<Long>

    @Update
    fun updateEp(ep: Ep): Int

    @Delete
    fun deleteEp(ep: Ep): Int
}