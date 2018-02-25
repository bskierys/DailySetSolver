package pl.ipebk.setsolver.ui.detail

import android.databinding.BindingAdapter
import android.widget.ImageView
import pl.ipebk.setsolver.extensions.loadImage

@BindingAdapter("android:src")
fun setImageBinding(view: ImageView, url: String){
    view.loadImage(url)
}