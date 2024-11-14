import com.unaerp.restaurantmenu.core.repositories.auth.AuthRepository
import com.unaerp.restaurantmenu.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class DependencyInitializer {
    val appModule = module {
        // Repositórios
        single { AuthRepositoryImpl() }
        // ViewModels
        viewModel { MainViewModel(get()) }
    }
}
