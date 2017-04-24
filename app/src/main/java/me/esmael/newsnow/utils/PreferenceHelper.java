package me.esmael.newsnow.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by esmael256 on 4/11/2017.
 */

public class PreferenceHelper {
    private static final String PREFERENCE_NAME = "newsnow";

    private static final String PREFERENCE_PASSWORD = "password";
    private static final String PREFERENCE_EMAIL = "email";



    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }



    public static String getPreferenceEmail(Context context) {
        return PreferenceHelper.getPreferences(context).getString(PREFERENCE_EMAIL, null);
    }

    public static void savePreferenceEmail(Context context, String email) {
        SharedPreferences.Editor prefs = PreferenceHelper.getPreferences(context).edit();
        prefs.putString(PREFERENCE_EMAIL, email);
        prefs.apply();
    }


    public static String getPreferencePassword(Context context) {
        return PreferenceHelper.getPreferences(context).getString(PREFERENCE_PASSWORD, null);
    }

    public static void savePreferencePassword(Context context, String androidKey) {
        SharedPreferences.Editor prefs = PreferenceHelper.getPreferences(context).edit();
        prefs.putString(PREFERENCE_PASSWORD, androidKey);
        prefs.apply();
    }


    public static void clearUser(Context context) {
        SharedPreferences.Editor prefs = PreferenceHelper.getPreferences(context).edit();

        prefs.remove(PREFERENCE_EMAIL);
        prefs.remove(PREFERENCE_PASSWORD);

        prefs.apply();
    }
}

