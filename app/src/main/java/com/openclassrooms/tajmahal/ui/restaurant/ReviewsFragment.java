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

import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.adapter.ReviewAdapter;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.databinding.FragmentDetailsBinding;
import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * ReviewsFragment displays reviews about a restaurant and provides the opportunity to leave a comment
 * <p>
 * This class uses {@link FragmentDetailsBinding} for data binding to its layout and
 * {@link ReviewViewModel} to interact with data sources and manage UI-related data.
 */

@AndroidEntryPoint
public class ReviewsFragment extends Fragment {

    private FragmentReviewsBinding binding;


    private ReviewViewModel reviewViewModel;

    public static ReviewsFragment newInstance() {
        ReviewsFragment fragment = new ReviewsFragment();
        return fragment;
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * The fragment should not add the view itself but return it.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return Returns the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Updates the UI components with the provided reviews data.
     *
     * @param reviewList Review list which containing objects Review to be displayed.
     */

    private void updateUIWithReviews(List<Review> reviewList) {

        if(reviewList==null)
        {
            return;
        }
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(new ReviewAdapter(reviewList));
        binding.restaurantName.setText(reviewViewModel.getRestaurantName());
    }

    /**
     * This method is called immediately after `onCreateView()`.
     * Use this method to perform final initialization once the fragment views have been inflated.
     *
     * @param view The View returned by `onCreateView()`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        reviewViewModel.getArrayReviews().observe(requireActivity(), this::updateUIWithReviews);
        ImageView imageView = binding.avatarNew;
        Picasso.get().load(getString(R.string.Girl)).into(imageView);

        binding.chipAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(binding.name.getText()!="" && !binding.commentEditText.getText().toString().trim().isEmpty() && binding.stars.getRating()!=0 ) {

                    reviewViewModel.addComment(binding.name.getText() + "", getString(R.string.Girl), binding.commentEditText.getText() + "", (int) binding.stars.getRating());
                    updateUIWithReviews(reviewViewModel.getArrayReviews().getValue());
                }

                else {
                    Snackbar.make(
                            getView(),
                            "Please complete your review",
                            Snackbar.LENGTH_LONG
                    ).setAnchorView(R.id.recyclerView).show();
                }
            }
        });
        binding.arrayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    /**
     * Initializes the ViewModel for this activity.
     */
    private void setupViewModel() {
        reviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * called to go back on the previous fragment
     */
    private void back(){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment detailsFragment = DetailsFragment.newInstance();
        fragmentTransaction.replace(R.id.container, detailsFragment);
        fragmentTransaction.commit();
    }
}
