package finalyearproject.drawer.ChartFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import finalyearproject.drawer.EventBus.BusProvider;
import finalyearproject.drawer.EventBus.SpinnerEvent;
import finalyearproject.drawer.EventBus.WatchlistToISEQEvent;
import finalyearproject.drawer.POJO.Quote;
import finalyearproject.drawer.POJO.ResultWrapper;
import finalyearproject.drawer.R;


/**
 * Created by Dvaid on 21/01/2015.
 */
public class MasterChartFragment extends Fragment {

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TextView mLine,mBar,mPie;
    private TextView mSpinnerHeading;

    private ResultWrapper mResult;
    private Quote[] mQuotes;
    private Quote mActiveQuote;

    private int mPosition;

    Fragment[] chartFragments = new Fragment[3];

    private ArrayList<String> state = new ArrayList<String>();

    public MasterChartFragment(ResultWrapper result, int position){
        this.mResult = result;
        this.mQuotes = mResult.getQuery().getResults().getQuote();
        for(int i = 0;i<mQuotes.length;i++) {
            state.add(mQuotes[i].getsymbol());
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


        final Spinner spinner = (Spinner) charts.findViewById(R.id.chart_spinner);



        final ArrayAdapter<String> adapter1 = new ChartSpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                state,mSpinnerHeading);
        spinner.setAdapter(adapter1);

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




/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
        chartFragments[0] = new BarChartFragment(mActiveQuote);
        chartFragments[1] = new LineChartYahooFragment(mActiveQuote);
        chartFragments[2] = new PieChartFragment(mActiveQuote);
    }

    @Override
    public Fragment getItem(int position) {
            return chartFragments[position];
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }



}




    public void iconSwitch(int position){
        switch ( position ) {
           case 0:
                mBar.setBackgroundResource(R.drawable.mycircle);
                mLine.setBackgroundResource(R.drawable.mycircle_white);
                mPie.setBackgroundResource(R.drawable.mycircle_white);
               mBar.setTextColor(getResources().getColor(R.color.White));
               mLine.setTextColor(getResources().getColor(R.color.list_divider));
               mPie.setTextColor(getResources().getColor(R.color.list_divider));
                break;
            case 1:
                mBar.setBackgroundResource(R.drawable.mycircle_white);
                mLine.setBackgroundResource(R.drawable.mycircle);
                mPie.setBackgroundResource(R.drawable.mycircle_white);
                mBar.setTextColor(getResources().getColor(R.color.list_divider));
                mLine.setTextColor(getResources().getColor(R.color.White));
                mPie.setTextColor(getResources().getColor(R.color.list_divider));
                break;
            case 2:
                mBar.setBackgroundResource(R.drawable.mycircle_white);
                mLine.setBackgroundResource(R.drawable.mycircle_white);
                mPie.setBackgroundResource(R.drawable.mycircle);
                mBar.setTextColor(getResources().getColor(R.color.list_divider));
                mLine.setTextColor(getResources().getColor(R.color.list_divider));
                mPie.setTextColor(getResources().getColor(R.color.White));
                break;
        }
    }


    @Subscribe
    public void spinnerPressCallback(SpinnerEvent event) {
        String textForSpinnerHeading = event.getActiveQuoteText();
        int position = event.getPosition();
        mSpinnerHeading.setText(textForSpinnerHeading);
    }

    public void setListeners(){
        mBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0,true);
// Commit the transaction
                iconSwitch(0);
            }
        });

        mLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1,true);
// Commit the transaction
                iconSwitch(1);
            }
        });

        mPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(2,true);
// Commit the transaction
                iconSwitch(2);
            }
        });
    }

}

