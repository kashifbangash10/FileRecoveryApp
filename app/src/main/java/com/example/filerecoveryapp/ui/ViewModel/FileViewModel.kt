package com.example.filerecoveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.filerecoveryapp.data.FileRepository.FileRepository

import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Define different states
sealed class ScanState {
    data class Scanning(val currentFileName: String, val scanProgress: Int) : ScanState() // File name and progress
    data class Success(val data: List<FolderItem>) : ScanState() // Folders data after scan
    data class Error(val message: String) : ScanState() // Error state
}

@HiltViewModel
class FileViewModel @Inject constructor(private val repository: FileRepository) : ViewModel() {

    private val _scanState = MutableLiveData<ScanState>()
    val scanState: LiveData<ScanState> = _scanState

    private val _scanProgress = MutableLiveData<Int>()
    val scanProgress: LiveData<Int> = _scanProgress

    fun fetchMediaFolders() {
        _scanState.value = ScanState.Scanning("Initializing scan...", 0)
        _scanProgress.value = 0

        viewModelScope.launch {
            try {
                for (i in 1..100 step 10) {
                    val currentFile = "/storage/emulated/$i/"
                    withContext(Dispatchers.Main) {
                        _scanState.value = ScanState.Scanning(currentFile, i)
                        _scanProgress.value = i
                    }
                    delay(200) // Simulated delay
                }

                // Fetch media folders on IO thread
                val data = withContext(Dispatchers.IO) { repository.getMediaFolders() }

                // Update UI on Main thread
                _scanState.value = ScanState.Success(data)

            } catch (e: Exception) {
                _scanState.value = ScanState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
