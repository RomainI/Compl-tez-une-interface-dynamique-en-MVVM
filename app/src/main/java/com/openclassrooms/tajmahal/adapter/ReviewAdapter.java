package com.openclassrooms.tajmahal.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> reviewList;

    public ReviewAdapter (List<Review> reviewList){
        this.reviewList=reviewList;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(reviewList.get(position));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewComment;
        private ImageView imageViewAvatar;

        private RatingBar ratingBar;


        public ReviewViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            textViewComment = itemView.findViewById(R.id.comment);
            imageViewAvatar = itemView.findViewById(R.id.avatar);
            ratingBar = itemView.findViewById(R.id.stars);
            ratingBar.setNumStars(5);
        }

        public void bind(Review r) {
            textViewName.setText(r.getUsername());
            textViewComment.setText(r.getComment());

            Log.d("Picture", "bind: "+r.getPicture());
            //imageViewAvatar.setImageResource(r.getPicture());

            Picasso.get().load(r.getPicture()).into(imageViewAvatar);
            ratingBar.setRating(r.getRate());
        }
    }



}
