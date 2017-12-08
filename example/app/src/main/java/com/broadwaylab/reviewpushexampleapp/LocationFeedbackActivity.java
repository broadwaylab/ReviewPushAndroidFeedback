package com.broadwaylab.reviewpushexampleapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;

public class LocationFeedbackActivity extends AppCompatActivity {

    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_feedback);
        container=findViewById(R.id.review_push_confetti_container);
        RatingBar mRating=findViewById(R.id.ratingBarFeedback);
        mRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                CommonConfetti.rainingConfetti(container, new int[] { Color.GRAY,Color.GREEN,Color.BLUE,Color.RED })
                        .infinite();
            }
        });
    }
}
