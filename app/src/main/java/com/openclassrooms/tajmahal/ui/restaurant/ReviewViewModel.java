package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
/**
 * ReviewViewModel is responsible for preparing and managing the data for the {@link ReviewsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch review and restaurant details and provides
 * utility methods related to the restaurant UI.
 *
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class ReviewViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;
    MutableLiveData<List<Review>> pileReview;
    //private final MutableLiveData<List<Review>> reviews;
    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide review data.
     */
    @Inject
    public ReviewViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.pileReview = restaurantRepository.getReviews();
    }
    /**
     * Fetches the reviews of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public MutableLiveData<List<Review>> getArrayReviews() {
        return this.pileReview;
    }

    public String getRestaurantName(){
        return restaurantRepository.getRestaurant().getValue().getName();
    }


    /**
     * Create a Review from parameters and add it to pileReview
     * @param name,picture,comment,rate
     *
     */
    public void addComment(String name, String picture, String comment, int rate) {
        Review r = new Review( name, picture, comment, rate);
        List<Review> reviewList = new ArrayList<>();
        reviewList.addAll(getArrayReviews().getValue());
        reviewList.add(0,r);
        pileReview.setValue(reviewList);
    }




}
