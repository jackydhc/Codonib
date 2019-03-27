package com.ian.codonib.network;

import com.ian.codonib.StepDataOuterClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface CodonApi {

    @POST
    Call<String> postSteps(@Url String url,
                           @Body StepDataOuterClass.StepData data);
}
