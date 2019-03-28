package com.ian.codonib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ian.codonib.network.ServiceFactory;
import com.ian.codonib.network.UserManagers;
import com.ian.codonib.network.UserMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceFactory.setUserMode(UserManagers.getUserModes().get(0));
    }

    private void doPost(){
       ServiceFactory.doPost(4000);

    }

    public void btnPost(View view) {
        doPost();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ServiceFactory.getCurrentStemps();
    }
}
