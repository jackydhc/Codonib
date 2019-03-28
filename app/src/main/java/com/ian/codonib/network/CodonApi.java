package com.ian.codonib.network;


import com.ian.codonib.StepData;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface CodonApi {

    @POST
    Call<String> postSteps(@Url String url,
                             @Body StepData.StepDataReq data);
}
