package com.example.clonestagram.navigation.addPost

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageGridAdapter(private val context: Context? = null,
                       private val imageURI: ArrayList<Uri> = ArrayList<Uri>(),
                       private val size: Int = 0
) :
    BaseAdapter() {
    private var params : RelativeLayout.LayoutParams? = null

    init {
        params = RelativeLayout.LayoutParams(size, size)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return (if (convertView != null) {
            convertView as ImageView
        } else {
            ImageView(context)
        }).also {
            it.layoutParams = params
            Glide
                .with(context!!)
                .load(imageURI[position])
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .into(it)
        }
    }

    override fun getItem(position: Int): Any {
        return imageURI[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return imageURI.size
    }
}