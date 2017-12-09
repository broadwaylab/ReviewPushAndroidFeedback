package com.broadwaylab.reviewpushframework;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.jinatonic.confetti.CommonConfetti;


public class FeedbackDialog extends DialogFragment {

    private View rootView;
    private FeedbackConfiguration configuration;
    private TextView reviewPushTitle;
    private TextView reviewPushDescription;
    private Button reviewPushButtonYes;
    private Button reviewPushButtonNo;

    public FeedbackDialog() {
        configuration = new FeedbackConfiguration();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.feedback_dialog, container, false);
        configureDialogViews();
        return rootView;
    }

    private void configureDialogViews() {
        setViews();
        configureCopyOnViews();
        configureDialogWindow();
        setViewsListeners();
    }

    private void configureCopyOnViews() {
        if (!TextUtils.isEmpty(configuration.getTitle()))
            reviewPushTitle.setText(configuration.getTitle());
        if (!TextUtils.isEmpty(configuration.getDescription()))
            reviewPushTitle.setText(configuration.getDescription());
        if(!TextUtils.isEmpty(configuration.getButtonNoText()))
            reviewPushButtonNo.setText(configuration.getButtonNoText());
        if(!TextUtils.isEmpty(configuration.getButtonYesText()))
            reviewPushButtonYes.setText(configuration.getButtonYesText());
    }

    private void setViews() {
        reviewPushTitle = rootView.findViewById(R.id.review_push_title);
        reviewPushDescription = rootView.findViewById(R.id.review_push_sub_title);
        reviewPushButtonYes=rootView.findViewById(R.id.review_push_button_yes);
        reviewPushButtonNo=rootView.findViewById(R.id.review_push_button_no);
    }

    private void setViewsListeners() {
        RatingBar mRating = rootView.findViewById(R.id.review_push_rating_bar);
        mRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float value, boolean b) {
                rootView.findViewById(R.id.review_push_buttons).setVisibility(View.VISIBLE);
                if (configuration.isConfettiEnabled())
                    CommonConfetti.rainingConfetti((ViewGroup) rootView.findViewById(R.id.review_push_confetti_container), new int[]{Color.GRAY, Color.GREEN, Color.BLUE, Color.RED})
                            .infinite();
                if (configuration.getType().equals(FeedbackType.APP_FEEDBACK)) {
                    if (!TextUtils.isEmpty(configuration.getTitleAfterReview()))
                        reviewPushTitle.setText(configuration.getTitleAfterReview());
                    else
                        reviewPushTitle.setText(getResources().getString(R.string.after_review_title));
                    reviewPushDescription.setVisibility(View.VISIBLE);
                } else {

                }
            }
        });
    }

    private void configureDialogWindow() {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
        wmlp.gravity = Gravity.FILL_HORIZONTAL;
    }

    private void showNoGPSAlert(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.message_no_gps))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void setConfiguration(FeedbackConfiguration configuration) {
        this.configuration = configuration;
    }
}
