package com.developer.kulloveth.userrepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText user;
    Button search;
    GithubClient client;
    String getUser;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search);
        progressBar = findViewById(R.id.progress);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.checkInternet(MainActivity.this, progressBar);
                Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https:api.github.com/")
                        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()));
                Retrofit retrofit = builder.build();

                client = retrofit.create(GithubClient.class);
                if (recyclerView == null) {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView = findViewById(R.id.recyclerview);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                }
                getRepos();
            }
        });


    }


    public void getRepos() {
        user = findViewById(R.id.user_test);
        getUser = user.getText().toString();
        Call<List<GithubRepo>> call = client.reposForUser(getUser);
        call.enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                List<GithubRepo> githubRepoList = response.body();
                RepoAdapter repoAdapter = new RepoAdapter();

                if (githubRepoList != null) {
                    progressBar.setVisibility(View.GONE);
                    repoAdapter.setRepos(githubRepoList);
                    recyclerView.setAdapter(repoAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "list is empty", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }


}
