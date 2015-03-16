package com.codepath.apps.TacoTweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.TacoTweets.EndlessScrollListener;
import com.codepath.apps.TacoTweets.R;
import com.codepath.apps.TacoTweets.TweetsArrayAdapter;
import com.codepath.apps.TacoTweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ariel on 3/9/15.
 */


public abstract class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private ArrayAdapter aTweets;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeContainer;

    //Inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        //Find the listview
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        //Connect adapter to data source
        lvTweets.setAdapter(aTweets);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //Setup endless scroll
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView

                customLoadMoreDataFromApi(findMax());
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
        return v;
    }

    private long findMax() {
//        long currentId;
//        long maxId=-1;
//        for(Tweet everyTweet:lvTweets.getChildAt(lvTweets.getAdapter().getCount())){
//            currentId = everyTweet.getUid();
//            if(maxId == -1) {
//                currentId = maxId;
//            } else if(currentId < maxId) {
//                maxId = currentId;
//            }
//        }
        Tweet blah = (Tweet) aTweets.getItem(aTweets.getCount()-1);
        return blah.getUid();
    }

    // Append more data into the adapter
    public abstract void customLoadMoreDataFromApi(long maxId);

    //Creation of lifecycle event
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create the arraylist (data source)
        tweets = new ArrayList<>();
        //Construct the adapter from data source
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void clearOut() {
        aTweets.clear();
    }

    public ListView getLvTweets() {
        return lvTweets;
    }

    public SwipeRefreshLayout getSwipeContainer() {
        return swipeContainer;
    }

    public abstract void fetchTimelineAsync();

}
