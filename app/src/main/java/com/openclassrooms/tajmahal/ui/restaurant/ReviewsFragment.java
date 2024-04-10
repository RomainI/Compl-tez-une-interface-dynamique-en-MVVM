package com.openclassrooms.tajmahal.ui.restaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.adapter.ReviewAdapter;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReviewsFragment extends Fragment {

    private FragmentReviewsBinding binding;


    private ReviewViewModel reviewViewModel;

   /** private Review getReview() {
        return (Review) getArguments().getSerializable("REVIEW");
    }*/


    public static ReviewsFragment newInstance() {
        ReviewsFragment fragment = new ReviewsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void updateUIWithReviews(List<Review> reviewList) {

        if(reviewList==null)
        {
            return;
        }
        Log.d("reviewList", "updateUIWithReviews: non null");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(new ReviewAdapter(reviewList));
        binding.restaurantName.setText(reviewViewModel.getRestaurantName());
        //binding.recyclerView.addItemDecoration(new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL));

    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        reviewViewModel.getArrayReviews().observe(requireActivity(), this::updateUIWithReviews);
        ImageView imageView = binding.avatarNew;
        Picasso.get().load(getString(R.string.Girl)).into(imageView);

        binding.chipAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewViewModel.addComment(binding.name.getText()+"", getString(R.string.Girl),binding.commentEditText.getText()+"",(int) binding.stars.getRating());
                updateUIWithReviews(reviewViewModel.getArrayReviews().getValue());
                Log.d("valeur size", "onClick: "+reviewViewModel.getArrayReviews().getValue().size());
            }
        });
        binding.arrayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
//TODO

    private void setupViewModel() {
        reviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void back(){
        getParentFragmentManager().popBackStack();
    }
}
