package com.example.myapplication;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Fragment2 extends Fragment {
    private ImageDataAdapter imageDataAdapter;
    private RecyclerView imageRecyclerView;
    private GridLayoutManager layoutManager;

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
        try {
            JSONObject jsonObject = new JSONObject(loadJsonFile());
            JSONArray jsonArray = jsonObject.getJSONArray("images");
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                String image = obj.getString("image");
                listItems.add(image);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        imageRecyclerView = view.findViewById(R.id.Images);
        imageDataAdapter = new ImageDataAdapter(listItems);
        layoutManager =  new GridLayoutManager(getActivity(), 2);
        imageRecyclerView.setLayoutManager(layoutManager);
        imageRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 75, true));
        imageRecyclerView.setAdapter(imageDataAdapter);
        return view;
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


