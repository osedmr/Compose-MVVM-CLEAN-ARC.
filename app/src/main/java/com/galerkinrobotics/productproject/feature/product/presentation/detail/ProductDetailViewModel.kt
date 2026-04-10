package com.galerkinrobotics.productproject.feature.product.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galerkinrobotics.productproject.feature.product.domain.usecase.DeleteProductUseCase
import com.galerkinrobotics.productproject.feature.product.domain.usecase.GetProductByIdUseCase
import com.galerkinrobotics.productproject.feature.product.presentation.navigation.ProductNavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductDetailEffect {
    data object NavigateBack : ProductDetailEffect
}

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle[ProductNavArgs.PRODUCT_ID])

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private val _effects = Channel<ProductDetailEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        loadProduct()
    }

    fun loadProduct() {
        viewModelScope.launch {
            _uiState.value = ProductDetailUiState.Loading
            runCatching { getProductByIdUseCase(productId) }
                .onSuccess { product ->
                    _uiState.value = if (product != null) {
                        ProductDetailUiState.Loaded(product)
                    } else {
                        ProductDetailUiState.NotFound
                    }
                }
                .onFailure { e ->
                    _uiState.value = ProductDetailUiState.Error(
                        message = e.message.orEmpty().ifBlank { "Error" },
                    )
                }
        }
    }

    fun deleteCurrentProduct() {
        val state = _uiState.value
        if (state !is ProductDetailUiState.Loaded) return
        viewModelScope.launch {
            runCatching { deleteProductUseCase(state.product) }
                .onSuccess {
                    _effects.send(ProductDetailEffect.NavigateBack)
                }
        }
    }
}
