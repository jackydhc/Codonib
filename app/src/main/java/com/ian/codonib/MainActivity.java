package com.ian.codonib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ian.codonib.network.ServiceFactory;
import com.ian.codonib.network.UserMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    private void doPost(){
        StepDataOuterClass.Step step =  StepDataOuterClass.Step.newBuilder()
                .setTime(201903271).setStep(50).build();
        List<StepDataOuterClass.Step> stepList = new ArrayList<>()
                ;
        stepList.add(step);
        StepDataOuterClass.StepData stepData = StepDataOuterClass.StepData.newBuilder()
                .setId(UUID.randomUUID().toString())
                .addAllSteps(stepList)
                .build();

    }
}
