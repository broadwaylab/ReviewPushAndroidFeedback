package com.broadwaylab.reviewpushframework;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.broadwaylab.reviewpushframework.utils.ConfigurationException;
import com.github.jinatonic.confetti.CommonConfetti;


public class FeedbackDialog extends DialogFragment {

    private View rootView;
    private FeedbackConfiguration configuration;
    private TextView reviewPushTitle;
    private TextView reviewPushDescription;
    private Button reviewPushButtonYes;
    private Button reviewPushButtonNo;
    private String packageApp;
    private int rating;
    private FeedbackState state;
    private EditText reviewPushFeedback;
    private RatingBar reviewPusRating;

    public FeedbackDialog() {
        state = FeedbackState.OPEN;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.feedback_dialog, container, false);
        packageApp = getActivity().getPackageName();
        configureDialogViews();
        return rootView;
    }

    private void configureDialogViews() {
        try {
            setViews();
            configureDesignElements();
            configureCopyOnViews();
            configureDialogWindow();
            setViewsListeners();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void configureDesignElements() throws ConfigurationException {
        if (configuration == null)
            throw new ConfigurationException("Configuration must be provided!");
        if (configuration.getBackgroundDrawable() != 0)
            rootView.findViewById(R.id.review_push_background).setBackground(getResources().getDrawable(configuration.getBackgroundDrawable()));
        if (configuration.getPositiveColor() != 0)
            reviewPushButtonYes.setBackgroundColor(configuration.getPositiveColor());
        if (configuration.getNegativeColor() != 0)
            reviewPushButtonNo.setBackgroundColor(configuration.getNegativeColor());
    }

    private void configureCopyOnViews() {
        if (!TextUtils.isEmpty(configuration.getTitle()))
            reviewPushTitle.setText(configuration.getTitle());
        if (!TextUtils.isEmpty(configuration.getDescription()))
            reviewPushTitle.setText(configuration.getDescription());
        if (!TextUtils.isEmpty(configuration.getButtonNoText()))
            reviewPushButtonNo.setText(configuration.getButtonNoText());
        if (!TextUtils.isEmpty(configuration.getButtonYesText()))
            reviewPushButtonYes.setText(configuration.getButtonYesText());
    }

    private void setViews() {
        reviewPushTitle = rootView.findViewById(R.id.review_push_title);
        reviewPushDescription = rootView.findViewById(R.id.review_push_sub_title);
        reviewPushButtonYes = rootView.findViewById(R.id.review_push_button_yes);
        reviewPushButtonNo = rootView.findViewById(R.id.review_push_button_no);
        reviewPushFeedback = rootView.findViewById(R.id.review_push_feedback);
        reviewPusRating = rootView.findViewById(R.id.review_push_rating_bar);
    }

    private void setViewsListeners() {
        reviewPusRating.setOnRatingBarChangeListener(ratingListener);
        reviewPushButtonNo.setOnClickListener(buttonsClickListener);
        reviewPushButtonYes.setOnClickListener(buttonsClickListener);
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
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
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

    private View.OnClickListener buttonsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == reviewPushButtonNo.getId()) {
                dismiss();
            } else if (v.getId() == reviewPushButtonYes.getId()) {
                if (configuration.getType() == FeedbackType.APP_FEEDBACK) {
                    if (state == FeedbackState.OPEN) {
                        if (rating <= 3) {
                            changeViewToFeedback();
                        } else {
                            sendPlayStoreIntent();
                        }
                        state = FeedbackState.FEEDBACK;
                    } else {
                        sendPlayStoreIntent();
                    }
                } else {
                    if (state == FeedbackState.OPEN) {
                        if (rating <= 3) {
                            changeViewToFeedback();
                        } else {
                            getReviewSites();
                        }
                        state = FeedbackState.FEEDBACK;
                    } else {
                        if (rating <= 3)
                            sendReview();
                    }
                }
            }
        }
    };

    private void getReviewSites() {

    }

    private void sendReview() {

    }

    private void sendPlayStoreIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageApp));
        startActivity(intent);
    }


    private void changeViewToFeedback() {
        reviewPusRating.setVisibility(View.GONE);
        reviewPushDescription.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(configuration.getTitleNegativeFeedback()))
            reviewPushTitle.setText(configuration.getTitleNegativeFeedback());
        else
            reviewPushTitle.setText(getResources().getString(R.string.review_negative_title));
        rootView.findViewById(R.id.review_push_feedback_parent).setVisibility(View.VISIBLE);
        reviewPushButtonYes.setText(getResources().getString(R.string.submit));
        reviewPushButtonNo.setText(getResources().getString(R.string.cancel));

    }

    private RatingBar.OnRatingBarChangeListener ratingListener = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float value, boolean b) {
            rating = (int) value;
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
                if (rating <= 3) {
                    configureViewsToFeedback();
                } else {
                    configureViewsToReviewSite();
                }
            }
        }
    };

    private void configureViewsToReviewSite() {
        if (!TextUtils.isEmpty(configuration.getTitleAfterReview()))
            reviewPushTitle.setText(configuration.getTitleAfterReview());
        else
            reviewPushTitle.setText(getResources().getString(R.string.thanks));
        if (!TextUtils.isEmpty(configuration.getDescription()))
            reviewPushDescription.setText(configuration.getDescription());
        else
            reviewPushDescription.setText(getResources().getString(R.string.share_on_review_site));
        reviewPushDescription.setVisibility(View.VISIBLE);
    }

    private void configureViewsToFeedback() {
        if (!TextUtils.isEmpty(configuration.getTitleAfterReview()))
            reviewPushTitle.setText(configuration.getTitleAfterReview());
        else
            reviewPushTitle.setText(getResources().getString(R.string.thanks));
        if (!TextUtils.isEmpty(configuration.getDescription()))
            reviewPushDescription.setText(configuration.getDescription());
        else
            reviewPushDescription.setText(getResources().getString(R.string.tell_us_about_your_experience));
        reviewPushDescription.setVisibility(View.VISIBLE);
    }
}
