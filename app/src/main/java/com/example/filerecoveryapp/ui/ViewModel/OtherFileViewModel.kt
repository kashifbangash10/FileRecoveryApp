//package com.example.filerecoveryapp.ui.ViewModel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.filerecoveryapp.data.FileRepository.FileRepository
//import com.example.filerecoveryapp.data.FileRepository.OtherFileRepository
//import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderItem
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class OtherFileViewModel @Inject constructor(private val otherFileRepository: OtherFileRepository) : ViewModel() {
//
//    private val _mediaFolders = MutableLiveData<List<FolderItem>>()
//    val mediaFolders: LiveData<List<FolderItem>> get() = _mediaFolders
//
//    private val _otherFiles = MutableLiveData<List<FolderItem>>()
//    val otherFiles: LiveData<List<FolderItem>> get() = _otherFiles
//
//    fun loadMediaFolders() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val mediaData = otherFileRepository.getMediaFolders1()
//            _mediaFolders.postValue(mediaData)
//        }
//    }
//
//
//    fun loadOtherFiles() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val fileData = otherFileRepository.getMediaFolders1()
//            _otherFiles.postValue(fileData)
//        }
//    }
//}
