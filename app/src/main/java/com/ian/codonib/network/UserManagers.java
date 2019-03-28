package com.ian.codonib.network;

import java.util.ArrayList;
import java.util.List;

public final class UserManagers {

    static List<UserMode> userModes = new ArrayList<>();

    public static List<UserMode> getUserModes(){
        if (userModes.size() <= 0) {
            UserMode userMode = new UserMode();
            userMode.uuid = "fb5f43b6-b4c6-4d33-aa50-44272457d053";
            userMode.deviceID = "0b22e16e-023c-451d-9fee-8f22a6117e28";
            userMode.name = "duhongchun";
            userMode.uniqID = "NULLDD9569E70630FECB88784469C2C56541";
            userMode.token = "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImZiNWY0M2I2LWI0YzYtNGQzMy1hYTUwLTQ0MjcyNDU3ZDA1MyIsImV4cCI6MTU2OTE2MTQxOSwic3ViIjoiZmI1ZjQzYjYtYjRjNi00ZDMzLWFhNTAtNDQyNzI0NTdkMDUzIn0.awP6GTGytZBPj1QLrvB7-9_5RxLyVx0Qhk7MaDKzkQX6iDa2Ap4Aclem_pIbySURy2KkO8VL5YFzJ5bLgkvxngJ2BDjRf37BQ1S97Ej9SuEdzDUNXwxcvIbC1tzvpeVs4b7xevvk1toackcXIh1d_iCFftUqMITu0f8RhPkp054";
            userModes.add(userMode);
        }
        return userModes;
    }

}
