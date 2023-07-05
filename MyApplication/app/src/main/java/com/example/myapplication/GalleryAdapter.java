package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GalleryAdapter extends PagerAdapter {
    Context context;
    static ArrayList<String> paths;
    LayoutInflater layoutInflater;

    public GalleryAdapter(Context context, ArrayList<String> paths) {
        this.context = context;
        GalleryAdapter.paths = paths;
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
        TextView filename = itemView.findViewById(R.id.filename);
        String imgname = paths.get(position);
        Uri uri = Uri.fromFile(new File(Fragment2.currentDir + File.separator + "images" + File.separator + imgname));
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //imageView.setImageURI(Uri.fromFile(new File(context.getExternalFilesDir(null).toString() + File.separator + "images" + File.separator + imgname)));
        imageView.setImageBitmap(bitmap);
        filename.setText(paths.get(position).replace(".jpg", ""));
        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object){
        container.removeView((LinearLayout) object);
    }
}
