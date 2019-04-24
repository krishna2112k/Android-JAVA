package com.cityofnewyork.project.data.network.interfaces.callbacks;

import android.support.annotation.NonNull;
import com.cityofnewyork.project.data.network.services.Models.SATDetailsResponse;
import java.util.List;

import okhttp3.ResponseBody;

public interface GetSATDetails {

    void onSuccess(@NonNull List<SATDetailsResponse> responseBody);

    void onError(@NonNull Throwable throwable);
}
