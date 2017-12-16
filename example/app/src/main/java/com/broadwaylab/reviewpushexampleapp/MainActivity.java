package com.broadwaylab.reviewpushexampleapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.broadwaylab.reviewpushframework.API.FeedbackAuth;
import com.broadwaylab.reviewpushframework.API.FeedbackConfiguration;
import com.broadwaylab.reviewpushframework.FeedbackDialog;
import com.broadwaylab.reviewpushframework.FeedbackType;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String key = getResources().getString(R.string.review_push_key);
        String locationId = getResources().getString(R.string.review_push_secret);
        String secret = getResources().getString(R.string.review_push_location_id);
        FeedbackAuth auth = new FeedbackAuth(key, secret, locationId, "Name", "email");
        if (id == R.id.nav_feedback_design) {
            FeedbackDialog mDialog = new FeedbackDialog();
            FeedbackConfiguration configuration = new FeedbackConfiguration(auth);
            configuration.setConfettiEnabled(true);
            configuration.setPositiveColor(Color.RED);
            configuration.setNegativeColor(Color.BLACK);
            configuration.setConfettiEnabled(false);
            configuration.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
            configuration.setType(FeedbackType.GENERAL);
            mDialog.setConfiguration(configuration);
            mDialog.show(getFragmentManager(), "FeedbackDialog");
        } else if (id == R.id.nav_store_feedback) {
            FeedbackDialog mDialog = new FeedbackDialog();
            FeedbackConfiguration configuration = new FeedbackConfiguration(auth);
            configuration.setConfettiEnabled(true);
            configuration.setType(FeedbackType.APP_FEEDBACK);
            mDialog.setConfiguration(configuration);
            mDialog.show(getFragmentManager(), "FeedbackDialog");
        } else if (id == R.id.nav_feedback_copy) {
            FeedbackDialog mDialog = new FeedbackDialog();
            FeedbackConfiguration configuration = new FeedbackConfiguration(auth);
            configuration.setType(FeedbackType.GENERAL);
            configuration.setButtonNoText("Nope");
            configuration.setButtonYesText("Yes!");
            configuration.setTitle("Could you rate your experience here?");
            configuration.setTitleAfterReviewNegative("We're bummed you had a bad time. Can you tell us why?");
            configuration.setSitesDescription("Your opinion is very important");
            configuration.setTitleNegativeFeedback("Tell us, how can we do it better?");
            configuration.setTitlePositiveFeedback("We are happy to have you here!, Rate us on other sites");
            mDialog.setConfiguration(configuration);
            mDialog.show(getFragmentManager(), "FeedbackDialog");
        } else if (id == R.id.nav_feedback_locations) {
            FeedbackDialog mDialog = new FeedbackDialog();
            FeedbackConfiguration configuration = new FeedbackConfiguration(auth);
            configuration.setType(FeedbackType.GENERAL);
            mDialog.setConfiguration(configuration);
            mDialog.show(getFragmentManager(), "FeedbackDialog");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
