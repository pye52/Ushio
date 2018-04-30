package com.kanade.ushio.arch

import com.kanade.ushio.api.*
import com.kanade.ushio.arch.factory.*
import com.kanade.ushio.arch.repository.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Injection {
    fun provideUserTokenViewModelFactory(): UserTokenDaoViewModelFactory {
        // 由于登录部分的base url与api不同，因此这里需要构建一个新的retrofit对象
        val service = provideLoginService()
        val userTokenRepo = provideUserTokenRepository()
        return UserTokenDaoViewModelFactory(service, userTokenRepo)
    }

    fun provideUserViewModelFactory(): UserViewModelFactory {
        val userRepository = provideUserRepostory()
        return UserViewModelFactory(userRepository)
    }

    fun provideSubjectDetailViewModelFactory(): SubjectDetailViewModelFactory {
        val service = ApiManager.retrofit.create(SubjectService::class.java)
        val subjectDao = AppDatabase.getInstance().subjectDao()
        val crtRepo = provideCrtRepository()
        val epRepo = provideEpRepository()
        return SubjectDetailViewModelFactory(service, subjectDao, crtRepo, epRepo)
    }

    fun provideSubjectProgressViewModelFactory(): SubjectProgressViewModelFactory {
        val repository = provideSubjectProgressRepository()
        val epRepo = provideEpRepository()
        return SubjectProgressViewModelFactory(repository, epRepo)
    }

    fun provideCalendarViewModelFactory(): CalendarViewModelFactory {
        val calendarRepo = provideCalendarRepository()
        val subjectRepo = provideSubjectRepository()
        val userRepo = provideUserRepostory()
        return CalendarViewModelFactory(calendarRepo, subjectRepo, userRepo)
    }

    fun provideLoginService(): AuthService {
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
        return retrofit.create(AuthService::class.java)
    }

    fun provideActorRepository(): ActorRepository {
        val actorDao = AppDatabase.getInstance().actorDao()
        return ActorRepository(actorDao)
    }

    fun provideSubjectRepository(): SubjectRepository {
        val subjectDao = AppDatabase.getInstance().subjectDao()
        val crtRepo = provideCrtRepository()
        val epRepo = provideEpRepository()
        val service = ApiManager.retrofit.create(SubjectService::class.java)
        return SubjectRepository(service, subjectDao, crtRepo, epRepo)
    }

    fun provideCalendarRepository(): CalendarRepository {
        val calendarService = ApiManager.retrofit.create(CalendarService::class.java)
        return CalendarRepository(calendarService)
    }

    fun provideCrtRepository(): CrtRepository {
        val crtDao = AppDatabase.getInstance().crtDao()
        val actorRepo = provideActorRepository()
        return CrtRepository(crtDao, actorRepo)
    }

    fun provideEpRepository(): EpRepository {
        val epDao = AppDatabase.getInstance().epDao()
        val subjectService = ApiManager.retrofit.create(SubjectService::class.java)
        return EpRepository(epDao, subjectService)
    }

    fun provideSubjectProgressRepository(): SubjectProgressRepository {
        val service = ApiManager.retrofit.create(SubjectProgressService::class.java)
        return SubjectProgressRepository(service)
    }

    fun provideUserRepostory(): UserRepository {
        val service = ApiManager.retrofit.create(UserService::class.java)
        val userCollectionDao = AppDatabase.getInstance().userCollectionDao()
        val subjectRepo = provideSubjectRepository()
        return UserRepository(service, userCollectionDao, subjectRepo)
    }

    fun provideUserTokenRepository(): UserTokenRepository {
        val userTokenDao = AppDatabase.getInstance().userTokenDao()
        return UserTokenRepository(userTokenDao)
    }
}