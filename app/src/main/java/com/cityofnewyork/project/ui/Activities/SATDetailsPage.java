package com.cityofnewyork.project.ui.Activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.cityofnewyork.project.R;
import com.cityofnewyork.project.data.network.interfaces.callbacks.GetSATDetails;
import com.cityofnewyork.project.data.network.services.HttpServices;
import com.cityofnewyork.project.data.network.services.Models.SATDetailsResponse;
import com.cityofnewyork.project.utils.BaseClass;
import com.cityofnewyork.project.utils.Connectivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SATDetailsPage extends AppCompatActivity {

    @BindView(R.id.tvSchoolName)
    TextView tvSchoolName;

    @BindView(R.id.tvMathAverage)
    TextView tvMathAverage;

    @BindView(R.id.tvWritingAvg)
    TextView tvWritingAvg;

    @BindView(R.id.tvTestTakers)
    TextView tvTestTakers;

    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satdetails_page);
        ButterKnife.bind(this);


        progress =   new ProgressDialog(this);

        this.getBundleValues();
    }

    private void getBundleValues() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(BaseClass.SCHOOL_NAME)) {
                String schoolName = extras.getString(BaseClass.SCHOOL_NAME);

                Log.e("SCHOOL NAME",extras.getString(BaseClass.SCHOOL_NAME));

                tvSchoolName.setText(schoolName);

                if (Connectivity.getInstance(this.getApplicationContext()).isOnline()) {

                    progress.setTitle("Loading");
                    progress.setMessage("Wait while loading...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();



                    HttpServices httpService = new HttpServices();
                    httpService.getSATDetailsBySchoolName(schoolName, new GetSATDetails() {
                        @Override
                        public void onSuccess(@NonNull List<SATDetailsResponse> satDetailsResponses) {

                            //with less time hardcoded
                            if(satDetailsResponses.size() > 0){
                                tvMathAverage.setText("Average math score : "+ satDetailsResponses.get(0).getSat_math_avg_score());
                                tvTestTakers.setText("Average sat test takers : "+satDetailsResponses.get(0).getNum_of_sat_test_takers());
                                tvWritingAvg.setText("Average writing score : "+ satDetailsResponses.get(0).getSat_writing_avg_score());
                            }

                            progress.dismiss();
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
        }
    }
}
