package com.codepath.apps.TacoTweets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.TacoTweets.R;
import com.codepath.apps.TacoTweets.TwitterApplication;
import com.codepath.apps.TacoTweets.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {
    private TwitterClient client;
    private String composedTweet;
    private EditText etTweet;
    private MenuItem tvCharacterCount;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getSupportActionBar().setElevation(0);
        client = TwitterApplication.getRestClient();
        etTweet = (EditText) findViewById(R.id.etTweet);
    }

    private TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            tvCharacterCount.setTitle(Integer.toString(140 - etTweet.getText().toString().length()));
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        this.mMenu = menu;
        tvCharacterCount = mMenu.findItem(R.id.tvCharacterCount);
        etTweet.addTextChangedListener(textWatcher);
        return true;
    }

    public void submitTweet(View view) {
        if(Integer.parseInt(tvCharacterCount.getTitle().toString()) < 0) {
            Toast.makeText(this, "This message is too long!", Toast.LENGTH_LONG).show();
        } else {
            composedTweet = etTweet.getText().toString();
            client.postTweet(composedTweet, new JsonHttpResponseHandler() {
                //SUCCESS

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

                    Log.d("Debug on Success", json.toString());
                }


                //FAILURE

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG PopulateTimeline", errorResponse.toString());
                }
            });
            Intent intent = new Intent(this, TimelineActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }
}
