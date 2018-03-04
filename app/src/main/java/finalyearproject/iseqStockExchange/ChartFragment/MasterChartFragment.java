package finalyearproject.iseqStockExchange.ChartFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import finalyearproject.iseqStockExchange.CustomListeners.CustomOnPageChangeListener;
import finalyearproject.iseqStockExchange.EventBus.BusProvider;
import finalyearproject.iseqStockExchange.EventBus.SpinnerEvent;
import finalyearproject.iseqStockExchange.POJO.Quote;
import finalyearproject.iseqStockExchange.POJO.ResultWrapper;
import finalyearproject.iseqStockExchange.R;


/**
 * Created by Dvaid on 21/01/2015.
 */
public class MasterChartFragment extends Fragment {

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TextView mLine,mBar,mPie;
    private TextView mSpinnerHeading;
    private Quote[] mQuotes;
    private Quote mActiveQuote;
    private int mPosition;
    private Fragment[] mChartFragments = new Fragment[NUM_PAGES];
    private ArrayList<String> mDataForSpinner = new ArrayList<String>();

    public MasterChartFragment(ResultWrapper result, int position){
        this.mQuotes = result.getQuery().getResults().getQuote();
        for(int i = 0;i<mQuotes.length;i++) {
            mDataForSpinner.add(mQuotes[i].getsymbol());
        }
        mActiveQuote = mQuotes[0];
        BusProvider.getInstance().register(this);
        this.mPosition = position;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View charts = inflater.inflate(R.layout.chart, container, false);

        mPager = (ViewPager) charts.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mLine = (TextView) charts.findViewById(R.id.tv_line_chart);
        mBar = (TextView) charts.findViewById(R.id.tv_bar_chart);
        mPie = (TextView) charts.findViewById(R.id.tv_pie_chart);

        setListeners();

        mSpinnerHeading = (TextView) charts.findViewById(R.id.chart_spinner_heading);
        mSpinnerHeading.setText(mQuotes[mPosition].getsymbol());

        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new CustomOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                 iconSwitch(position);
            }
        });


        Spinner spinner = (Spinner) charts.findViewById(R.id.chart_spinner);

        ArrayAdapter<String> adapter = new ChartSpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                mDataForSpinner,mSpinnerHeading);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                mSpinnerHeading.setText(mQuotes[position].getsymbol());
                mActiveQuote = mQuotes[position];
                mPager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));

                iconSwitch(0);
            }


            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        return charts;
    }





   public void iconSwitch(int position){
        switch ( position ) {
           case 0:
               mBar.setBackgroundResource(R.drawable.mycircle);
               mLine.setBackgroundResource(R.drawable.mycircle_white_scale);
               mPie.setBackgroundResource(R.drawable.mycircle_white_scale);
               mBar.setTextColor(getResources().getColor(R.color.White));
               mLine.setTextColor(getResources().getColor(R.color.app_orange));
               mPie.setTextColor(getResources().getColor(R.color.app_orange));
                break;
            case 1:
                mBar.setBackgroundResource(R.drawable.mycircle_white_scale);
                mLine.setBackgroundResource(R.drawable.mycircle);
                mPie.setBackgroundResource(R.drawable.mycircle_white_scale);
                mBar.setTextColor(getResources().getColor(R.color.app_orange));
                mLine.setTextColor(getResources().getColor(R.color.White));
                mPie.setTextColor(getResources().getColor(R.color.app_orange));
                break;
            case 2:
                mBar.setBackgroundResource(R.drawable.mycircle_white_scale);
                mLine.setBackgroundResource(R.drawable.mycircle_white_scale);
                mPie.setBackgroundResource(R.drawable.mycircle);
                mBar.setTextColor(getResources().getColor(R.color.app_orange));
                mLine.setTextColor(getResources().getColor(R.color.app_orange));
                mPie.setTextColor(getResources().getColor(R.color.White));
                break;
        }
    }


    @Subscribe
    public void spinnerPressCallback(SpinnerEvent event) {
        mSpinnerHeading.setText(event.getActiveQuoteText());
    }

    public void setListeners(){
        mBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0,true);
                iconSwitch(0);
            }
        });

        mLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1,true);
                iconSwitch(1);
            }
        });

        mPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(2,true);
                iconSwitch(2);
            }
        });
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            mChartFragments[0] = new BarChartFragment(mActiveQuote);
            mChartFragments[1] = new LineChartYahooFragment(mActiveQuote);
            mChartFragments[2] = new PieChartFragment(mActiveQuote);
        }

        @Override
        public Fragment getItem(int position) {
            return mChartFragments[position];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }



    }

}

