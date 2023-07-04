package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter <MemoAdapter.ViewHolder>{
    private List<MemoItem> memoItemList;
    private Context context;
    private OnItemClickListener mListener;
    public MemoAdapter(List<MemoItem> memoItemList, Context context){
        this.context = context;
        this.memoItemList = memoItemList;

    }

    public void setData(List<MemoItem> list) {
        memoItemList = list;
    }
    public void addItem(MemoItem item){
        memoItemList.add(item);
    }
    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_item, parent, false);
        Context context = parent.getContext();
        return new MemoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemoItem item = memoItemList.get(position);
        holder.time.setText(item.getTime());
        holder.title.setText(item.getTitle());
        String preview = item.getPreview();
        if(preview.length() > 16)
            preview = preview.substring(0, 15) + "...";
        holder.preview.setText(preview);

        Log.d("dsf", "1");
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    String key = item.getKey();
                    mListener.onItemClick(key);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return memoItemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView time;
        private TextView title;
        private TextView preview;
        private String key;
        public ViewHolder(View itemView){
            super(itemView);

            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.title);
            preview = itemView.findViewById(R.id.preview);

        }

    }

    public interface OnItemClickListener {
        void onItemClick(String key); //you can pass whatever information you need from the item that will be clicked, here, i want to further use his position in the adapter's list
    }
}
