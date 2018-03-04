package finalyearproject.iseqStockExchange.Calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnPageChange;
import finalyearproject.iseqStockExchange.CustomListeners.CustomOnPageChangeListener;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 24/03/2015.
 */
public class MasterCalculatorFragment extends Fragment{


    @InjectView(R.id.vp_calculator_pager) ViewPager mPager;
    @InjectView(R.id.iv_calculator_simple) ImageView mSimple;
    @InjectView(R.id.iv_calculator_fools) ImageView mFools;

    private static final int NUM_PAGES = 2;
    private PagerAdapter mPagerAdapter;
    private Fragment[] mCalculatorFragments = new Fragment[NUM_PAGES];



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View calculatorView = inflater.inflate(R.layout.calculator, container, false);
        ButterKnife.inject(this, calculatorView);

        mPagerAdapter = new ScreenSlideCalculatorPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        setListeners();

        mPager.setOnPageChangeListener(new CustomOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                iconSwitch(position);
            }

        });

        return calculatorView;
    }



    private class ScreenSlideCalculatorPagerAdapter extends FragmentPagerAdapter {

        public ScreenSlideCalculatorPagerAdapter(FragmentManager fm) {
            super(fm);
            mCalculatorFragments[0] = new SimpleCalculatorFragment();
            mCalculatorFragments[1] = new FoolsRatioCalculatorFragment();

        }

        @Override
        public Fragment getItem(int position) {
            return mCalculatorFragments[position];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    private void iconSwitch(int position){
        switch ( position ) {
            case 0:
                mSimple.setBackgroundResource(R.drawable.mycircle);
                mFools.setBackgroundResource(R.drawable.mycircle_white_scale);
                break;
            case 1:
                mSimple.setBackgroundResource(R.drawable.mycircle_white_scale);
                mFools.setBackgroundResource(R.drawable.mycircle);

                break;

        }
    }


    public void setListeners(){
        mSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0,true);
                iconSwitch(0);
            }
        });

        mFools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1,true);
                iconSwitch(1);
            }
        });


    }




}
