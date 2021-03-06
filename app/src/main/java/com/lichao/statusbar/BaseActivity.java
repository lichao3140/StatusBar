package com.lichao.statusbar;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView之前  全屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 5.0  4.4
     * @param toolbar
     * @param bottomView
     * @param styleColor
     */
    public void setToolBarStyle(Toolbar toolbar, View bottomView, int styleColor) {
        //5.0  4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (toolbar != null) {
                int statusHeight = getStatusHeight();
                Log.i("tuch", "   statusHeight  " + statusHeight);

                toolbar.setPadding(0, toolbar.getPaddingTop() + statusHeight, 0, 0);
                //下面的导航栏
                if (haveNavgtion()) {
                    ViewGroup.LayoutParams layoutParams = bottomView.getLayoutParams();
                    layoutParams.height += getNavigationHeight();
                    Log.i("tuch", "getNavigationHeight  " + getNavigationHeight());
                    bottomView.setLayoutParams(layoutParams);
                    bottomView.setBackgroundColor(styleColor);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            } else {
                //没办法修改
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean haveNavgtion() {
        //屏幕的高度  真实物理的屏幕
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        int heightDisplay = displayMetrics.heightPixels;
        //为了防止横屏
        int widthDisplay = displayMetrics.widthPixels;
        DisplayMetrics contentDisplaymetrics = new DisplayMetrics();
        display.getMetrics(contentDisplaymetrics);
        int contentDisplay=contentDisplaymetrics.heightPixels;
        int contentDisplayWidth=contentDisplaymetrics.widthPixels;
        //屏幕内容高度  显示内容的屏幕
        int w = widthDisplay - contentDisplayWidth;
        //哪一方大于0   就有导航栏
        int h = heightDisplay - contentDisplay;
        return w > 0|| h > 0;
    }

    public int getStatusHeight() {
        int height = -1;
        //运用反射获取
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField("status_bar_height").get(object).toString();
            height = Integer.parseInt(heightStr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return height;
    }

    public int getNavigationHeight() {
        int height=-1;
        try {
            Class<?> clazz=Class.forName("com.android.internal.R$dimen");
            Object  object=clazz.newInstance();
            String heightStr=clazz.getField("navigation_bar_height").get(object).toString();
            height = Integer.parseInt(heightStr);
            //dp--px
            height = getResources().getDimensionPixelSize(height);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return height;
    }
}
