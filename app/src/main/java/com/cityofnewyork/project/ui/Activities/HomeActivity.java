package com.cityofnewyork.project.ui.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.cityofnewyork.project.R;
import com.cityofnewyork.project.data.network.interfaces.callbacks.GetSATDetails;
import com.cityofnewyork.project.data.network.services.HttpServices;
import com.cityofnewyork.project.data.network.services.Models.SATDetailsResponse;
import com.cityofnewyork.project.ui.Adapters.RecyclerViewAdapter;
import com.cityofnewyork.project.utils.Connectivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<String> rowsArrayList = new ArrayList<>();

    ProgressDialog progress;



    private static final String SCHOOLS = "EXTRA_MOVIES";

    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        progress =   new ProgressDialog(this);
        initScrollListener();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SCHOOLS)) {
                ArrayList<String> schools = savedInstanceState.getStringArrayList(SCHOOLS);
                rowsArrayList = schools;
                initAdapter();
            }
        } else {
            // Fetch Schools only if savedInstanceState == null
            populateData();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<String> schoolsList = new ArrayList<>();

        if (rowsArrayList != null && !rowsArrayList.isEmpty()) {
            outState.putStringArrayList(SCHOOLS, schoolsList);
        }
        outState.putStringArrayList(SCHOOLS, this.rowsArrayList);
    }

    private void populateData() {

        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        if (Connectivity.getInstance(this.getApplicationContext()).isOnline()) {
            HttpServices httpService = new HttpServices();
            httpService.getSATDetails("12", "0", new GetSATDetails() {
                @Override
                public void onSuccess(@NonNull List<SATDetailsResponse> satDetailsResponses) {
                    int i = 0;
                    while (i < satDetailsResponses.size()) {
                        rowsArrayList.add(satDetailsResponses.get(i).getSchool_name());
                        i++;
                    }

                    progress.dismiss();

                    initAdapter();
                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    progress.dismiss();
                }
            });
        }else{
            Toast.makeText(this.getApplicationContext(), "Internet is  unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    private void initAdapter() {
        recyclerViewAdapter = new RecyclerViewAdapter(rowsArrayList,getApplicationContext(),recyclerView);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        rowsArrayList.add(null);
        recyclerViewAdapter.notifyItemInserted(rowsArrayList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                HttpServices httpService = new HttpServices();
                httpService.getSATDetails("12", String.valueOf(rowsArrayList.size() - 1), new GetSATDetails() {
                    @Override
                    public void onSuccess(@NonNull List<SATDetailsResponse> satDetailsResponses) {

                        rowsArrayList.remove(rowsArrayList.size() - 1);
                        int scrollPosition = rowsArrayList.size();
                        recyclerViewAdapter.notifyItemRemoved(scrollPosition);

                        int i = 0;
                        while (i < satDetailsResponses.size()) {
                            rowsArrayList.add(satDetailsResponses.get(i).getSchool_name());
                            i++;
                        }
                        recyclerViewAdapter.notifyDataSetChanged();
                        isLoading = false;
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        rowsArrayList.remove(rowsArrayList.size() - 1);
                        int scrollPosition = rowsArrayList.size();
                        recyclerViewAdapter.notifyItemRemoved(scrollPosition);

                        recyclerViewAdapter.notifyDataSetChanged();
                        isLoading = false;
                    }
                });

            }
        }, 2000);


    }
}
