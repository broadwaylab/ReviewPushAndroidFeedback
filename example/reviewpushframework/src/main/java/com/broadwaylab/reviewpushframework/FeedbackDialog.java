package com.broadwaylab.reviewpushframework;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.broadwaylab.reviewpushframework.API.APIEndPoints;
import com.broadwaylab.reviewpushframework.API.FeedbackConfiguration;
import com.broadwaylab.reviewpushframework.API.ReviewSite;
import com.broadwaylab.reviewpushframework.utils.ConfigurationException;
import com.github.jinatonic.confetti.CommonConfetti;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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
    private ListView reviewSitesLitstView;
    private AdapterView.OnItemClickListener onItemSelectedListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ReviewSite site = sites.get(i);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(site.getUrl()));
            startActivity(intent);
            dismiss();
        }
    };
    private ArrayList<ReviewSite> sites;

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
        if (configuration.getBackgroundDrawable() != null)
            rootView.findViewById(R.id.review_push_background).setBackground(configuration.getBackgroundDrawable());
        if (configuration.getPositiveColor() != 0)
            reviewPushButtonYes.setBackgroundColor(configuration.getPositiveColor());
        if (configuration.getNegativeColor() != 0)
            reviewPushButtonNo.setBackgroundColor(configuration.getNegativeColor());
    }

    private void configureCopyOnViews() {
        if (!TextUtils.isEmpty(configuration.getTitle()))
            reviewPushTitle.setText(configuration.getTitle());
        if (!TextUtils.isEmpty(configuration.getSitesDescription()))
            reviewPushDescription.setText(configuration.getSitesDescription());
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
        reviewSitesLitstView = rootView.findViewById(R.id.review_push_list_view);
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

    public void setConfiguration(FeedbackConfiguration configuration) {
        this.configuration = configuration;
    }

    private boolean disableButton;
    private View.OnClickListener buttonsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == reviewPushButtonNo.getId()) {
                dismiss();
            } else if (v.getId() == reviewPushButtonYes.getId()) {
                if (!disableButton)
                    if (configuration.getType() == FeedbackType.APP_FEEDBACK) {
                        if (state == FeedbackState.OPEN) {
                            if (rating <= 3) {
                                changeViewToFeedback();
                            } else {
                                executePost();
                                sendPlayStoreIntent();
                                disableButton = true;
                            }
                            state = FeedbackState.FEEDBACK;
                        } else {
                            executePost();
                            sendPlayStoreIntent();
                            disableButton = true;
                        }
                    } else {
                        if (state == FeedbackState.OPEN) {
                            if (rating <= 3) {
                                changeViewToFeedback();
                            } else {
                                executePost();
                                disableButton = true;
                            }
                            state = FeedbackState.FEEDBACK;
                        } else {
                            if (rating <= 3) {
                                executePost();
                                disableButton = true;
                            }
                        }
                    }
            }
        }
    };
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private void executePost() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("location_id", configuration.getLocationId());
            jsonObject.put("api_key", configuration.getKey());
            jsonObject.put("api_secret", configuration.getSecret());
            jsonObject.put("reviewer", configuration.getReviewer());
            jsonObject.put("email", configuration.getEmail());
            jsonObject.put("review", reviewPushFeedback.getText().toString());
            jsonObject.put("rating", rating);
            Log.d("FeedbackParams", jsonObject.toString());
            postToURL(APIEndPoints.FEEDBACK_ENDPOINT, jsonObject.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    void postToURL(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(request);
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
            state = FeedbackState.OPEN;
            rating = (int) value;
            rootView.findViewById(R.id.review_push_buttons).setVisibility(View.VISIBLE);
            if (configuration.isConfettiEnabled())
                CommonConfetti.rainingConfetti((ViewGroup) rootView.findViewById(R.id.review_push_confetti_container), new int[]{Color.GRAY, Color.GREEN, Color.BLUE, Color.RED})
                        .infinite();
            if (configuration.getType().equals(FeedbackType.APP_FEEDBACK)) {
                if (!TextUtils.isEmpty(configuration.getTitleAfterReviewPositive()))
                    reviewPushTitle.setText(configuration.getTitleAfterReviewPositive());
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
        if (!TextUtils.isEmpty(configuration.getTitleAfterReviewPositive()))
            reviewPushTitle.setText(configuration.getTitleAfterReviewPositive());
        else
            reviewPushTitle.setText(getResources().getString(R.string.share_on_review_site));
        if (!TextUtils.isEmpty(configuration.getSitesDescription()))
            reviewPushDescription.setText(configuration.getSitesDescription());
        else
            reviewPushDescription.setText(getResources().getString(R.string.your_feedback_is_really_important));
        reviewPushDescription.setVisibility(View.VISIBLE);
    }

    private void configureViewsToFeedback() {
        if (!TextUtils.isEmpty(configuration.getTitleAfterReviewNegative()))
            reviewPushTitle.setText(configuration.getTitleAfterReviewNegative());
        else
            reviewPushTitle.setText(getResources().getString(R.string.rate_message));
        if (!TextUtils.isEmpty(configuration.getSitesDescription()))
            reviewPushDescription.setText(configuration.getSitesDescription());
        else
            reviewPushDescription.setText(getResources().getString(R.string.your_feedback_is_really_important));
        reviewPushDescription.setVisibility(View.VISIBLE);
    }


    public class OkHttpHandler extends AsyncTask<Request, String, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(Request... params) {

            Request request = params[0];

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("review_push_framework", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("error")) {
                    if (!configuration.silenceErrors())
                        Toast.makeText(getActivity(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                } else {
                    if (rating <= 3) {
                        Toast.makeText(getActivity(), "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    } else {
                        getActivity().runOnUiThread(new ConfigureListView(s));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConfigureListView implements Runnable {
        String json;

        ConfigureListView(String s) {
            json = s;
        }

        @Override
        public void run() {
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("review_site_links")) {
                    if (!TextUtils.isEmpty(configuration.getTitlePositiveFeedback()))
                        reviewPushTitle.setText(configuration.getTitlePositiveFeedback());
                    else
                        reviewPushTitle.setText(getString(R.string.plase_choose_your_preferred_website));
                    rootView.findViewById(R.id.review_push_rating_bar_parent).setVisibility(View.GONE);
                    rootView.findViewById(R.id.review_push_list_view_parent).setVisibility(View.VISIBLE);
                    reviewPushButtonYes.setVisibility(View.GONE);
                    sites = getReviewSites(jsonObject);
                    String[] sitesUrls = new String[sites.size()];
                    int i = 0;
                    for (ReviewSite reviewSite : sites) {
                        sitesUrls[i] = reviewSite.getName();
                        i++;
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, sitesUrls);
                    reviewSitesLitstView.setAdapter(adapter);
                    reviewSitesLitstView.setOnItemClickListener(onItemSelectedListener);
                } else {
                    Toast.makeText(getActivity(), "This site does not includes review sites, thank you for your feedback!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private ArrayList<ReviewSite> getReviewSites(JSONObject jsonObject) throws JSONException {
            ArrayList<ReviewSite> result = new ArrayList<>();
            JSONObject sites = jsonObject.getJSONObject("review_site_links");
            JSONArray names = sites.names();
            Log.d("ReviewPush", names.toString());
            for (int x = 0; x < names.length(); x++) {
                ReviewSite site = new ReviewSite();
                site.setName(names.getString(x));
                site.setUrl(sites.getJSONObject(names.getString(x)).getString("url"));
                result.add(site);
            }
            return result;
        }
    }
}
