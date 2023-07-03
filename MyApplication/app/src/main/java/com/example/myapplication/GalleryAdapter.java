package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class GalleryAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> paths;
    LayoutInflater layoutInflater;

    public GalleryAdapter(Context context, ArrayList<String> paths) {
        this.context = context;
        this.paths = paths;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position){
        View itemView = layoutInflater.inflate(R.layout.expanded_image, container, false);
        ImageView imageView = itemView.findViewById(R.id.image);
        String imgname = paths.get(position);
        imageView.setImageURI(Uri.fromFile(new File(context.getExternalFilesDir(null).toString() + File.separator + "images" + File.separator + imgname)));
        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object){
        container.removeView((LinearLayout) object);
    }
}
