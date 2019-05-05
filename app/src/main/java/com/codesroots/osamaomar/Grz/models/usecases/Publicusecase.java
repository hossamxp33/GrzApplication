package com.codesroots.osamaomar.Grz.models.usecases;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

public class Publicusecase {


    public  static  void makeToas(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


    public static void makeSnakBare(View view,String message)
    {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }


    public static void setupviewPager(ViewPager viewPager) {
        viewPager.setPageTransformer(true, (view, position) -> {
            view.setTranslationX(-position * view.getWidth());
            if (Math.abs(position) < 0.5) {
                view.setVisibility(View.VISIBLE);
                view.setScaleX(1 - Math.abs(position));
                view.setScaleY(1 - Math.abs(position));
            } else if (Math.abs(position) > 0.5) {
                view.setVisibility(View.GONE);
            }
        });
    }



}
