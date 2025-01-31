package com.example.filerecoveryapp.data.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

    @HiltViewModel
    class RecoveryPhotoViewModel @Inject constructor(
        private val repository: RecoveryRepository
    ) : ViewModel() {

        val recoveredFiles = repository.recoveredFiles

        fun scanDeletedFiles(files: List<RecoveryFile>) = viewModelScope.launch {
            files.forEach { repository.insertFile(it) }
        }
    }
