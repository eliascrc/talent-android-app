package common;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.widget.EditText;


/**
 * This class contains methods that are used to programatically adjust views.
 *
 * @author Otto Mena Kikut
 */
public class ViewFormatUtil {

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static void setEditContainerColor(int color, EditText container, Context context) {
        GradientDrawable drawable = (GradientDrawable) container.getBackground();
        drawable.setStroke(dpToPx(1, context), ContextCompat.getColor(context, color));
    }
}
