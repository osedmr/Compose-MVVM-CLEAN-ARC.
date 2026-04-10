package com.galerkinrobotics.productproject.feature.product.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galerkinrobotics.productproject.feature.product.domain.usecase.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductListUiState.Loading
            runCatching { getProductUseCase() }
                .onSuccess { products ->
                    _uiState.value = ProductListUiState.Success(products)
                }
                .onFailure { throwable ->
                    _uiState.value = ProductListUiState.Error(
                        message = throwable.message.orEmpty().ifBlank { "Unknown error" },
                    )
                }
        }
    }
}