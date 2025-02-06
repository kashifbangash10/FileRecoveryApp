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
        _scanState.postValue(ScanState.Scanning("Starting scan...", 0)) // Initialize scanning state
        _scanProgress.postValue(0)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Simulate scanning progress with current file name
                for (i in 1..100 step 10) {
                    val currentFile = "/storage/emulated/$i/" // Simulate a file path being scanned
                    _scanState.postValue(ScanState.Scanning(currentFile, i)) // Update scanning state with the file name and progress
                    _scanProgress.postValue(i)
                    delay(200) // Simulated delay for scanning
                }

                // After scanning simulation, fetch data from repository
                val data = repository.getMediaFolders()
                _scanState.postValue(ScanState.Success(data))
            } catch (e: Exception) {
                _scanState.postValue(ScanState.Error(e.message ?: "An error occurred"))
            }
        }
    }
}
