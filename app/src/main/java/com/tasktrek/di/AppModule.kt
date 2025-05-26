package com.tasktrek.di

import android.content.Context
import android.content.SharedPreferences
import com.tasktrek.data.repository.AuthRepositoryImpl
import com.tasktrek.data.repository.ProjectRepositoryImpl
import com.tasktrek.data.repository.TaskRepositoryImpl
import com.tasktrek.domain.repository.AuthRepository
import com.tasktrek.domain.repository.ProjectRepository
import com.tasktrek.domain.repository.TaskRepository
import com.tasktrek.domain.usecase.LoginUseCase
import com.tasktrek.domain.usecase.ProjectUseCase
import com.tasktrek.domain.usecase.RegisterUseCase
import com.tasktrek.domain.usecase.TaskUseCase
import com.tasktrek.network.RetrofitInstance
import com.tasktrek.presentation.ui.screens.auth.viewModel.AuthViewModel
import com.tasktrek.presentation.ui.screens.home.viewModel.HomeViewModel
import com.tasktrek.utils.TokenManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    single { TokenManager(get()) }
    single { RetrofitInstance.provideServices(get()) }

    // Auth
    single<AuthRepository> { AuthRepositoryImpl(get<RetrofitInstance.ServiceContainer>().authApiService) }
    single { RegisterUseCase(get()) }
    single { LoginUseCase(get()) }
    factory { AuthViewModel(get(), get(), get()) }

    // Tasks
    single<TaskRepository> { TaskRepositoryImpl(get<RetrofitInstance.ServiceContainer>().taskApiService) }
    single { TaskUseCase(get()) }

    // Projects
    single<ProjectRepository> { ProjectRepositoryImpl(get<RetrofitInstance.ServiceContainer>().projectApiService) }
    single { ProjectUseCase(get()) }

    // Home
    factory { HomeViewModel(get(), get()) }
}

