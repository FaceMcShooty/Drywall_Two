package sapphyx.gsd.com.drywall.fragments.collections;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import sapphyx.gsd.com.drywall.R;
import sapphyx.gsd.com.drywall.util.AnimationHelper;
import sapphyx.gsd.com.drywall.views.PageIndicator;

import static sapphyx.gsd.com.drywall.activity.MainActivityBase.ARGS_INSTANCE;

/**
 * Created by ry on 2/28/18.
 */

public class LandingPage extends Fragment {

    public Context context;
    private int[] layouts;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private PageIndicator indicator;
    private HorizontalScrollView one, two;

    private Handler handler;
    private Runnable runnable;

    public LandingPage() {
    }

    public static LandingPage newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        LandingPage fragment = new LandingPage();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.collections_landing_page, container, false);
        setHasOptionsMenu(true);

        viewPager = v.findViewById(R.id.viewPager);
        indicator = v.findViewById(R.id.indicator);
        one = v.findViewById(R.id.sections_one);
        two = v.findViewById(R.id.sections_two);

        AnimationHelper.quickViewReveal(one, 400);
        AnimationHelper.quickViewReveal(two, 500);

        layouts = new int[]{
                R.layout.landing_one,
                R.layout.landing_two,
                R.layout.landing_three};

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        indicator.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 5000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                if(viewPager.getCurrentItem() == 0){
                    viewPager.setCurrentItem(1);
                } else if(viewPager.getCurrentItem() ==1){
                    viewPager.setCurrentItem(2);
                }else {
                    viewPager.setCurrentItem(0);
                }
            }
        };

        handler = new Handler();
        handler.postDelayed(runnable, 5000);


        return v;
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater != null ? layoutInflater.inflate(layouts[position], container, false) : null;
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}