package home.fissher.wallpaperhub.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import home.fissher.wallpaperhub.activity.adapters.WallpapersAdapter;
import home.fissher.wallpaperhub.R;


import home.fissher.wallpaperhub.models.Wallpaper;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WallpapersActivity extends AppCompatActivity {

    List<Wallpaper> wallpaperList;
    List<Wallpaper> favList;
    RecyclerView recyclerView;
    WallpapersAdapter adapter;
    DatabaseReference dbWallpapers,dbFavs;

    ProgressBar progressBar;

    AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        SharedPreferences prefs = getSharedPreferences("prefsds",MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStartds",true);


        if (firstStart) {

            ShowStartDialog();
        }



        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Intent intent= getIntent();
        String category = intent.getStringExtra("category");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(category+" - Tap at any image");
        setSupportActionBar(toolbar);
favList=new ArrayList<>();
        wallpaperList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        adapter = new WallpapersAdapter(this,wallpaperList);
        recyclerView.setAdapter(adapter);
        progressBar = findViewById(R.id.progressbar);
        dbWallpapers = FirebaseDatabase.getInstance().getReference("images")
                .child(category);

        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            dbFavs=FirebaseDatabase.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("favourites")
                    .child(category);

            fetchFavWallpapers(category);
        }
        else
        {
            fetchWallpapers(category);
        }





    }


    private void ShowStartDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Information")
                .setMessage("Tap any image.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();

        SharedPreferences prefs = getSharedPreferences("prefsds",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStartds",false);
        editor.apply();
    }

    private void fetchFavWallpapers(final String category){

        progressBar.setVisibility(View.VISIBLE);
        dbFavs.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if(dataSnapshot.exists()){

                    for(DataSnapshot wallpaperSnapshot:dataSnapshot.getChildren()){

                        String id= wallpaperSnapshot.getKey();
                        String title = wallpaperSnapshot.child("title").getValue(String.class);
                        String desc = wallpaperSnapshot.child("desc").getValue(String.class);
                        String url = wallpaperSnapshot.child("url").getValue(String.class);

                        Wallpaper w = new Wallpaper(id,title,desc,url,category);
                        favList.add(w);
                    }
                }
                fetchWallpapers(category);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchWallpapers(final String category){
        progressBar.setVisibility(View.VISIBLE);
        dbWallpapers.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if(dataSnapshot.exists()){

                    for(DataSnapshot wallpaperSnapshot:dataSnapshot.getChildren()){

                        String id= wallpaperSnapshot.getKey();
                        String title = wallpaperSnapshot.child("title").getValue(String.class);
                        String desc = wallpaperSnapshot.child("desc").getValue(String.class);
                        String url = wallpaperSnapshot.child("url").getValue(String.class);

                        Wallpaper w = new Wallpaper(id,title,desc,url,category);

                        if(isFavourite(w)){
                            w.isFavourite=true;
                        }

                        wallpaperList.add(w);
                    }
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


 private boolean isFavourite(Wallpaper w)
 {
   for(Wallpaper f:favList){
       if(f.id.equals(w.id)){
           return true;
       }
   }
return false;
 }

    @Override
    public void recreate() {
        super.recreate();
    }
}
