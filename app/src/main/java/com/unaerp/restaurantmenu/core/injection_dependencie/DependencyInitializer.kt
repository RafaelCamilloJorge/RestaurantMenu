import com.unaerp.restaurantmenu.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class DependencyInitializer {
    val appModule = module {
        //Repositories
        single { AuthRepositoryImpl() }
        //ViewModels
        viewModel { MainViewModel(get()) }


    }
}
