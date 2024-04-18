package com.openclassrooms.tajmahal.ui.restaurant;

import static org.mockito.Mockito.when;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Review;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ReviewsFragmentTest extends TestCase {


    @Mock
    private RestaurantRepository mRestaurantRepository;

    @Test
    public void testAddComment() {
        // Arrange
        Review newReview = new Review("", "", "", 4);
        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(newReview);
        when(mRestaurantRepository.getReviews()).thenReturn(new MutableLiveData<>(expectedReviews));

        // Act
        mRestaurantRepository.addReview(newReview);

        // Assert
        assertEquals(expectedReviews, mRestaurantRepository.getReviews().getValue());
    }
    


}