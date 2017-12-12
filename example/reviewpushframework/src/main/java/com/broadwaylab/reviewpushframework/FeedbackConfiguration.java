package com.broadwaylab.reviewpushframework;

/**
 * Created by master on 09/12/17.
 */

public class FeedbackConfiguration {

    private boolean confettiEnabled=true;
    private FeedbackType type=FeedbackType.GENERAL;
    private String title;
    private String description;
    private String titleAfterReview;
    private String titlePositiveFeedback;
    private String titleNegativeFeedback;
    private String buttonNoText;
    private String buttonYesText;
    private String key;
    private String secret;
    private String locationId;
    private int backgroundDrawable;
    private int positiveColor;
    private int negativeColor;
    private String positiveDescription;


    public FeedbackConfiguration(String key, String secret, String locationID) {
        this.key = key;
        this.secret = secret;
        this.locationId = locationID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleAfterReview() {
        return titleAfterReview;
    }

    public void setTitleAfterReview(String titleAfterReview) {
        this.titleAfterReview = titleAfterReview;
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
        return key;
    }

    public String getSecret() {
        return secret;
    }

    public String getLocationId() {
        return locationId;
    }

    public int getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(int backgroundDrawable) {
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
}
