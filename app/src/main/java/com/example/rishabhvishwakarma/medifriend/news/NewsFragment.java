package com.example.rishabhvishwakarma.medifriend.news;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rishabhvishwakarma.medifriend.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsFragment extends Fragment {

    String API_KEY = "bf747861189c4b3880cdd84b23dfa9ee";      // ### YOUE NEWS API HERE ###
    String NEWS_SOURCE = "medical-news-today";
    ListView listNews;
    ProgressBar loader;

    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    private Context context;
    private ActionBar actionBar;
    private Boolean isHomeAsUpShown;
    private String previousTitle, previousSubTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_news_fragment, container, false);

        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            isHomeAsUpShown = (actionBar.getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP) != 0;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            previousTitle = actionBar.getTitle().toString();
            previousSubTitle = actionBar.getSubtitle().toString();
            actionBar.setTitle(R.string.news_info);
            actionBar.setSubtitle(R.string.news_subtitle);
        }

        listNews = rootView.findViewById(R.id.listNews);
        loader =  rootView.findViewById(R.id.loader);
        listNews.setEmptyView(loader);

        context=getContext();

        if(Function.isNetworkAvailable(context))
        {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        }else{
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    return rootView;
    }

    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";
            xml = Function.excuteGet("https://newsapi.org/v2/everything?sources="+NEWS_SOURCE+"&apiKey="+API_KEY, urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

                if(xml.length()>10){ // Just checking if not empty

                    try {
                        JSONObject jsonResponse = new JSONObject(xml);
                        JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR).toString());
                            map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE).toString());
                            map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION).toString());
                            map.put(KEY_URL, jsonObject.optString(KEY_URL).toString());
                            map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE).toString());
                            dataList.add(map);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context, "Unexpected error", Toast.LENGTH_SHORT).show();
                    }

                    ListNewsAdapter adapter = new ListNewsAdapter(getActivity(), dataList);
                    listNews.setAdapter(adapter);

                    listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Intent i = new Intent(context, DetailsActivity.class);
                            i.putExtra("url", dataList.get(+position).get(KEY_URL));
                            startActivity(i);
                        }
                    });

                }else{
                    Toast.makeText(context, "No news found", Toast.LENGTH_SHORT).show();
                }
        }



    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_news, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isHomeAsUpShown){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        actionBar.setTitle(previousTitle);
        actionBar.setSubtitle(previousSubTitle);
        super.onDestroy();
    }
}
