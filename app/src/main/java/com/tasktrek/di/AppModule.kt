package com.tasktrek.di

import com.tasktrek.data.repository.AuthRepositoryImpl
import com.tasktrek.domain.repository.AuthRepository
import com.tasktrek.domain.usecase.LoginUseCase
import com.tasktrek.domain.usecase.RegisterUseCase
import com.tasktrek.network.RetrofitInstance
import com.tasktrek.presentation.ui.screens.auth.viewModel.AuthViewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitInstance.authApiService }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single { RegisterUseCase(get()) }
    single { LoginUseCase(get()) }
    factory { AuthViewModel(get(), get()) }
}