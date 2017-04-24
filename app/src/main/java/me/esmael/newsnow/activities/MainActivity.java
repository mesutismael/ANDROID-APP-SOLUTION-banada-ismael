package me.esmael.newsnow.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.esmael.newsnow.R;
import me.esmael.newsnow.fragments.NewsFragment;
import me.esmael.newsnow.fragments.SourcesFragment;
import me.esmael.newsnow.utils.Constants;
import me.esmael.newsnow.utils.NavigationUtils;
import me.esmael.newsnow.utils.PreferenceHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewsFragment.OnFragmentInteractionListener,
        SourcesFragment.OnFragmentInteractionListener{

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(TextUtils.isEmpty(PreferenceHelper.getPreferenceEmail(this)))
        {
          NavigationUtils.logOut(this);
        }else
            {
                setContentView(R.layout.activity_main);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.setDrawerListener(toggle);
                toggle.syncState();

                findViewById(R.id.fab).setOnClickListener(v -> {
                    Toast.makeText(this, "News now App", Toast.LENGTH_SHORT).show();
                });

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);

                this.startFragment(NewsFragment.newInstance(Constants.SOURCE_TECH_CRUNCH));

            }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String category=null;
        String source=null;

        switch (id) {
            case R.id.nav_articles:
                source=Constants.SOURCE_TECH_CRUNCH;
                fragment=NewsFragment.newInstance(source);
                break;
            case R.id.nav_technology:
                source=Constants.SOURCE_TECH_CRUNCH;
                fragment=NewsFragment.newInstance(source);
                break;

            case R.id.nav_sources:
                category=Constants.CATEGORY_TECHNOLOGY;
                fragment= SourcesFragment.newInstance(category);
                break;

            case R.id.nav_logout:
                NavigationUtils.logOut(MainActivity.this);
                break;


        }
        if (fragment != null) {
            MainActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout_content,  fragment)
                    .commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void startFragment(Fragment fragment)
    {
        MainActivity.this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout_content, fragment)
                .commit();
    }
}
