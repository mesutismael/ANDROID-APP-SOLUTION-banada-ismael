package me.esmael.newsnow.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.esmael.newsnow.R;
import me.esmael.newsnow.fragments.LoginFragment;


/**
 * Created by banada ismael on 4/11/2017.
 */

public class LoginActivity extends AppCompatActivity {
    public static Intent getIntent(Context context) {
        return new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout_content, LoginFragment.newInstance())
                .commit();
    }
}
