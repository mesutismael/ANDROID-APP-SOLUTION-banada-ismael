package me.esmael.newsnow.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import me.esmael.newsnow.R;

/**
 * Created by esmael256 on 4/24/2017.
 */

public class ImageUtils {
    public static void loadImage(Context context,ImageView imageView, String imageUrl)
    {
        Picasso.with(context).load(imageUrl).placeholder(R.mipmap.ic_place_holder)
                .error(R.mipmap.ic_place_holder)
                .into(imageView);
    }
}
