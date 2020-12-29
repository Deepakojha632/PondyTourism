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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.AttractionViewHolder> {
    List<String> categories;
    Context context;
    ItemClickListener cLickListener;

    public CategoryAdapter(Context context, List<String> categories) {
        this.context = context;
        this.categories = categories;
    }


    @NonNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_item, parent, false);
        return new AttractionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.AttractionViewHolder holder, int position) {
        holder.title.setText(categories.get(position));
        int[] images = {R.drawable.beaches, R.drawable.museums, R.drawable.temples, R.drawable.lakesandgarden};
        Glide.with(context)
                .asBitmap()
                .load(images[position])
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.cLickListener = itemClickListener;
    }

    public class AttractionViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public AttractionViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.categoryName);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(v -> {
                if (cLickListener != null) cLickListener.onItemClick(v, getAdapterPosition());
            });
        }
    }

}
