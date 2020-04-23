package com.example.clonestagram.navigation.addPost.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clonestagram.navigation.addPost.GalleryLoader

@Suppress("UNCHECKED_CAST")
class AddPhotoViewModelFactory(val mLoader: GalleryLoader) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = AddPhotoViewModel(loader = mLoader) as T
}