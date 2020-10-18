package home.fissher.wallpaperhub.activity;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import home.fissher.wallpaperhub.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.Glide;

import home.fissher.wallpaperhub.models.Favourite;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class SelectedWallpaper extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
File file;
    String url, desc, title, id;
    CheckBox checkBox;
    ProgressBar progressBar,progibar;
    public boolean t, d;
    Favourite fav;
    String category;

    boolean gone=false;
    Button buttonwp,buttonwplock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_wallpaper);
        Intent intent = getIntent();

       progibar=findViewById(R.id.progressbar);
        url = intent.getStringExtra("url");
        category = intent.getStringExtra("categoryname");
        desc = intent.getStringExtra("desc");
        title = intent.getStringExtra("title");
        id = intent.getStringExtra("id");
        t = intent.getBooleanExtra("truefalse", false);
        d = intent.getBooleanExtra("truefalsewallpaper", false);
        ImageView img;
        SharedPreferences prefs = getSharedPreferences("prefsd",MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStartd",true);


        if (firstStart) {

            ShowStartDialog();
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        img = findViewById(R.id.selected_imageview);
        fav = new Favourite(id, title, desc, url, category);
        Glide.with(getApplicationContext()).load(url).into(img);



        final ImageButton buttonShare, buttonDownload;
        checkBox = findViewById(R.id.checkbox_favourite);

        if (t)
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        if (d)
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

buttonwp = findViewById(R.id.wallpaperbuton);
buttonwplock=findViewById(R.id.wallpaperbutton);
        buttonShare = findViewById(R.id.button_share);
        buttonDownload = findViewById(R.id.button_download);

        checkBox.setOnCheckedChangeListener(this);
        buttonShare.setOnClickListener(this);
        buttonDownload.setOnClickListener(this);
buttonwp.setOnClickListener(new View.OnClickListener() {

    @Override

    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),"Processing...",Toast.LENGTH_SHORT).show();
        SetHomeAndLockScreen(true,false);




    }



});
buttonwplock.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),"Processing...",Toast.LENGTH_SHORT).show();
        SetHomeAndLockScreen(false,true);
    }
});


        RelativeLayout rl = (RelativeLayout) findViewById(R.id.tunjel);
        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonwplock.animate().alpha(0f).setDuration(300);
                    buttonDownload.animate().alpha(0f).setDuration(300);
                    buttonShare.animate().alpha(0f).setDuration(300);
                    checkBox.animate().alpha(0f).setDuration(300);
                    buttonwp.animate().alpha(0f).setDuration(300);
                }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP) {

                        buttonwplock.animate().alpha(1f).setDuration(300);
                        buttonDownload.animate().alpha(1f).setDuration(300);
                        buttonShare.animate().alpha(1f).setDuration(300);
                        checkBox.animate().alpha(1f).setDuration(300);
                        buttonwp.animate().alpha(1f).setDuration(300);

                    }
                return  true;
            }
        });





    }


    @Override
    public void onClick (View view) {

        switch (view.getId()) {
            case R.id.button_share:
                shareWallpaper(fav);

                break;
            case R.id.button_download:
                downloadWallpaper(fav);
                Toast.makeText(getApplicationContext(),"Image downloaded succesfully in InternalStorage/Download directory",Toast.LENGTH_LONG).show();
                break;

        }

    }

















    private void ShowStartDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Information")
                .setMessage("Hold your screen with your finger to see better the picture.You can download,share,favourite the picture")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();

        SharedPreferences prefs = getSharedPreferences("prefsd",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStartd",false);
        editor.apply();
    }

    private void shareWallpaper(Favourite w) {
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .asBitmap()
                .load(w.url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        progressBar.setVisibility(View.GONE);

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(resource));
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        startActivity(Intent.createChooser(intent, "Formula 1 Wallpaper"));

                    }
                });
    }


    public void setwallpaper() {

            Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER) ;
            intent.addCategory(Intent.ACTION_ATTACH_DATA);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setDataAndType(uri, "image/jpeg");
            intent.putExtra("mimeType", "image/jpeg");
            this.startActivity(Intent.createChooser(intent, "Set as:"));
        }


    private Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;


        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "wallpaper_hub_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    private Uri saveWallpaperAndGetUrl(Bitmap bitmap, String id) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);

                startActivity(intent);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);


            }
            return null;

        }


        File folder = new File(Environment.getExternalStorageDirectory().toString()+"/Download");


         file = new File(folder,id+".jpg");



        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();


            return Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void downloadWallpaper(final Favourite wallpaper) {

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .asBitmap()
                .load(wallpaper.url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        progressBar.setVisibility(View.GONE);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri uri = saveWallpaperAndGetUrl(resource,"Formula 1 - "+wallpaper.id);
                        if (uri != null) {
                            intent.setDataAndType(uri, "image/*");
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            startActivity(Intent.createChooser(intent, "Formula 1 Wallpaper"));
refreshGallery(file);

                        }


                    }
                });


    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            compoundButton.setChecked(false);
            return;
        }

        final DatabaseReference dbFavs = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("favourites")
                .child(fav.category);


        if (b) {
            dbFavs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.hasChild(fav.id)) {

                    } else {
                        dbFavs.child(fav.id).setValue(fav);
                        Toast.makeText(getApplicationContext(), "Saved as favourite", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else {
            dbFavs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.hasChild(fav.id)) {
                        dbFavs.child(fav.id).setValue(null);
                        Toast.makeText(getApplicationContext(), "Deleted from favourites", Toast.LENGTH_SHORT).show();

                    } else {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void refreshGallery(File file){
        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }


    public void SetHomeAndLockScreen(boolean home,boolean lock)
    {



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        WallpaperManager wpm = WallpaperManager.getInstance(getApplicationContext());
        Bitmap asd = null;
        try {
            URL url = new URL(fav.url);
             asd = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(IOException e) {
            System.out.println(e);
        }




        try {
            if(lock)
            {


                wpm.setBitmap(asd, null, true, WallpaperManager.FLAG_LOCK);


            }
            if(home){



                wpm.setBitmap(asd,null,true,WallpaperManager.FLAG_SYSTEM);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(lock)
        {

        Toast.makeText(getApplicationContext(), "Lock Screen Applied", Toast.LENGTH_SHORT).show();
        }
        if(home) {

            Toast.makeText(getApplicationContext(), "Home Screen Applied", Toast.LENGTH_SHORT).show();

        }

    }

}
