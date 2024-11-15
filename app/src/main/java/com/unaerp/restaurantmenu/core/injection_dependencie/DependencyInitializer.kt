package com.unaerp.restaurantmenu.core.injection_dependencie

import AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unaerp.restaurantmenu.core.data_source.impl.*
import com.unaerp.restaurantmenu.core.repositories.menu.impl.MenuRepositoryImpl
import com.unaerp.restaurantmenu.core.repositories.order.impl.OrderRepositoryImpl
import com.unaerp.restaurantmenu.core.repositories.user_data.impl.UserDataRepositoryImpl
import com.unaerp.restaurantmenu.core.use_case.auth.impl.AuthUseCaseImpl
import com.unaerp.restaurantmenu.core.use_case.menu.impl.MenuUseCaseImpl
import com.unaerp.restaurantmenu.core.use_case.order.impl.OrderUseCaseImpl
import com.unaerp.restaurantmenu.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class DependencyInitializer {
    val appModule = module {
        //ViewModels
        viewModel { MainViewModel(get()) }

        //UseCases
        single { MenuUseCaseImpl(get()) }
        single { AuthUseCaseImpl(get(), get()) }
        single { OrderUseCaseImpl(get(), get()) }

        //Repositories
        single { AuthRepositoryImpl(get()) }
        single { OrderRepositoryImpl(get()) }
        single { MenuRepositoryImpl(get()) }
        single { UserDataRepositoryImpl(get()) }

        //DataSource
        single { RemoteUserDataSourceImpl(FirebaseFirestore.getInstance()) }
        single { RemoteMenuDataSourceImpl(FirebaseFirestore.getInstance()) }
        single { RemoteOrderDataSourceImpl(FirebaseFirestore.getInstance()) }
        single { RemoteAuthenticationDataSourceImpl(FirebaseAuth.getInstance()) }
    }
}
