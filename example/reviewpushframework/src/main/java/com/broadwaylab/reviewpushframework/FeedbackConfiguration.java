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


    public FeedbackConfiguration() {
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
}
