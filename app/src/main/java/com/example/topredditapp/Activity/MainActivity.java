package com.example.topredditapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.Toast;

import com.example.topredditapp.API.API_reddit;
import com.example.topredditapp.API.RedditRetrofit;
import com.example.topredditapp.Adapter.RedditAdapter;
import com.example.topredditapp.InternetConnection;
import com.example.topredditapp.Model.Dat;
import com.example.topredditapp.Model.Example;
import com.example.topredditapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Dat> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_reddit);

        if(InternetConnection.checkConnection(this)){

            API_reddit api = RedditRetrofit.getRetrofitIntance().create(API_reddit.class);

            Call<Example> call = api.getRedditJSON();

            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            list = response.body().getData().getData();

                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(new RedditAdapter(getApplicationContext(), list));
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error 404" + list.size(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "onFailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this,"No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }
}
