package finalyearproject.drawer.ChartFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import finalyearproject.drawer.POJO.Quote;
import finalyearproject.drawer.R;

/**
 * Created by Dvaid on 21/01/2015.
 */
public class BarChartFragment extends Fragment{

    BarChart chart;
    Quote mQuote;

    public BarChartFragment(Quote quote){
        this.mQuote = quote;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  barGraph = inflater.inflate(R.layout.barchart, container, false);

        chart = (BarChart) barGraph.findViewById(R.id.bar_chart);
        chart.animateXY(3000, 3000);
        ArrayList<BarEntry> valsComp1 = new ArrayList<BarEntry>();

        BarEntry c1e1 = new BarEntry((float)Double.parseDouble(mQuote.getYearsHigh()), 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        BarEntry c1e2 = new BarEntry((float)Double.parseDouble(mQuote.getYearsLow()), 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        BarEntry c1e3 = new BarEntry((float)mQuote.getLastTradePriceOnly(), 2); // 0 == quarter 1
        valsComp1.add(c1e3);
        /*BarEntry c1e4 = new BarEntry(1.56f, 3); // 1 == quarter 2 ...
        valsComp1.add(c1e4);*/

        // and so on ...



        BarDataSet setComp1 = new BarDataSet(valsComp1, mQuote.getName());
        setComp1.setColors(new int[] { R.color.change_pos,R.color.change_neg,R.color.list_divider}, getActivity());

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(setComp1);


        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Year High (€)"); xVals.add("Year Low (€)"); xVals.add("Current Price (€)");// xVals.add("April");

        BarData data = new BarData(xVals, dataSets);
        chart.setData(data);


        return barGraph;
    }

    public BarChart getBarChart(){
        return chart;
    }





}
