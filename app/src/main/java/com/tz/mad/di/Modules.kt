package com.tz.mad.di

import com.tz.mad.domain.KoUseCase
import com.tz.mad.repo.UserRepo
import com.tz.mad.repo.UserRepoImpl
import com.tz.mad.ui.kosample.KoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.scope.get

import org.koin.dsl.module


val appModules = module {
    single<UserRepo> { UserRepoImpl() }
    factory<KoUseCase> { KoUseCase(get()) }
    viewModel { KoViewModel(get()) }

}