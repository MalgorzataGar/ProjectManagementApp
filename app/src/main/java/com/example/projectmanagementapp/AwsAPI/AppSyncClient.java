package com.example.projectmanagementapp.AwsAPI;

import android.content.Context;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;

public class AppSyncClient {
    private AWSAppSyncClient client;

    public void create(Context context) {
        client = AWSAppSyncClient.builder()
                .context(context)
                .awsConfiguration(new AWSConfiguration(context))
                .build();
    }

}
