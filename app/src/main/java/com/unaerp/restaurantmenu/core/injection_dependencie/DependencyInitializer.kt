package com.unaerp.restaurantmenu.core.injection_dependencie

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unaerp.restaurantmenu.Feature.cart.CartViewModel
import com.unaerp.restaurantmenu.Feature.ForgotPassword.ForgotPasswordViewModel
import com.unaerp.restaurantmenu.core.data_source.impl.RemoteMenuDataSourceImpl
import com.unaerp.restaurantmenu.core.data_source.impl.RemoteUserDataSourceImpl
import com.unaerp.restaurantmenu.core.data_source.impl.RemoteOrderDataSourceImpl
import com.unaerp.restaurantmenu.core.data_source.impl.RemoteAuthenticationDataSourceImpl
import com.unaerp.restaurantmenu.core.repositories.menu.impl.MenuRepositoryImpl
import com.unaerp.restaurantmenu.core.repositories.auth.impl.AuthRepositoryImpl
import com.unaerp.restaurantmenu.core.repositories.order.impl.OrderRepositoryImpl
import com.unaerp.restaurantmenu.core.repositories.user_data.impl.UserDataRepositoryImpl
import com.unaerp.restaurantmenu.core.use_case.auth.impl.AuthUseCaseImpl
import com.unaerp.restaurantmenu.core.use_case.menu.impl.MenuUseCaseImpl
import com.unaerp.restaurantmenu.core.use_case.order.impl.OrderUseCaseImpl
import com.unaerp.restaurantmenu.Feature.login.MainViewModel
import com.unaerp.restaurantmenu.Feature.menu.MenuViewModel
import com.unaerp.restaurantmenu.Feature.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class DependencyInitializer {
    val appModule = module {
        //ViewModels
        viewModel { MainViewModel(get<AuthUseCaseImpl>()) }
        viewModel { RegisterViewModel(get<AuthUseCaseImpl>()) }
        viewModel { MenuViewModel(get<MenuUseCaseImpl>()) }
        viewModel { CartViewModel(get<OrderUseCaseImpl>()) }
        viewModel { ForgotPasswordViewModel(get<AuthUseCaseImpl>()) }

        //UseCases
        factory { MenuUseCaseImpl(get<MenuRepositoryImpl>()) }
        factory { AuthUseCaseImpl(get<AuthRepositoryImpl>(), get<UserDataRepositoryImpl>()) }
        factory {
            OrderUseCaseImpl(
                get<AuthRepositoryImpl>(),
                get<OrderRepositoryImpl>(),
                get<UserDataRepositoryImpl>(),
                get<MenuRepositoryImpl>()
            )
        }

        //Repositories
        factory { AuthRepositoryImpl(get<RemoteAuthenticationDataSourceImpl>()) }
        factory { OrderRepositoryImpl(get<RemoteOrderDataSourceImpl>()) }
        factory { MenuRepositoryImpl(get<RemoteMenuDataSourceImpl>()) }
        factory { UserDataRepositoryImpl(get<RemoteUserDataSourceImpl>()) }

        //DataSource
        factory { RemoteUserDataSourceImpl(FirebaseFirestore.getInstance()) }
        factory { RemoteMenuDataSourceImpl(FirebaseFirestore.getInstance()) }
        factory { RemoteOrderDataSourceImpl(FirebaseFirestore.getInstance()) }
        factory { RemoteAuthenticationDataSourceImpl(FirebaseAuth.getInstance()) }
    }
}
