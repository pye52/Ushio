package com.kanade.ushio.arch

import com.kanade.ushio.api.*
import com.kanade.ushio.arch.factory.SubjectDetailViewModelFactory
import com.kanade.ushio.arch.factory.SubjectProgressViewModelFactory
import com.kanade.ushio.arch.factory.UserCollectionViewModelFactory
import com.kanade.ushio.arch.factory.UserTokenDaoViewModelFactory
import com.kanade.ushio.arch.repository.ActorRepository
import com.kanade.ushio.arch.repository.CrtRepository
import com.kanade.ushio.arch.repository.EpRepository
import com.kanade.ushio.arch.repository.SubjectRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Injection {
    fun provideUserTokenViewModelFactory(): UserTokenDaoViewModelFactory {
        val dataSource = AppDatabase.getInstance().userTokenDao()
        // 由于登录部分的base url与api不同，因此这里需要构建一个新的retrofit对象
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://bgm.tv/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        val service = retrofit.create(AuthService::class.java)
        return UserTokenDaoViewModelFactory(service, dataSource)
    }

    fun provideUserCollectionViewModelFactory(): UserCollectionViewModelFactory {
        val smallSubjectDao = AppDatabase.getInstance().smallSubjectDao()
        val subjectDao = AppDatabase.getInstance().subjectDao()
        val service = ApiManager.retrofit.create(UserCollectionService::class.java)
        return UserCollectionViewModelFactory(service, smallSubjectDao, subjectDao)
    }

    fun provideSubjectDetailViewModelFactory(): SubjectDetailViewModelFactory {
        val actDao = AppDatabase.getInstance().actorDao()
        val actRepo = ActorRepository(actDao)
        val crtDao = AppDatabase.getInstance().crtDao()
        val crtRepo = CrtRepository(crtDao, actRepo)
        val epDao = AppDatabase.getInstance().epDao()
        val epRepo = EpRepository(epDao)
        val service = ApiManager.retrofit.create(SubjectService::class.java)
        return SubjectDetailViewModelFactory(service, crtRepo, epRepo)
    }

    fun provideSubjectProgressViewModelFactory(): SubjectProgressViewModelFactory {
        val service = ApiManager.retrofit.create(SubjectProgressService::class.java)
        val epDao = AppDatabase.getInstance().epDao()
        val epRepo = EpRepository(epDao)
        return SubjectProgressViewModelFactory(service, epRepo)
    }

    fun provideSubjectRepository(): SubjectRepository {
        val actDao = AppDatabase.getInstance().actorDao()
        val actRepo = ActorRepository(actDao)
        val crtDao = AppDatabase.getInstance().crtDao()
        val crtRepo = CrtRepository(crtDao, actRepo)
        val epDao = AppDatabase.getInstance().epDao()
        val epRepo = EpRepository(epDao)
        val service = ApiManager.retrofit.create(SubjectService::class.java)
        return SubjectRepository(service, crtRepo, epRepo)
    }
}