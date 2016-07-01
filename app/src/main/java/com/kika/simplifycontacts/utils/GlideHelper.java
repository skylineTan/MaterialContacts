package com.kika.simplifycontacts.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kika.simplifycontacts.ContactsApplication;
import com.kika.simplifycontacts.R;

/**
 * Created by skylineTan on 2016/6/30.
 */
public class GlideHelper {

    private static GlideHelper instance;


    private GlideHelper() {

    }


    public static GlideHelper getInstance() {
        if (instance == null) {
            synchronized (GlideHelper.class) {
                if (instance == null) {
                    instance = new GlideHelper();
                }
            }
        }
        return instance;
    }


    public void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
             .load(url)
             .asBitmap()
             .centerCrop()
             .placeholder(R.drawable.place_iv_shape)
             .error(R.drawable.place_iv_shape)
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .into(imageView);
    }

    @SuppressWarnings("unchecked")
    public void loadLocalImage(Context context,int resId, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context)
             .load(resId)
             .asBitmap()
             .centerCrop()
             .into(simpleTarget);
    }
}

