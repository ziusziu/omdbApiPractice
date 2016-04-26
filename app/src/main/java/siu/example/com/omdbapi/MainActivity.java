package siu.example.com.omdbapi;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import siu.example.com.omdbapi.omdb.Omdb;
import siu.example.com.omdbapi.omdb.omdbService;
import siu.example.com.omdbapi.recyclerview.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    //http://www.omdbapi.com/?t=iron=&y=2016&plot=short&r=json
    private static final String TAG = "MAIN_ACTIVITY";
    private static final String API_URL = "http://www.omdbapi.com/";
    private static final String API_TITLE = "iron";
    private static final String API_YEAR = "2016";
    private static final String API_PLOT = "short";
    private static final String API_FORMAT = "json";
    private static Omdb omdb;
    private static Retrofit retrofit;
    private static OkHttpClient client;
    private static TextView mTextView;
    private static RelativeLayout mRelativeLayout;
    private static Toolbar mToolBar;
    private static List<Omdb> mOmdb;
    private static RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "onCreate: ----->>>" + BuildConfig.THE_MOVIE_API_TOKEN);

        initializeViews();
        basicHttpLogging();
        omdbApiCall();
        initializeToolBar();
        RecyclerViewSetup();


    }

    private void RecyclerViewSetup(){
        mOmdb = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mOmdb);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_info_details:
                Snackbar snackbarDownload = Snackbar
                        .make(mRelativeLayout, "Info Details Clicked", Snackbar.LENGTH_SHORT);
                snackbarDownload.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeViews(){
        mTextView = (TextView)findViewById(R.id.textView);
        mRelativeLayout = (RelativeLayout)findViewById(R.id.main_relativeLayout);
        mToolBar = (Toolbar)findViewById(R.id.main_toolBar);
        mRecyclerView = (RecyclerView)findViewById(R.id.main_recyclerView);
    }

    private void initializeToolBar(){
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    private void basicHttpLogging(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    private void omdbApiCall(){
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
                    mOmdb.add(omdb);
                    mTextView.setText(omdb.getDirector());
                }
            }

            @Override
            public void onFailure(Call<Omdb> call, Throwable t) {

            }
        });
    }


}
