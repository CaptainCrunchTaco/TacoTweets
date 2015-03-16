package com.codepath.apps.TacoTweets.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
//    int sourceHome = 1;

    @Override
    public void customLoadMoreDataFromApi(long maxId) {
        if(isNetworkAvailable()) {
            client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {

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
//            addAll(Tweet.fromSQLite());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the client
        client = TwitterApplication.getRestClient(); // singleton client
        populateTimeline();
    }

    public void fetchTimelineAsync() {
        if(isNetworkAvailable()) {
            client.getHomeTimeline(new JsonHttpResponseHandler() {
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
                    getSwipeContainer().setRefreshing(false);
                }
            });
        } else {
            getSwipeContainer().setRefreshing(false);
            Toast.makeText(getActivity(), "Can't refresh, no internet connection", Toast.LENGTH_LONG).show();
        }
    }

    //Send an API request to get the timeline JSON
    //Fill the listview by creating the tweet objects from the JSON
    private void populateTimeline() {
        if(isNetworkAvailable()) {
            client.getHomeTimeline(new JsonHttpResponseHandler() {

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
//            addAll(Tweet.fromSQLite());
        }
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
