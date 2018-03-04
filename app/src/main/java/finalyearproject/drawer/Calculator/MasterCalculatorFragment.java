package finalyearproject.drawer.Calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import finalyearproject.drawer.ChartFragment.BarChartFragment;
import finalyearproject.drawer.ChartFragment.LineChartFragment;
import finalyearproject.drawer.ChartFragment.PieChartFragment;
import finalyearproject.drawer.R;

/**
 * Created by Dvaid on 24/03/2015.
 */
public class MasterCalculatorFragment extends Fragment{

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    Fragment[] calculatorFragments = new Fragment[2];

    ImageView mFools,mSimple;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View calculator = inflater.inflate(R.layout.calculator, container, false);

        mPager = (ViewPager) calculator.findViewById(R.id.vp_calculator_pager);
        mPagerAdapter = new ScreenSlideCalculatorPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mFools = (ImageView) calculator.findViewById(R.id.iv_calculator_fools);
        mSimple = (ImageView) calculator.findViewById(R.id.iv_calculator_simple);

        setListeners();

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("CURRENT_PAGE: ", Integer.toString(position));
                iconSwitch(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return calculator;
    }



    private class ScreenSlideCalculatorPagerAdapter extends FragmentPagerAdapter {




        public ScreenSlideCalculatorPagerAdapter(FragmentManager fm) {
            super(fm);
            calculatorFragments[0] = new CalculatorSimple();
            calculatorFragments[1] = new FoolsRatioCalculatorFragment();

        }

        @Override
        public Fragment getItem(int position) {
            return calculatorFragments[position];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }



    }

    public void iconSwitch(int position){
        switch ( position ) {
            case 0:
                mSimple.setBackgroundResource(R.drawable.mycircle);
                mFools.setBackgroundResource(R.drawable.mycircle_white);
                break;
            case 1:
                mSimple.setBackgroundResource(R.drawable.mycircle_white);
                mFools.setBackgroundResource(R.drawable.mycircle);

                break;

        }
    }


    public void setListeners(){
        mSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0,true);
// Commit the transaction
                iconSwitch(0);
            }
        });

        mFools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1,true);
// Commit the transaction
                iconSwitch(1);
            }
        });


    }

}
