package com.galerkinrobotics.productproject.feature.product.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galerkinrobotics.productproject.feature.product.domain.model.Product
import com.galerkinrobotics.productproject.feature.product.domain.usecase.AddProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductAddEffect {
    data object NavigateBack : ProductAddEffect
}

@HiltViewModel
class ProductAddViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductAddUiState())
    val uiState: StateFlow<ProductAddUiState> = _uiState.asStateFlow()

    private val _effects = Channel<ProductAddEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value, errorMessage = null) }
    }

    fun onDescriptionChange(value: String) {
        _uiState.update { it.copy(description = value) }
    }

    fun onIconUriChange(uriString: String) {
        _uiState.update { it.copy(iconUri = uriString) }
    }

    fun onImageUriChange(uriString: String) {
        _uiState.update { it.copy(imageUri = uriString) }
    }

    fun save() {
        val name = _uiState.value.name.trim()
        if (name.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "NAME_REQUIRED") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }
            val s = _uiState.value
            runCatching {
                addProductUseCase(
                    Product(
                        id = 0,
                        name = name,
                        icon = s.iconUri.trim(),
                        image = s.imageUri.trim(),
                        description = s.description.trim(),
                    ),
                )
            }.onSuccess {
                _effects.send(ProductAddEffect.NavigateBack)
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = e.message.orEmpty().ifBlank { "SAVE_FAILED" },
                    )
                }
            }
        }
    }
}
