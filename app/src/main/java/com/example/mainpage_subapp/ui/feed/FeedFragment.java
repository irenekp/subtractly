package com.example.mainpage_subapp.ui.feed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.ui.home.DataModel;
import com.example.mainpage_subapp.ui.home.HomeFragment;
import com.example.mainpage_subapp.ui.home.MyData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeedFragment extends Fragment {
    private LinearLayout feedview;
    private TransparentProgressDialog p;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feed, container, false);
        feedview=root.findViewById(R.id.feed_view);
        return root;
    }
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            AsyncGetFeed data=new AsyncGetFeed();
            data.execute();
        }
    }
    public class AsyncGetFeed extends AsyncTask<Void,View,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new TransparentProgressDialog(getActivity());
            p.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //p.setMessage("Please wait...It is downloading");
            //p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected Void doInBackground(Void...v) {
            String jsonData="{error:404}";
            JSONObject Jobject=null;
            JSONArray Jarray=null;
            String APIKey="a4e9b4e9caeb4b0c83c3322515a255eb";
            Resources res = getResources();
            String[] topics = res.getStringArray(R.array.SubscriptionName);
            //Getting the current date value
            LocalDate currentdate = null;
            LocalDate prevdate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentdate = LocalDate.now();
                prevdate = currentdate.minus(20, ChronoUnit.DAYS);
            }
            String today=currentdate.toString();
            String past=prevdate.toString();
            for(int i=0;i<topics.length-1;i++)
            {
                System.out.println("for "+topics[i]+" get articles");
                String url="https://newsapi.org/v2/everything?qInTitle="+topics[i]+"&from="+today+"&to="+past+"&language=en&sortBy=popularity&pageSize=4&apiKey="+APIKey;
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response responses = null;
                    try {
                        responses = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    jsonData = responses.body().string();
                    Jobject = new JSONObject(jsonData);
                    Jarray = Jobject.getJSONArray("articles");
                    for (int j = 0; j < Jarray.length(); j++) {
                        final JSONObject object = Jarray.getJSONObject(j);
                        JSONObject src = object.getJSONObject("source");
                        String Source=src.getString("name");
                        String Title=object.getString("title");
                        String Key=topics[i];
                        String Date=object.getString("publishedAt").substring(0,10);
                        String Content=object.getString("description");
                        URL imgurl = new URL(object.getString("urlToImage"));
                        Bitmap bmp = BitmapFactory.decodeStream(imgurl.openConnection().getInputStream());
                        LayoutInflater li = LayoutInflater.from(getContext());
                        View cv = li.inflate(R.layout.feed_entry, null);
                        TextView tv=cv.findViewById(R.id.articleTitle);
                        Title = Title.substring(0, 40)+"...";
                        tv.setText(Title);
                        tv=cv.findViewById(R.id.topic);
                        tv.setText(Key);
                        tv=cv.findViewById(R.id.articleDate);
                        tv.setText(Date);
                        tv=cv.findViewById(R.id.newsContent);
                        tv.setText(Content);
                        tv=cv.findViewById(R.id.source);
                        tv.setText(Source);
                        ImageView iv=cv.findViewById(R.id.articleImage);
                        iv.setImageBitmap(bmp);
                        cv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(object.getString("url")));
                                    startActivity(intent);
                                }catch (Exception e){
                                    Toast.makeText(getContext(),"Unable to open link.",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        publishProgress(cv);
                    }
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        public void openWebPage(String url) {
            try {
                Uri webpage = Uri.parse(url);
                Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(myIntent);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Unable to open link.",  Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        @Override
        protected void onProgressUpdate(View... v) {
            p.dismiss();
            feedview.addView(v[0]);
        }
    }
}