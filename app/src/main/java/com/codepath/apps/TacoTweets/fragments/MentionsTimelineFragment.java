package com.codepath.apps.TacoTweets.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.TacoTweets.EndlessScrollListener;
import com.codepath.apps.TacoTweets.TwitterApplication;
import com.codepath.apps.TacoTweets.TwitterClient;
import com.codepath.apps.TacoTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Ariel on 3/10/15.
 */
public class MentionsTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the client
        client = TwitterApplication.getRestClient(); // singleton client
        populateTimeline();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);
        getLvTweets().setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
        // Setup refresh listener which triggers new data loading
        getSwipeContainer().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync();
            }
        });
        // Configure the refreshing colors
        getSwipeContainer().setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void fetchTimelineAsync() {
        if (isNetworkAvailable()) {
            client.getMentionsTimeline(new JsonHttpResponseHandler() {
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    // Remember to CLEAR OUT old items before appending in the new ones
                    clearOut();
                    // ...the data has come back, add new items to your adapter...
                    addAll(Tweet.fromJSONArray(json));
                    // Now we call setRefreshing(false) to signal refresh has finished
                    getSwipeContainer().setRefreshing(false);
                }

                public void onFailure(Throwable e) {
                    Log.d("DEBUG", "Fetch timeline error: " + e.toString());
                }
            });
        } else {
            getSwipeContainer().setRefreshing(false);
            Toast.makeText(getActivity(), "Can't refresh, no internet connection", Toast.LENGTH_LONG).show();
        }
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        if (isNetworkAvailable()) {
            // This method probably sends out a network request and appends new data items to your adapter.
            // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
            // Deserialize API response and then construct new objects to append to the adapter
            client.getMentionsTimeline(Tweet.getMaxId(), new JsonHttpResponseHandler() {
                //SUCCESS

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    //DESERIALIZE JSON
                    //CREATE MODELS
                    //LOAD THE MODEL DATA INTO A LIST VIEW
                    addAll(Tweet.fromJSONArray(json));
                }


                //FAILURE

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG PopulateTimeline", errorResponse.toString());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Can't load stories, no internet connection", Toast.LENGTH_LONG).show();
        }
    }

    //Send an API request to get the timeline JSON
    //Fill the listview by creating the tweet objects from the JSON
    private void populateTimeline() {
        if (isNetworkAvailable()) {
            client.getMentionsTimeline(new JsonHttpResponseHandler() {

                //SUCCESS
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    //DESERIALIZE JSON
                    //CREATE MODELS
                    //LOAD THE MODEL DATA INTO A LIST VIEW
                    addAll(Tweet.fromJSONArray(json));
                    //Log.d("DEBUG", aTweets.toString());
                }

                //FAILURE
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG PopulateTimeline", errorResponse.toString());
                }
            });
        } else {
            //Populate with SQLite database
//            Toast.makeText(this, "Loading from SQLite database", Toast.LENGTH_LONG).show();
            addAll(Tweet.fromSQLite());
        }
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
