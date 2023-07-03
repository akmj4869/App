package com.example.myapplication;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Fragment2 extends Fragment {
    static ImageDataAdapter imageDataAdapter;
    private RecyclerView imageRecyclerView;
    private GridLayoutManager layoutManager;
    static String currentDir;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        ArrayList<String> listItems = new ArrayList<>();
        currentDir = getActivity().getExternalFilesDir(null).toString();
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
        imageRecyclerView = view.findViewById(R.id.Images);
        ImageButton camera = view.findViewById(R.id.camera);
        camera.bringToFront();
        imageDataAdapter = new ImageDataAdapter(listItems);
        layoutManager =  new GridLayoutManager(getActivity(), 2);
        imageRecyclerView.setLayoutManager(layoutManager);
        imageRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 75, true));
        imageRecyclerView.setAdapter(imageDataAdapter);
        camera.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Camera.class);
            getActivity().startActivity(intent);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        imageRecyclerView.setAdapter(null);
        imageRecyclerView.setLayoutManager(null);
        imageDataAdapter.notifyDataSetChanged();
        imageRecyclerView.setAdapter(imageDataAdapter);
        imageRecyclerView.setLayoutManager(layoutManager);
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

