package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fragment2 extends Fragment {
    private ImageDataAdapter imageDataAdapter;
    static ArrayList<String> listItems;
    private ActivityResultLauncher<Intent> launcher;
    static String currentDir;
    SimpleDateFormat format;
    String file_name, f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listItems = new ArrayList<>();
        currentDir = getActivity().getExternalFilesDir(null).toString();
        File imageDir = new File(currentDir + File.separator + "images");
        File imgdir = new File(currentDir + File.separator + "texts");
        if (!imgdir.exists()) {
            imgdir.mkdirs();
        }
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri imageUri = data.getData();
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (bitmap != null) {
                        try{
                            File imagefile = new File(imageDir, file_name);
                            FileOutputStream output = new FileOutputStream(imagefile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, output);
                            output.flush();
                            output.close();
                            File img = new File(imgdir, "filetexts.txt");
                            if (!img.exists()) {
                                try {
                                    img.createNewFile();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(img, true);
                                fileOutputStream.write(f.getBytes());
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getActivity(), "Image Loaded", Toast.LENGTH_SHORT).show();
                        } catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        File imgdir = new File(currentDir + File.separator + "texts");
        if (!imgdir.exists()) {
            imgdir.mkdirs();
        }
        File imgfile = new File(imgdir, "filetexts.txt");
        if (imgfile.exists()){
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(imgfile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            try {
                String line;
                while((line = reader.readLine()) != null) {
                    listItems.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        RecyclerView imageRecyclerView = view.findViewById(R.id.Images);
        ImageButton camera = view.findViewById(R.id.camera);
        ImageButton gallery = view.findViewById(R.id.gallery);
        camera.bringToFront();
        gallery.bringToFront();
        imageDataAdapter = new ImageDataAdapter(listItems);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        imageRecyclerView.setLayoutManager(layoutManager);
        imageRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 75, true));
        imageRecyclerView.setAdapter(imageDataAdapter);
        camera.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Camera.class);
            requireActivity().startActivity(intent);
        });
        gallery.setOnClickListener(v -> {
            format = new SimpleDateFormat("HH.mm.ss");
            file_name = format.format(new Date()) + ".jpg";
            f = file_name + "\n";
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            launcher.launch(Intent.createChooser(galleryIntent, "Select Picture"));
            listItems.add(file_name);
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        imageDataAdapter.notifyDataSetChanged();
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }
}
