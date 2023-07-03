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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Fragment2 extends Fragment {
    static ImageDataAdapter imageDataAdapter;
    private RecyclerView imageRecyclerView;
    private GridLayoutManager layoutManager;
    private ImageButton camera;
    static String currentDir;
    private String loadJsonFile() {
        String json = null;
        try {
            InputStream inputStream = getActivity().getAssets().open("jsons/image.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        ArrayList<String> listItems = new ArrayList<>();
        currentDir = getActivity().getFilesDir().toString();
        File imgfile = new File(currentDir + "/imagefiles/filetexts.txt");
        if (imgfile.exists()){
            FileInputStream fileInputStream = null;
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
        /*
        try {
            JSONObject jsonObject = new JSONObject(loadJsonFile());
            JSONArray jsonArray = jsonObject.getJSONArray("images");
            FileOutputStream output = null;
            String directory = getContext().getFilesDir().toString();
            File file = new File(directory + "/images");
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                String image = obj.getString("image");
                listItems.add(image);
                /*
                bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(image, "string", this.getActivity().getPackageName()));
                try {
                    output = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                    output.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        assert (output != null);
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        */
        imageRecyclerView = view.findViewById(R.id.Images);
        camera = view.findViewById(R.id.camera);
        camera.bringToFront();
        imageDataAdapter = new ImageDataAdapter(listItems);
        layoutManager =  new GridLayoutManager(getActivity(), 2);
        imageRecyclerView.setLayoutManager(layoutManager);
        imageRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 75, true));
        imageRecyclerView.setAdapter(imageDataAdapter);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Camera.class);
                getActivity().startActivity(intent);
            }
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

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}


