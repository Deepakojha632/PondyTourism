package com.example.pondytourism;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder> {
    List<String> data;
    List<String> dataDetail;
    List<String> images;
    Context context;
    ItemClickListener itemClickListener;

    public AttractionAdapter(Context context, List<String> data, List<String> dataDetail, List<String> images) {
        this.context = context;
        this.data = data;
        this.images = images;
        this.dataDetail = dataDetail;
    }

    @NonNull
    @Override
    public AttractionAdapter.AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attractionitem, parent, false);
        return new AttractionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionAdapter.AttractionViewHolder holder, int position) {
        holder.title.setText(data.get(position));
        holder.detail.setText(dataDetail.get(position));
        int resourceImage = context.getResources().getIdentifier(images.get(position), "drawable", context.getPackageName());
        Glide.with(context)
                .asBitmap()
                .load(resourceImage)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class AttractionViewHolder extends RecyclerView.ViewHolder {
        TextView title, detail;
        ImageView image;

        public AttractionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            detail = itemView.findViewById(R.id.detail);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(v, getAdapterPosition());
            });
        }
    }
}
