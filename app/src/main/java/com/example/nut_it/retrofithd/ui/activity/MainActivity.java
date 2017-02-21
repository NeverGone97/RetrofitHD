package com.example.nut_it.retrofithd.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nut_it.retrofithd.R;
import com.example.nut_it.retrofithd.api.model.GitHubRepo;
import com.example.nut_it.retrofithd.api.service.GitHubClient;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.pagination_list);
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("http://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
       GitHubClient client= retrofit.create(GitHubClient.class);
        retrofit2.Call<List<GitHubRepo>> call=client.reposForUser("fs-opensource");
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(retrofit2.Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> repos=response.body();
                ArrayAdapter<GitHubRepo> adapter=new ArrayAdapter<GitHubRepo>(MainActivity.this,android.R.layout.simple_list_item_1,repos);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(retrofit2.Call<List<GitHubRepo>> call, Throwable t) {

            }
        });
    }
}
