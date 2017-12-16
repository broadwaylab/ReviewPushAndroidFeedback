package com.broadwaylab.reviewpushframework.API;

/**
 * Created by rodol on 15/12/2017.
 */

public class FeedbackAuth {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String secret;
    private String locationID;
    private String reviewer;
    private String email;

    public FeedbackAuth(String key, String secret, String locationID, String reviewer, String email) {
        this.key = key;
        this.secret = secret;
        this.locationID = locationID;
        this.reviewer = reviewer;
        this.email = email;
    }
}
