package net.better.hongqian.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by HongQian.Wang on 2018/2/12.
 */

public class UiUtil {
    public UiUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**  下面是关于Android 单位转换的源码   如果返回来计算的话 因为是除法  需要加上0.5f 这样更能得到精确值
     * public static float applyDimension(int unit, float value,
     DisplayMetrics metrics)
     {
     switch (unit) {
     case COMPLEX_UNIT_PX:
     return value;
     case COMPLEX_UNIT_DIP:
     return value * metrics.density;
     case COMPLEX_UNIT_SP:
     return value * metrics.scaledDensity;
     case COMPLEX_UNIT_PT:
     return value * metrics.xdpi * (1.0f/72);
     case COMPLEX_UNIT_IN:
     return value * metrics.xdpi;
     case COMPLEX_UNIT_MM:
     return value * metrics.xdpi * (1.0f/25.4f);
     }
     return 0;
     }
     */


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */

    public static int dp2px(Context context, float dpVal)

    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,

                dpVal, context.getResources().getDisplayMetrics());

    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dp(Context context, float pxVal)

    {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (pxVal / scale + 0.5f);

    }


    public static int sp2px(Context context, float spVal)

    {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,

                spVal, context.getResources().getDisplayMetrics());

    }


    public static float px2sp(Context context, float pxVal)

    {

        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity + 0.5f);

    }

    /**
     * 获取屏幕宽高
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {  //得到的不包括虚拟按键的高度
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    /**
     * 获得状态栏的高度
     * 通过反射实现
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


    /**
     *  获取屏幕原始尺寸高度，包括虚拟功能键高度和屏幕高度
      */

    public static int getDisplayHeight(Context context){
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi=displayMetrics.heightPixels;
        }catch(Exception e){
            e.printStackTrace();
        }
        return dpi;
    }


    /**
     * 获取 虚拟按键的高度
     * @param context
     * @return
     */
    public static  int getBottomStatusHeight(Context context){
        int totalHeight = getDisplayHeight(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight  - contentHeight;
    }
}
