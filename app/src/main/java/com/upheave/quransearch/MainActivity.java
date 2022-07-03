package com.upheave.quransearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.upheave.quransearch.model.VerseModel;
import com.upheave.quransearch.model.VerseSearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    private static final String QuranApiUrl = "https://api.alquran.cloud/v1/";
    private AlQuranAPI QuranApiAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(VerseSearchResponse.class, new VerseSearchResponse(null))
                .registerTypeAdapter(VerseModel.class, new VerseModel())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QuranApiUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.QuranApiAccess = retrofit.create(AlQuranAPI.class);
    }


    public void searchVerse(View v) {
        EditText verseTextInput = findViewById(R.id.searchText);
        String searchText = verseTextInput.getText().toString();
        searchVerse(searchText);
    }

    private void processResponse(String query, VerseSearchResponse response) {
        if (response.getResponseList().isEmpty()) {
            Toast.makeText(
                    this,
                    "Sorry! Failed to find results.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("VersesList", response.getResponseList());
        bundle.putString("query", query);

        Intent intent =  new Intent(MainActivity.this, ItemDetailHostActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void searchVerse(String searchQuery) {
        Call<VerseSearchResponse> call = QuranApiAccess.searchVerse(searchQuery);

        call.enqueue(new Callback<VerseSearchResponse>() {
            @Override
            public void onResponse(Call<VerseSearchResponse> call, Response<VerseSearchResponse> response) {
                processResponse(searchQuery, response.body());
            }

            @Override
            public void onFailure(Call<VerseSearchResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private static interface AlQuranAPI {
        @GET("search/{searchQuery}")
        Call<VerseSearchResponse> searchVerse(@Path("searchQuery") String query);
    }
}