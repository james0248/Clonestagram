package com.example.clonestagram.navigation.addPost.viewmodel

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonestagram.navigation.addPost.GalleryLoader
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@RequiresApi(Build.VERSION_CODES.Q)
class AddPhotoViewModel(var imageList: MutableLiveData<HashMap<String, ArrayList<Uri>>> = MutableLiveData(),
                        var bucketList : MutableLiveData<ArrayList<String>> = MutableLiveData(),
                        var displayImageList : MutableLiveData<ArrayList<Uri>> = MutableLiveData(),
                        var selectedImageList : MutableLiveData<ArrayList<Uri>> = MutableLiveData(),
                        var selectedBucket : MutableLiveData<String> = MutableLiveData(),
                        var loader: GalleryLoader? = null
) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    var multiMode = false
    var fitImage = false

    init {
        imageList.value = HashMap()
        bucketList.value = ArrayList()
        displayImageList.value = ArrayList()
        selectedImageList.value = ArrayList()
        selectedBucket.value = ""

        loadDataFromGallery()
    }

    private fun loadDataFromGallery() {
        launch {
            val (_imageList, _bucketList) = withContext(Dispatchers.IO) {
                loader!!.loadImages()
            }
            imageList.value = _imageList
            bucketList.value = _bucketList
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}