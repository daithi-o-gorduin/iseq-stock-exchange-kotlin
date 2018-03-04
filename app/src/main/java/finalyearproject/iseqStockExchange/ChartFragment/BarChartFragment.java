package finalyearproject.iseqStockExchange.ChartFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import finalyearproject.iseqStockExchange.POJO.Quote;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 21/01/2015.
 */
public class BarChartFragment extends Fragment{

    @InjectView(R.id.bar_chart)BarChart mChart;
    Quote mQuote;

    public BarChartFragment(Quote quote){
        this.mQuote = quote;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  barGraph = inflater.inflate(R.layout.barchart, container, false);
        ButterKnife.inject(this, barGraph);
        mChart.animateXY(3000, 3000);
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        BarEntry barEntryYHigh = new BarEntry((float)Double.parseDouble(mQuote.getYearsHigh()), 0);
        barEntries.add(barEntryYHigh);
        BarEntry barEntryYLow = new BarEntry((float)Double.parseDouble(mQuote.getYearsLow()), 1);
        barEntries.add(barEntryYLow);
        BarEntry barEntryCurrent = new BarEntry((float)mQuote.getLastTradePriceOnly(), 2);
        barEntries.add(barEntryCurrent);



        BarDataSet barDataSet = new BarDataSet(barEntries, mQuote.getName());
        barDataSet.setColors(new int[] { R.color.change_pos,R.color.change_neg,R.color.app_orange}, getActivity());

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(barDataSet);


        ArrayList<String> xValues = new ArrayList<String>();
        xValues.add("Year High");
        xValues.add("Year Low");
        xValues.add("Current Value");

        BarData barData = new BarData(xValues, dataSets);
        mChart.setData(barData);


        return barGraph;
    }

}
