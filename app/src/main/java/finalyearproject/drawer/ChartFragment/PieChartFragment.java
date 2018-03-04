package finalyearproject.drawer.ChartFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import finalyearproject.drawer.Formatter.NumberFormatter;
import finalyearproject.drawer.POJO.Quote;
import finalyearproject.drawer.R;
import finalyearproject.drawer.SQLiteDatabase.MySQLiteHelper;

/**
 * Created by Dvaid on 21/01/2015.
 */
public class PieChartFragment extends Fragment {


    PieChart chart;
    Quote mQuote;
    NumberFormatter mNumFormatter;

    public PieChartFragment(Quote quote){
        this.mQuote = quote;
        mNumFormatter = new NumberFormatter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View pieGraph = inflater.inflate(R.layout.piechart, container, false);

        double totalPortfolioValue = 0.0, quoteValueInPortfolio = 0.0;

        MySQLiteHelper stock_port_value = new MySQLiteHelper(getActivity());
        stock_port_value.open();
        totalPortfolioValue = stock_port_value.getPortfolioValueFromSQLLiteDB();
        quoteValueInPortfolio = stock_port_value.getStockItemPortfolioValueFromSQLLiteDB(mQuote.getsymbol());
        stock_port_value.close();

        double portfolioMinusStockValue = totalPortfolioValue - quoteValueInPortfolio;

        portfolioMinusStockValue = mNumFormatter.getPercentOfOneNumFromAnother(portfolioMinusStockValue,totalPortfolioValue);
        quoteValueInPortfolio = mNumFormatter.getPercentOfOneNumFromAnother(quoteValueInPortfolio,totalPortfolioValue);

        if(portfolioMinusStockValue == 0 && quoteValueInPortfolio ==0){
            portfolioMinusStockValue = 100.00;
        }
        chart = (PieChart) pieGraph.findViewById(R.id.pie_chart);
        chart.animateXY(3000, 3000);
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();


        Entry c1e1 = new Entry((float)portfolioMinusStockValue, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry((float)quoteValueInPortfolio, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);




        PieDataSet setComp1 = new PieDataSet(valsComp1, "%");
        setComp1.setColors(new int[] { R.color.list_divider, R.color.change_neu}, getActivity());

        ArrayList<PieDataSet> dataSets = new ArrayList<PieDataSet>();
        dataSets.add(setComp1);


        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Other");
        xVals.add(mQuote.getsymbol());

        PieData data = new PieData(xVals,setComp1);
        chart.setData(data);

        return pieGraph;
    }


    public PieChart getPieChart(){
        return chart;
    }



}
