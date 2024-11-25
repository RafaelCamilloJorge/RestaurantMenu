import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.use_case.order.OrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuDescriptionViewModel(private var orderUseCaseImpl: OrderUseCase) : ViewModel() {
    private val _cartState = MutableStateFlow<Result<Unit>?>(null)
    val cartState = _cartState.asStateFlow()


    fun addItemInCart(menuItem: ResponseMenuItem, quantity: Long) {
        viewModelScope.launch {
            val response = orderUseCaseImpl.itemInShoppingCar(menuItem, quantity)
            response.fold(
                onSuccess = {
                    _cartState.value = Result.success(it)
                },
                onError = {
                    _cartState.value = Result.failure(it)
                }
            )
        }
    }
}