package com.mobile2.p3210_a3.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile2.p3210_a3.ItemClickListener;
import com.mobile2.p3210_a3.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView poserImageView;
    TextView title;
    TextView studio;            //not provided in general search
    TextView rating;            //not provided in general search
    TextView year;

    ItemClickListener clickListener;


    public MyViewHolder(@NonNull View itemView, ItemClickListener clickListener) {
        super(itemView);

        title = itemView.findViewById(R.id.title_txt);
        poserImageView = itemView.findViewById(R.id.imageviewPoster);
        //studio = itemView.findViewById(R.id.studio_txt);              //not provided in general search
        //rating = itemView.findViewById(R.id.rating_text);             //not provided in general search
        year = itemView.findViewById(R.id.year_text);

        this.clickListener = clickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "onClick: " + title);
                clickListener.onClick(v, getAdapterPosition());
            }
        });
    }
}
