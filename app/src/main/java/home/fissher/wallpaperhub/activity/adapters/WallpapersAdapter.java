package home.fissher.wallpaperhub.activity.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.AdRequest;
import com.bumptech.glide.Glide;

import home.fissher.wallpaperhub.activity.SelectedWallpaper;
import home.fissher.wallpaperhub.models.Wallpaper;

import com.google.android.gms.ads.AdRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.ads.InterstitialAd;
import java.util.List;
import home.fissher.wallpaperhub.R;
public class WallpapersAdapter extends RecyclerView.Adapter<WallpapersAdapter.CategoryViewHolder> {

    private Context mCtx;
    private List<Wallpaper> wallpaperList;
    Wallpaper w;
    boolean d;
    int i=0;
    DatabaseReference dbFavs;
    private InterstitialAd mInterstitialAd;

    public WallpapersAdapter(Context mCtx, List<Wallpaper> wallpaperList) {
        this.mCtx=mCtx;
        this.wallpaperList = wallpaperList;
        mInterstitialAd = new InterstitialAd(mCtx);
        mInterstitialAd.setAdUnitId("ca-app-pub-8156706115088392/8896258374");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_wallpapers,parent,false);
        return new CategoryViewHolder(view);


    }
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {


            Wallpaper w = wallpaperList.get(position);
            Glide.with(mCtx)
                    .load(w.url)
                    .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       ImageView imageView;



        public CategoryViewHolder(View itemview){
            super(itemview);
            imageView=itemview.findViewById(R.id.image_view);
            itemview.setOnClickListener(this);



        }


        @Override
        public void onClick(View view) {

            if(i==3)
            {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
                i=0;
            }
            else
                i++;
            int p = getAdapterPosition();
     w = wallpaperList.get(p);

            Intent intent = new Intent(mCtx, SelectedWallpaper.class);
         if(FirebaseAuth.getInstance().getCurrentUser()!=null) {


             dbFavs = FirebaseDatabase.getInstance().getReference("users")
                     .child(FirebaseAuth.getInstance().getUid())
                     .child("favourites")
                     .child(w.category);


             dbFavs.addValueEventListener(new ValueEventListener() {

                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     if (snapshot.hasChild(w.id)) {
                         d = true;
                     } else {
                         d = false;
                     }

                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });

         }


            intent.putExtra("url", w.url);
            intent.putExtra("categoryname", w.category);
            intent.putExtra("id", w.id);
            intent.putExtra("title", w.title);
            intent.putExtra("desc", w.desc);
            intent.putExtra("truefalse", w.isFavourite);
            intent.putExtra("truefalsewallpaper", d);


            mCtx.startActivity(intent);

        }
    }


}
