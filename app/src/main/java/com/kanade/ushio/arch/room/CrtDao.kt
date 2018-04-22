package com.kanade.ushio.arch.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.kanade.ushio.entity.Crt
import io.reactivex.Maybe

@Dao
interface CrtDao {
    @Query("select * from crt where id = :id")
    fun queryCrt(id: Long): Maybe<Crt>

    @Insert(onConflict = REPLACE)
    fun insertCrt(crt: Crt): Long

    @Insert(onConflict = REPLACE)
    fun insertCrt(list: List<Crt>)

    @Delete
    fun deleteCrt(crt: Crt): Int

    @Update
    fun updateCrt(crt: Crt): Int
}