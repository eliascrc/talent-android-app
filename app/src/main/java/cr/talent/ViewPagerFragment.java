package cr.talent;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * This class is responsible for managing the action of a ViewPager.
 * Creating a "carousel".
 *
 * @author Renato Mainieri Sáenz.
 */
public class ViewPagerFragment extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.landing_view_1, R.drawable.landing_view_2, R.drawable.landing_view_3};
    private LinearLayout indexLayout;

    /**
     * This constructor instantiates the indexes to indicate in what position the "carousel" starts.
     *
     * @param context,     Context in which the "carousel" will be contained.
     * @param indexLayout, LinearLayout that contains the indexes.
     */
    public ViewPagerFragment(Context context, LinearLayout indexLayout) {
        this.context = context;
        this.indexLayout = indexLayout;
        for (int i = 0; i < this.getCount(); i++) {
            if (i == 0) {
                indexLayout.getChildAt(i).setBackgroundResource(R.drawable.circle_carousel_active);
            } else {
                indexLayout.getChildAt(i).setBackgroundResource(R.drawable.circle_carousel_inactive);
            }
        }
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals((View) object);
    }

    /**
     * Create the page for the given position.
     * Also assigns values ​​to the indices to indicate in what position the "carousel" is currently.
     *
     * @param container, The containing View in which the page will be shown.
     * @param position,  The page position to be instantiated.
     * @return Returns an Object representing the new page.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_view_pager, null);
        ImageView imageView = view.findViewById(R.id.image_view_pager);
        imageView.setImageResource(images[position]);
        ViewPager viewPager = (ViewPager) container;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0; i < getCount(); i++) {
                    if (i == position) {
                        indexLayout.getChildAt(i).setBackgroundResource(R.drawable.circle_carousel_active);
                    } else {
                        indexLayout.getChildAt(i).setBackgroundResource(R.drawable.circle_carousel_inactive);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View viewToDelete = (View) object;
        viewPager.removeView(viewToDelete);
    }
}
