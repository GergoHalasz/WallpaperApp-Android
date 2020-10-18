package home.fissher.wallpaperhub.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.gms.ads.AdRequest;

import home.fissher.wallpaperhub.Fragments.FavouritesFragment;
import home.fissher.wallpaperhub.Fragments.HomeFragment;
import home.fissher.wallpaperhub.Fragments.SettingsFragment;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import home.fissher.wallpaperhub.R;
import hotchemi.android.rate.AppRate;

public class HomeActiviy extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private InterstitialAd mInterstitialAd;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        ConnectivityManager connectivityManager =(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        AdView mAdView;
        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable())
        {
            NetworkDialog dialog = new NetworkDialog();
            dialog.show(getSupportFragmentManager(),"Network connection");



 }
        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);


        if (firstStart) {

            ShowStartDialog();
        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8156706115088392/8896258374");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());




        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        AppRate.with(this)
               .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);
        AppRate.with(this).clearAgreeShowDialog();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
   displayFragment(new HomeFragment());
    }
    private void displayFragment(Fragment fragment)
    {
 getSupportFragmentManager()
         .beginTransaction()
         .replace(R.id.content_area,fragment)
         .commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId())
        {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_fav:
                fragment = new FavouritesFragment();
                break;
            case R.id.nav_set:
                fragment = new SettingsFragment();
                break;
                default:
                    fragment = new HomeFragment();
                    break;
        }
        displayFragment(fragment);
        return true;
    }


    private void ShowStartDialog(){
      new AlertDialog.Builder(this)
              .setTitle("Information")
              .setMessage("Tap at a driver.")
              .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.dismiss();
                  }
              })
              .create().show();
       SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
       SharedPreferences.Editor editor = prefs.edit();
       editor.putBoolean("firstStart",false);
       editor.apply();
    }

}
