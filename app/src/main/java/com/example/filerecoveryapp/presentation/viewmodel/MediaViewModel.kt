package com.example.filerecoveryapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filerecoveryapp.domain.model.MediaFile
import com.example.filerecoveryapp.domain.model.MediaFolder
import com.example.filerecoveryapp.domain.model.MediaType
import com.example.filerecoveryapp.domain.repository.MediaRepository
import com.example.filerecoveryapp.domain.repository.StorageInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MediaUiState())
    val uiState: StateFlow<MediaUiState> = _uiState.asStateFlow()

    private val _scanProgress = MutableStateFlow(0)
    val scanProgress: StateFlow<Int> = _scanProgress.asStateFlow()

    private val _storageInfo = MutableStateFlow<StorageInfo?>(null)
    val storageInfo: StateFlow<StorageInfo?> = _storageInfo.asStateFlow()

    init {
        loadStorageInfo()
    }

    fun scanMedia(type: MediaType) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                currentScanType = type
            )

            try {
                _scanProgress.value = 0
                
                // Simulate scanning progress
                for (i in 1..100 step 10) {
                    _scanProgress.value = i
                    kotlinx.coroutines.delay(100)
                }

                val folders = mediaRepository.getMediaFolders(type)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    folders = folders,
                    scanComplete = true
                )
                
                _scanProgress.value = 100
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred",
                    scanComplete = false
                )
            }
        }
    }

    fun scanAllMedia() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                currentScanType = null
            )

            try {
                _scanProgress.value = 0
                
                // Simulate scanning progress
                for (i in 1..100 step 5) {
                    _scanProgress.value = i
                    kotlinx.coroutines.delay(50)
                }

                val folders = mediaRepository.getAllMediaFolders()
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    folders = folders,
                    scanComplete = true
                )
                
                _scanProgress.value = 100
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred",
                    scanComplete = false
                )
            }
        }
    }

    private fun loadStorageInfo() {
        viewModelScope.launch {
            try {
                val info = mediaRepository.getStorageInfo()
                _storageInfo.value = info
            } catch (e: Exception) {
                // Handle error silently for storage info
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun resetScan() {
        _uiState.value = _uiState.value.copy(
            scanComplete = false,
            folders = emptyList()
        )
        _scanProgress.value = 0
    }
}

data class MediaUiState(
    val isLoading: Boolean = false,
    val folders: List<MediaFolder> = emptyList(),
    val error: String? = null,
    val scanComplete: Boolean = false,
    val currentScanType: MediaType? = null
)