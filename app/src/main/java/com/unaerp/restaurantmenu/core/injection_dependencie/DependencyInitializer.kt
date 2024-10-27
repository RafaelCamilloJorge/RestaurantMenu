import com.unaerp.restaurantmenu.core.repositories.auth.impl.AuthRepositoryImpl
import org.koin.dsl.module

class DependencyInitializer {
    val appModule = module {
        //Repositories
        single { AuthRepositoryImpl() }
        //ViewModels
//        viewModel { LoginViewModel(get()) }


    }
}
