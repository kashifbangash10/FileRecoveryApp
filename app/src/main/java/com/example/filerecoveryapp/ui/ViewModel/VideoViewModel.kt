package com.example.filerecoveryapp.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filerecoveryapp.data.FileRepository.VideoRepository

import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderItem
import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.ScanResultAdaptor.VideoFolder // ✅ Ensure Correct Import
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val repository: VideoRepository) : ViewModel() {

    private val _videoFolders = MutableLiveData<List<VideoFolder>>() // LiveData should hold VideoFolder
    val videoFolders: LiveData<List<VideoFolder>> = _videoFolders

    fun fetchVideoFolders() {
        viewModelScope.launch(Dispatchers.IO) { // Runs in the IO thread for better performance
            val data = repository.getVideoFolders()
            _videoFolders.postValue(data) // Updates LiveData on the main thread
        }
    }
}
