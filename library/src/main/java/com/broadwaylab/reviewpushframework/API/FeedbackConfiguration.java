package com.broadwaylab.reviewpushframework.API;

import android.graphics.drawable.Drawable;

import com.broadwaylab.reviewpushframework.FeedbackType;

/**
 * Created by master on 09/12/17.
 */

public class FeedbackConfiguration {

    private final FeedbackAuth auth;
    private boolean confettiEnabled=true;
    private FeedbackType type=FeedbackType.GENERAL;
    private String title;
    private String sitesDescription;
    private String titleAfterReviewPositive;
    private String titleAfterReviewNegative;
    private String titlePositiveFeedback;
    private String titleNegativeFeedback;
    private String buttonNoText;
    private String buttonYesText;
    private boolean silenceErrors;
    private Drawable backgroundDrawable;
    private int positiveColor;
    private int negativeColor;
    private String positiveDescription;


    public boolean silenceErrors() {
        return silenceErrors;
    }

    public void setSilenceErrors(boolean silenceErrors) {
        this.silenceErrors = silenceErrors;
    }

    public FeedbackConfiguration(FeedbackAuth feedbackAuth) {
        this.auth = feedbackAuth;
    }

    public void setConfettiEnabled(boolean confettiEnabled) {
        this.confettiEnabled = confettiEnabled;
    }

    public boolean isConfettiEnabled() {
        return confettiEnabled;
    }

    public void setType(FeedbackType type) {
        this.type = type;
    }

    public FeedbackType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSitesDescription() {
        return sitesDescription;
    }

    public void setSitesDescription(String sitesDescription) {
        this.sitesDescription = sitesDescription;
    }


    public String getTitlePositiveFeedback() {
        return titlePositiveFeedback;
    }

    public void setTitlePositiveFeedback(String titlePositiveFeedback) {
        this.titlePositiveFeedback = titlePositiveFeedback;
    }

    public String getTitleNegativeFeedback() {
        return titleNegativeFeedback;
    }

    public void setTitleNegativeFeedback(String titleNegativeFeedback) {
        this.titleNegativeFeedback = titleNegativeFeedback;
    }

    public String getButtonNoText() {
        return buttonNoText;
    }

    public void setButtonNoText(String buttonNoText) {
        this.buttonNoText = buttonNoText;
    }


    public String getButtonYesText() {
        return buttonYesText;
    }

    public void setButtonYesText(String buttonYesText) {
        this.buttonYesText = buttonYesText;
    }

    public String getKey() {
        return auth.getKey();
    }

    public String getSecret() {
        return auth.getSecret();
    }

    public String getLocationId() {
        return auth.getLocationID();
    }

    public Drawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    public int getPositiveColor() {
        return positiveColor;
    }

    public void setPositiveColor(int positiveColor) {
        this.positiveColor = positiveColor;
    }

    public int getNegativeColor() {
        return negativeColor;
    }

    public void setNegativeColor(int negativeColor) {
        this.negativeColor = negativeColor;
    }

    public String getPositiveDescription() {
        return positiveDescription;
    }

    public void setPositiveDescription(String positiveDescription) {
        this.positiveDescription = positiveDescription;
    }

    public String getReviewer() {
        return auth.getReviewer();
    }

    public String getEmail() {
        return auth.getEmail();
    }

    public String getTitleAfterReviewPositive() {
        return titleAfterReviewPositive;
    }

    public void setTitleAfterReviewPositive(String titleAfterReviewPositive) {
        this.titleAfterReviewPositive = titleAfterReviewPositive;
    }

    public String getTitleAfterReviewNegative() {
        return titleAfterReviewNegative;
    }

    public void setTitleAfterReviewNegative(String titleAfterReviewNegative) {
        this.titleAfterReviewNegative = titleAfterReviewNegative;
    }
}
