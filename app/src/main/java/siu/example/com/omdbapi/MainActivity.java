package siu.example.com.omdbapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //http://www.omdbapi.com/?t=iron=&y=2016&plot=short&r=json
    private static final String TAG = "MAIN_ACTIVITY";
    private static final String API_URL = "http://www.omdbapi.com/";
    private static final String API_TITLE = "iron";
    private static final String API_YEAR = "2016";
    private static final String API_PLOT = "short";
    private static final String API_FORMAT = "json";
    Omdb omdb;
    Retrofit retrofit;
    OkHttpClient client;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        basicHttpLogging();
        textView = (TextView)findViewById(R.id.textView);

        Log.d(TAG, "onCreate: ----->>>" + BuildConfig.THE_MOVIE_API_TOKEN);

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        omdbService service = retrofit.create(omdbService.class);
        Call<Omdb> call = service.queryMovies(API_URL, API_TITLE, API_YEAR, API_PLOT, API_FORMAT);
        call.enqueue(new Callback<Omdb>() {
            @Override
            public void onResponse(Call<Omdb> call, Response<Omdb> response) {
                if(response.isSuccessful()){
                    omdb = response.body();
                    textView.setText(omdb.getDirector());
                }
            }

            @Override
            public void onFailure(Call<Omdb> call, Throwable t) {

            }
        });

    }

    private void basicHttpLogging(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }


}
