package com.example.clonestagram.navigation.addPost

import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.clonestagram.R
import com.example.clonestagram.navigation.addPost.viewmodel.AddPhotoViewModel
import com.example.clonestagram.navigation.addPost.viewmodel.AddPhotoViewModelFactory
import kotlinx.android.synthetic.main.activity_add_photo.*

class AddPhotoActivity : AppCompatActivity() {
    private val numColumn = 3
    private var screenWidth = 0
    private var gridSize = 0
    val TAG = "AddPhotoActivity_Debug"

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        screenWidth = size.x
        img_selected.layoutParams.height = screenWidth
        gridSize = ((screenWidth - 2 * numColumn * resources.displayMetrics.density) / 4).toInt()

        val factory = AddPhotoViewModelFactory(
            GalleryLoader(
                this
            )
        )
        val viewModel = ViewModelProvider(this, factory)[AddPhotoViewModel::class.java]

        img_gallery.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedImageList = ArrayList<Uri>()
            selectedImageList.add(viewModel.displayImageList.value!![position])
            viewModel.selectedImageList.value = selectedImageList
            img_gallery.smoothScrollToPositionFromTop(position, 1)
        }
        viewModel.selectedBucket.observe(this, Observer {
            if(it != null) {
                viewModel.displayImageList.value = viewModel.imageList.value!![it]
            }
        })
        viewModel.displayImageList.observe(this, Observer{
            if(it != null) {
                if(it.size == 0) {
                    Log.d(TAG, "display Empty")
                } else {
                    img_gallery.adapter =
                        ImageGridAdapter(
                            this,
                            it,
                            gridSize
                        )
                    val selectedImages = ArrayList<Uri>()
                    selectedImages.add(it.first())
                    viewModel.selectedImageList.value = selectedImages
                }
            }
        })
        viewModel.selectedImageList.observe(this, Observer {
            if(it != null) {
                if (it.size == 0) {
                    Log.d(TAG, "selected Empty")
                } else {
                    Glide
                        .with(this)
                        .load(it.last())
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade(200))
                        .into(img_selected)
                }
            }
        })
        viewModel.bucketList.observe(this, Observer {
            if(it != null) {
                spinner_upload.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it)

                spinner_upload.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        viewModel.selectedBucket.value = it[id.toInt()]
                    }
                }
            }
        })

        ic_cancel.setOnClickListener {
            finish()
        }
        next.setOnClickListener {
            val intent = Intent(this, AddNewPostActivity::class.java)
            intent.putExtra("SelectedImages", viewModel.selectedImageList.value)
            startActivity(intent)
        }
    }
}