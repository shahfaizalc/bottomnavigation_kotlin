package com.reelme.app.util;

import com.reelme.app.pojos.UserModel;

/**
 * Created by philips on 07/09/17.
 */

public class UpdateMobile {
    private final UserModel mobileNumber;


    public UpdateMobile(UserModel mobileNumber) {
        this.mobileNumber= mobileNumber;
    }

    public UserModel getMobileNumber() {
        return mobileNumber;
    }

}

