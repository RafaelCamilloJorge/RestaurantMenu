package com.unaerp.restaurantmenu.Feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.core.use_case.auth.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val authUseCaseImpl: AuthUseCase) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<Unit>?>(null)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = authUseCaseImpl.loginWithEmailAndPassword(email, password)
                result.fold(
                    onSuccess = {
                        _loginState.value = Result.success(Unit)
                    },
                    onError = { error ->
                        _loginState.value = Result.failure(error)
                    }
                )
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
            }
        }
    }
}

//---------------------------------ADD products-----------------------------------------------

//var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//var products: MutableList<Map<String, Any>> = mutableListOf()
//
////Porções
//products.add(mapOf("description" to "Mini salgados 12uni","name" to "Mini salgados 12uni","price" to 25.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Batata simples","name" to "Batata simples","price" to 27.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Batata com bacon e catupiry","name" to "Batata com bacon e catupiry","price" to 35.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Filé de tilapia","name" to "Filé de tilapia","price" to 60.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Frango à passarinho","name" to "Frango à passarinho","price" to 37.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Torresmo","name" to "Torresmo","price" to 42.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Calabresa acebolada","name" to "Calabresa acebolada","price" to 35.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Anel de cebola","name" to "Anel de cebola","price" to 34.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Linguiça suína","name" to "Linguiça suína","price" to 42.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Contra filé acebolado","name" to "Contra filé acebolado","price" to 62.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Picanha","name" to "Picanha","price" to 72.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Codeguim empanado","name" to "Codeguim empanado","price" to 42.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Ovo de codorna","name" to "Ovo de codorna","price" to 42.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Salame","name" to "Salame","price" to 27.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Isca de frango","name" to "Isca de frango","price" to 42.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Frios","name" to "Frios","price" to 52.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//products.add(mapOf("description" to "Mista","name" to "Mista","price" to 52.00,"type" to "Porções", "image" to "/produtos/porcao.png"))
//
//
////Espetos
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Queijo","price" to 10.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Kafta","price" to 8.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Kafta com queijo","price" to 9.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Pedaço","price" to 8.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Coração","price" to 9.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Linguiça","price" to 9.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Costela","price" to 8.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Medalão de frango","price" to 9.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Pão de alho","price" to 6.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Espeto de mandioca","price" to 8.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//products.add(mapOf("description" to "O espeto acompanham patê de alho e molho pimenta","name" to "Espeto de quibe","price" to 8.00,"type" to "Espetos", "image" to "/produtos/espeto.png"))
//
//
////Lanches
//products.add(mapOf("description" to "Presunto, mussarela, orégano e tomate","name" to "Misto quente","price" to 22.00,"type" to "Lanches", "image" to "/produtos/lanche.png"))
//products.add(mapOf("description" to "Contra filé, tomate, mussarela e cebola","name" to "Bauru","price" to 36.00,"type" to "Lanches", "image" to "/produtos/lanche.png"))
//products.add(mapOf("description" to "2 hambúrgueres, presunto, mussarela e cheddar","name" to "Duplo buguer","price" to 36.00,"type" to "Lanches", "image" to "/produtos/lanche.png"))
//products.add(mapOf("description" to "Hambúrguer, presunto e mussarela","name" to "X-burguer","price" to 24.00,"type" to "Lanches", "image" to "/produtos/lanche.png"))
//products.add(mapOf("description" to "Hambúrguer, bacon, presunto, mussarela, alface e tomate","name" to "X-bacon","price" to 27.00,"type" to "Lanches", "image" to "/produtos/lanche.png"))
//products.add(mapOf("description" to "Hambúrguer, presunto, mussarela, bacon, ovo, milho, alface e tomate","name" to "X-tudo","price" to 30.00,"type" to "Lanches", "image" to "/produtos/lanche.png"))
//products.add(mapOf("description" to "Hambúrguer, filé de frango, presunto, mussarela e catupiry","name" to "X-frango","price" to 36.00,"type" to "Lanches", "image" to "/produtos/lanche.png"))
//products.add(mapOf("description" to "Hambúrguer, ovo, tomate, alface, presunto e mussarela","name" to "X-egg salada","price" to 30.00,"type" to "Lanches", "image" to "/produtos/lanche.png"))
//products.add(mapOf("description" to "Filé de frango, presunto, mussarela, alface, tomate, milho ecatupiry","name" to "X-frango salada","price" to 32.00,"type" to "Lanches", "image" to "/produtos/lanche.png"))
//products.add(mapOf("description" to "Hambúrguer, presunto, mussarela, milho, alface e tomate","name" to "X-salada","price" to 29.00,"type" to "Lanches", "image" to "/produtos/lanche.png"))
//
//
////Pizzas
//products.add(mapOf("description" to "Mto, mussarela, orégano tomate e azeitona","name" to "Mussarela","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Mto, mussarela, parmesão, catupiry, provolone, orégano e azeitona","name" to "4 queijos","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Mto, frango desfiado, mussarela, catupiry, orégano e azeitona","name" to "Frango com catupiry","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Mto, frango desfiado, bacon, mussarela, catupiry e azeitona","name" to "Frango com bacon","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Mto, presunto, mussarela, orégano e azeitona","name" to "Da janta","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Mto, presunto, mussarela, palmito, ovo, milho, cebola, tomate, ervilha, orégano e azeitona","name" to "Portuguesa","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Mto, presunto, mussarela, palmito, ovo, bacon, cebola, tomate, calabresa, orégano e azeitona","name" to "A moda da cassa","price" to 70.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Mto, cababresa, mussarela, orégano, cebola e azeitona","name" to "Toscana","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Mto, presunto, ervilha, bacon, cebola, tomate, orégano e azeitona","name" to "Paulista","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Mto, presunto, mussarela, calabresa, catupiry, pimenta calabresa, orégano e azeitona","name" to "Mexicana","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Mto, filé mignon, mussarela, alho e catupiry","name" to "Filé ao alho","price" to 70.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Banana, chocolate branco, açúcar e canela","name" to "Banana nevada","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Brigadeiro e chocolate ralado","name" to "Brigadeiro","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//products.add(mapOf("description" to "Chocolate branco e chocolate ao leite","name" to "Chocolate mesclado","price" to 60.00,"type" to "Pizzas", "image" to "/produtos/pizza.png"))
//
//
////Hot dogs
//products.add(mapOf("description" to "Salsicha, milho, batata palha, ketchup, maionese e mostarda","name" to "Hot dog simples","price" to 14.00,"type" to "Hot dogs", "image" to "/produtos/hotdog.png"))
//products.add(mapOf("description" to "Salsicha, bacon, milho, batata palha, ketchup, maionese e mostarda","name" to "Hot dog bacon","price" to 21.00,"type" to "Hot dogs", "image" to "/produtos/hotdog.png"))
//products.add(mapOf("description" to "Salsicha, frango, milho, batata palha, ketchup, maionese e mostarda","name" to "Hot dog frango","price" to 22.00,"type" to "Hot dogs", "image" to "/produtos/hotdog.png"))
//
//
////Cervejas
//products.add(mapOf("description" to "Skol lata 350ml","name" to "Skol lata 350ml","price" to 5.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Antarctica lata 350ml","name" to "Antarctica lata 350ml","price" to 5.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Heineken lata 350ml","name" to "Heineken lata 350ml","price" to 8.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Brahma lata 350ml","name" to "Brahma lata 350ml","price" to 5.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Brahma zero lata 350ml","name" to "Brahma zero lata 350ml","price" to 6.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Brahma malzbier lata 350ml","name" to "Brahma malzbier lata 350ml","price" to 6.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Skol 600ml","name" to "Skol 600ml","price" to 10.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Brahma 600ml","name" to "Brahma 600ml","price" to 12.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Original 600ml","name" to "Original 600ml","price" to 15.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Budweiwer 600ml","name" to "Budweiwer 600ml","price" to 12.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Antarctica 600ml","name" to "Antarctica 600ml","price" to 12.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Heineken 600ml","name" to "Heineken 600ml","price" to 10.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Energético red bull 250ml","name" to "Energético red bull 250ml","price" to 14.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Energético monster 473ml","name" to "Energético monster 473ml","price" to 14.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Corote com gelo","name" to "Corote com gelo","price" to 35.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Caipirinha com vodka","name" to "Caipirinha com vodka","price" to 10.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Caipirinha com pinga","name" to "Caipirinha com pinga","price" to 20.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Whisky com energético","name" to "Whisky com energético","price" to 15.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Vodka com suco del valle","name" to "Vodka com suco del valle","price" to 20.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Vinho taça","name" to "Vinho taça","price" to 15.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Chopp caneca","name" to "Chopp caneca","price" to 12.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Chopp copo","name" to "Chopp copo","price" to 12.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//products.add(mapOf("description" to "Original lata 350ml","name" to "Original lata 350ml","price" to 7.00,"type" to "Cervejas", "image" to "/produtos/cerveja.png"))
//
//
////Refrigerantes
//products.add(mapOf("description" to "Água com gás 510ml","name" to "Água com gás 510ml","price" to 4.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Água sem gás 510ml","name" to "Água sem gás 510ml","price" to 4.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Água tônica lata 350ml ","name" to "Água tônica lata 350ml ","price" to 6.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Schweppes lata 350ml ","name" to "Schweppes lata 350ml ","price" to 6.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Refrigerante lata 350ml","name" to "Refrigerante lata 350ml","price" to 6.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Coca-cola ks 290ml","name" to "Coca-cola ks 290ml","price" to 5.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Coca-cola 600ml","name" to "Coca-cola 600ml","price" to 8.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Coca-cola 1 litro","name" to "Coca-cola 1 litro","price" to 10.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Coca-cola 2 litro","name" to "Coca-cola 2 litro","price" to 14.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Guaraná antarctica 2 litros","name" to "Guaraná antarctica 2 litros","price" to 12.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Suco kmais 300ml","name" to "Suco kmais 300ml","price" to 7.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Suco kmais 900ml","name" to "Suco kmais 900ml","price" to 15.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Suco natural laranja copo","name" to "Suco natural laranja copo","price" to 12.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//products.add(mapOf("description" to "Suco natural laranja 1 litro","name" to "Suco natural laranja 1 litro","price" to 20.00,"type" to "Refrigerantes", "image" to "/produtos/refrigerante.png"))
//
//
//
//products.forEach {
//    firestore.collection("product").add(it)
//}