package codepath.com.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.io.File;

public class HomeActivity extends AppCompatActivity implements CameraFragment.OnItemSelectedListener, ProfileFragment.OnItemSelectedListener {

    public final String APP_TAG = "Parsetagram";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg"; // todo-- what goes here?
    final FragmentManager fragmentManager = getSupportFragmentManager();
    File photoFile;
    // define your fragments here
    final Fragment fragment1 = new FeedFragment();
    final Fragment fragment2 = new CameraFragment();
    final Fragment fragment3 = new ProfileFragment();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
        fragmentTransaction1.replace(R.id.placeholder, fragment1).commit();

        setContentView(R.layout.activity_home);


        // handle navigation selection

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.placeholder, fragment1).commit();
                                return true;
                            case R.id.camera:
                                Log.d("HomeActivity", "going to camera fragment");
                                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                                fragmentTransaction2.replace(R.id.placeholder, fragment2).commit();
                                return true;
                            case R.id.profile:
                                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                                fragmentTransaction3.replace(R.id.placeholder, fragment3).commit();
                                return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    public void onHitPostButton() {
            // launch feed fragment
            Log.d("HomeActivity", "onHitPostButton");
            bottomNavigationView.setSelectedItemId(R.id.home);

    }

    @Override
    public void onHitLogOutButton() {
        Intent i = new Intent (HomeActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
