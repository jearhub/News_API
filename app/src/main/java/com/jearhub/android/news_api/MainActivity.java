package com.jearhub.android.news_api;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView mTextNews;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        mTextNews = findViewById ( R.id.txtNews );
        Button buttonNews = findViewById ( R.id.btnNews );

        mQueue = Volley.newRequestQueue ( this );

        buttonNews.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                jsonNews();
            }
        } );
    }

    private void jsonNews() {
        String url = "https://newsapi.org/v2/top-headlines?sources=cnn&apiKey=";

        JsonObjectRequest request = new JsonObjectRequest ( Request.Method.GET, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray ( "articles" );

                            for (int i = 0; i < jsonArray.length (); i++) {
                                JSONObject article = jsonArray.getJSONObject ( i );

                                String title = article.getString ( "title" );
                                String publishedAt = article.getString ( "publishedAt" );
                                String url = article.getString ( "url" );

                                mTextNews.append ( title + "\n" + publishedAt + "\n" + url + "\n\n" );

                            }
                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }
                    }
                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace ();
            }
        });

        mQueue.add ( request );
    }
}
