package home.fissher.wallpaperhub.activity.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import home.fissher.wallpaperhub.activity.WallpapersActivity;
import home.fissher.wallpaperhub.models.Category;
import home.fissher.wallpaperhub.R;

import com.google.android.gms.ads.AdRequest;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.InterstitialAd;
import com.bumptech.glide.Glide;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    private Context mCtx;
    private List<Category> categoryList;
    private InterstitialAd mInterstitialAd;


    public CategoriesAdapter(Context mCtx,List<Category> categoryList) {
        this.mCtx=mCtx;
        this.categoryList=categoryList;
        mInterstitialAd = new InterstitialAd(mCtx);
        mInterstitialAd.setAdUnitId("ca-app-pub-8156706115088392/8896258374");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


    }

    public CategoryViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_categories,parent,false);
        return new CategoryViewHolder(view);


    }
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {


           if(position==0) {//lewis


               Category c = categoryList.get(12);
               holder.textView.setText(c.name);
               Glide.with(mCtx)
                       .load(c.thumb)
                       .into(holder.imageView);


           }

        if(position==1) {//bottas


            Category c = categoryList.get(19);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }


        if(position==2) {//seb


            Category c = categoryList.get(17);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }
        if(position==3) {//lecler


            Category c = categoryList.get(3);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }

        if(position==4) {//max


            Category c = categoryList.get(13);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }

        if(position==5) {//albon


            Category c = categoryList.get(0);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }



        if(position==6) {//sainz


            Category c = categoryList.get(2);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }



        if(position==7) {//lando


            Category c = categoryList.get(11);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }
        if(position==8) {//ric


            Category c = categoryList.get(4);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }

        if(position==9) {//ocon


            Category c = categoryList.get(6);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }
        if(position==10) {//raikonnen


            Category c = categoryList.get(9);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }

        if(position==11) {//antoniao gio


            Category c = categoryList.get(1);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }
        if(position==12) {//sergio


            Category c = categoryList.get(18);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }
        if(position==13) {//lance


            Category c = categoryList.get(10);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }
        if(position==14) {//kvyat


            Category c = categoryList.get(5);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }

        if(position==15) {//gasly


            Category c = categoryList.get(15);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }

        if(position==16) {//russell


            Category c = categoryList.get(7);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }


        if(position==17) {//latifi


            Category c = categoryList.get(14);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }

        if(position==18) {//kevin


            Category c = categoryList.get(8);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }
        if(position==19) {//grosjean


            Category c = categoryList.get(16);
            holder.textView.setText(c.name);
            Glide.with(mCtx)
                    .load(c.thumb)
                    .into(holder.imageView);


        }




    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView textView;
       ImageView imageView;

        public CategoryViewHolder(View itemview){
            super(itemview);

            textView=itemview.findViewById(R.id.text_view_cat_name);
            imageView=itemview.findViewById(R.id.image_view);
            itemview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
           if(p==0) {
               Category c = categoryList.get(12);

               Intent intent = new Intent(mCtx, WallpapersActivity.class);
               intent.putExtra("category", c.name);

               mCtx.startActivity(intent);
           }
           if(p==1)
           {


               Category c = categoryList.get(19);

               Intent intent = new Intent(mCtx, WallpapersActivity.class);
               intent.putExtra("category", c.name);

               mCtx.startActivity(intent);



           }
           if(p==2)
           {


               Category c = categoryList.get(17);

               Intent intent = new Intent(mCtx, WallpapersActivity.class);
               intent.putExtra("category", c.name);

               mCtx.startActivity(intent);



           }
            if(p==3) {


                Category c = categoryList.get(3);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==4) {


                Category c = categoryList.get(13);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==5) {

                Category c = categoryList.get(0);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==6) {


                Category c = categoryList.get(2);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==7) {


                Category c = categoryList.get(11);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==8) {


                Category c = categoryList.get(4);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==9) {


                Category c = categoryList.get(6);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==10) {


                Category c = categoryList.get(9);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==11) {


                Category c = categoryList.get(1);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==12) {


                Category c = categoryList.get(18);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==13) {


                Category c = categoryList.get(10);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==14) {


                Category c = categoryList.get(5);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==15) {


                Category c = categoryList.get(15);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==16) {


                Category c = categoryList.get(7);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==17) {


                Category c = categoryList.get(14);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==18) {


                Category c = categoryList.get(8);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }
            if(p==19) {


                Category c = categoryList.get(16);

                Intent intent = new Intent(mCtx, WallpapersActivity.class);
                intent.putExtra("category", c.name);

                mCtx.startActivity(intent);
            }

            }
        }
    }


