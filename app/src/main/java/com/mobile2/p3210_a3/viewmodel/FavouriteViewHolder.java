package com.mobile2.p3210_a3.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile2.p3210_a3.ItemClickListener;
import com.mobile2.p3210_a3.R;

public class FavouriteViewHolder extends RecyclerView.ViewHolder {

    // adding only the three standard params for now
    ImageView posterImageView;
    TextView title;
    TextView year;

    ItemClickListener clickListener;

    public FavouriteViewHolder(@NonNull View itemView, ItemClickListener clickListener){
        super(itemView);

        posterImageView = itemView.findViewById(R.id.imageviewPoster);
        title = itemView.findViewById(R.id.title_txt);
        year = itemView.findViewById(R.id.year_text);

        this.clickListener = clickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("tag", "onClick: " + title);
                clickListener.onClick(v, getAdapterPosition());
            }
        });
    }
}
