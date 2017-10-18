package ramymichealcenter.patient.viewcontroller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import ramymichealcenter.patient.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView nvDrawer;
    private static FragmentManager fragmentManager;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();


        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        nvDrawer.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {

            nvDrawer.getMenu().getItem(0).setChecked(true);
            setTitle(nvDrawer.getMenu().getItem(0).getTitle());

            Fragment fragment = new HomeFragment();

            fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.content_main, fragment, HomeFragment.class.getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
        }

    }


    protected void replaceFragment(Fragment fragment, String tag) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content_main, fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {

        Fragment ServiceDetailsFragment = fragmentManager.findFragmentByTag("ServiceDetailsFragment");
        Fragment ReservationDialogFragment = fragmentManager.findFragmentByTag("ReservationDialogFragment");
        Fragment DetailPost_Fragment = fragmentManager.findFragmentByTag("DetailPost_Fragment");

        if (ServiceDetailsFragment != null) {
            String previous_activity = ServiceDetailsFragment.getArguments().getString("previous_activity");
            switch (previous_activity) {
                case "ServiceFragment":
                    replaceFragment(new ServiceFragment(), "ServiceFragment");
                    break;
                case "HomeFragment":
                    replaceFragment(new HomeFragment(), "HomeFragment");
                    break;
            }
        } else if (DetailPost_Fragment != null) {
            replaceFragment(new PostFragment(), "PostFragment");
        } else if (ReservationDialogFragment != null) {
            String previous_activity = ReservationDialogFragment.getArguments().getString("previous_activity");
            switch (previous_activity) {
                case "HomeFragment":
                case "ServiceFragment":
                    replaceFragment(new ServiceDetailsFragment(), "ServiceDetailsFragment",
                            ReservationDialogFragment.getArguments().getInt("service_position"),
                            ReservationDialogFragment.getArguments().getString("previous_activity"));
                    break;
                case "ReservationFragment":
                    replaceFragment(new ReservationFragment(), "ServiceDetailsFragment",
                            ReservationDialogFragment.getArguments().getInt("service_position"),
                            ReservationDialogFragment.getArguments().getString("previous_activity"));
                    break;
            }
        } else if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment, String tag, int service_position, String previous_activity) {

        Bundle bundle = new Bundle();
        bundle.putInt("service_position", service_position);
        bundle.putString("previous_activity", previous_activity);

        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content_main, fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.e(TAG, "onOptionsItemSelected: ");
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        Class fragmentClass;

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_services:
                fragmentClass = ServiceFragment.class;
                break;
            case R.id.nav_reservation:
                fragmentClass = ReservationFragment.class;
                break;
            case R.id.nav_calories:
                fragmentClass = CaloriesFragment.class;
                break;
            case R.id.nav_post:
                fragmentClass = PostFragment.class;
                break;
            case R.id.nav_contact_us:
                fragmentClass = ContactUsFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Insert the fragment by replacing any existing fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content_main, fragment, HomeFragment.class.getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();


        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawer(GravityCompat.START);


        return true;
    }


    public void openBookAppointmentScreeen() {
        nvDrawer.getMenu().getItem(2).setChecked(true);
        setTitle(nvDrawer.getMenu().getItem(2).getTitle());

        Fragment fragment = new ReservationFragment();

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content_main, fragment, ReservationFragment.class.getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();

    }

    public void setDrawerState(boolean isEnabled) {

        if (isEnabled) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

        } else {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.ic_arrow_forward_black_24dp);
            toggle.syncState();

        }
    }

}
