package finalyearproject.iseqStockExchange.ChartFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import finalyearproject.iseqStockExchange.Constants.Constants;
import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.SharedPreferences.SharedPref;

/**
 * Created by Dvaid on 21/01/2015.
 */
public class PortfolioLineChartFragment extends Fragment {

    @InjectView(R.id.line_chart)LineChart mChart;
    SharedPref mPref;
    ArrayList <Double> mLineChartValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View lineChart = inflater.inflate(R.layout.linechart, container, false);
        ButterKnife.inject(this,lineChart);

        mChart.animateXY(3000, 3000);

        ArrayList<Entry> lineEntries = new ArrayList<>();
        mPref = new SharedPref(getActivity());
        mLineChartValues = mPref.loadChartValueFavSavedPreferences(Constants.CHART_VALUES);

        for(int i =0;i<mLineChartValues.size();i++) {

            double temp = (double)mLineChartValues.get(i);
            int convertedTemp = (int)temp;
            Entry lineEntry = new Entry((float)convertedTemp, i);
            lineEntries.add(lineEntry);
        }

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Portfolio Value");
        lineDataSet.setDrawCircles(false);

        lineDataSet.setColors(new int[] { R.color.app_orange}, getActivity());

        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet);


        ArrayList<String> xValues = new ArrayList<String>();
        for(int i = 0;i<mLineChartValues.size();i++){
            xValues.add("");

        }

        LineData lineData = new LineData(xValues, lineDataSets);
        mChart.setData(lineData);

        return lineChart;
    }


}
