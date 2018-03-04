package finalyearproject.drawer.ChartFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import finalyearproject.drawer.Constants.Constants;
import finalyearproject.drawer.R;
import finalyearproject.drawer.SharedPreferences.SharedPref;

/**
 * Created by Dvaid on 21/01/2015.
 */
public class LineChartFragment extends Fragment {

    LineChart chart;
    SharedPref mPref;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View lineGraph = inflater.inflate(R.layout.linechart, container, false);

        chart = (LineChart) lineGraph.findViewById(R.id.line_chart);
        chart.animateXY(3000, 3000);
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        mPref = new SharedPref(getActivity());
        ArrayList <Double> lineChartValues = mPref.loadChartValueFavSavedPreferences(Constants.CHART_VALUES);

        for(int i =0;i<lineChartValues.size();i++) {
           /* Entry c1e1 = new Entry(1.43f, i); // 0 == quarter 1

            valsComp1.add(c1e1);

            Entry c1e2 = new Entry(1.22f, i+1); // 1 == quarter 2 ...
            valsComp1.add(c1e2);
            Entry c1e3 = new Entry(1.27f, i+2); // 0 == quarter 1
            valsComp1.add(c1e3);
            Entry c1e4 = new Entry(1.56f, i+3); // 1 == quarter 2 ...
            valsComp1.add(c1e4);*/
            double temp = (double)lineChartValues.get(i);
            int temp2 = (int)temp;
            Entry entry = new Entry((float)temp2, i);
            valsComp1.add(entry);
        }


        // and so on ...



        LineDataSet setComp1 = new LineDataSet(valsComp1, "Chart Value");
        setComp1.setDrawCircles(false);

        setComp1.setColors(new int[] { R.color.list_divider}, getActivity());

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);


        ArrayList<String> xVals = new ArrayList<String>();
        for(int i = 0;i<lineChartValues.size();i++){
            xVals.add("");

        }




        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);

        return lineGraph;
    }

    public LineChart getLineChart(){
        return chart;
    }

}
