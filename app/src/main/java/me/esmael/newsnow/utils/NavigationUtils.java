package me.esmael.newsnow.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import me.esmael.newsnow.R;
import me.esmael.newsnow.activities.LoginActivity;
import me.esmael.newsnow.activities.MainActivity;


/**
 * Created by esmael256 on 4/12/2017.
 */

public class NavigationUtils {

    public static void startActivity(Context context, Class activityClass) {
        Log.d("starting activity", "starting activity");
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Intent intent) {
        Log.d("starting activity", "starting activity");
        context.startActivity(intent);
    }


    public static void logOut(Context context) {
        PreferenceHelper.clearUser(context);
        Intent intent = LoginActivity.getIntent(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


}
