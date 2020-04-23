package com.example.clonestagram.navigation.addPost

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi

class GalleryLoader(private val context : Context) {
    @RequiresApi(Build.VERSION_CODES.Q)
    fun loadImages(): Pair<HashMap<String, ArrayList<Uri>>, ArrayList<String>> {
        val imageList = HashMap<String, ArrayList<Uri>>()
        val bucketList = ArrayList<String>()

        imageList["Gallery"] = ArrayList()
        imageList["Photos"] = ArrayList()
        imageList["Videos"] = ArrayList()
        bucketList.addAll(arrayListOf("Gallery", "Photos", "Videos"))

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val bucketNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val bucketName = cursor.getString(bucketNameColumn)
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                if(imageList[bucketName] == null) {
                    imageList[bucketName] = ArrayList()
                    bucketList.add(bucketName)
                }
                imageList[bucketName]?.add(contentUri)
                imageList["Gallery"]?.add(contentUri)
                imageList["Photos"]?.add(contentUri)
            }
        }

        return Pair(imageList, bucketList)
    }
}