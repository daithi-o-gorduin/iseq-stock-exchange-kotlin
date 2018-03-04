package finalyearproject.iseqStockExchange.ChartFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import finalyearproject.iseqStockExchange.Formatter.NumberFormatter;
import finalyearproject.iseqStockExchange.POJO.Quote;
import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.SQLiteDatabase.MySQLiteHelper;

/**
 * Created by Dvaid on 21/01/2015.
 */
public class PieChartFragment extends Fragment {


    //private PieChart mChart;
    @InjectView(R.id.pie_chart)PieChart mChart;
    private Quote mQuote;
    private NumberFormatter mNumFormatter;

    public PieChartFragment(Quote quote){
        this.mQuote = quote;
        mNumFormatter = new NumberFormatter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View pieChart = inflater.inflate(R.layout.piechart, container, false);
        ButterKnife.inject(this,pieChart);

        double totalPortfolioValue = 0.0, quotePercentageInPortfolio = 0.0;

        MySQLiteHelper stock_port_value = new MySQLiteHelper(getActivity());
        stock_port_value.open();
        totalPortfolioValue = stock_port_value.getPortfolioValueFromSQLLiteDB();
        quotePercentageInPortfolio = stock_port_value.getStockItemPortfolioValueFromSQLLiteDB(mQuote.getsymbol());
        stock_port_value.close();

        double portfolioMinusStockPercentage = totalPortfolioValue - quotePercentageInPortfolio;

        portfolioMinusStockPercentage = mNumFormatter.getPercentOfOneNumFromAnother(portfolioMinusStockPercentage,totalPortfolioValue);
        quotePercentageInPortfolio = mNumFormatter.getPercentOfOneNumFromAnother(quotePercentageInPortfolio,totalPortfolioValue);

        if(portfolioMinusStockPercentage == 0 && quotePercentageInPortfolio ==0){
            portfolioMinusStockPercentage = 100.00;
        }


        mChart.animateXY(3000, 3000);
        ArrayList<Entry> pieEntries = new ArrayList<>();


        Entry pieEntryPortfolio= new Entry((float)portfolioMinusStockPercentage, 0);
        pieEntries.add(pieEntryPortfolio);
        Entry pieEntryChart = new Entry((float)quotePercentageInPortfolio, 1);
        pieEntries.add(pieEntryChart);




        PieDataSet pieDataSet = new PieDataSet(pieEntries, "%");
        pieDataSet.setColors(new int[] { R.color.app_orange, R.color.change_neu}, getActivity());

        ArrayList<PieDataSet> dataSets = new ArrayList<PieDataSet>();
        dataSets.add(pieDataSet);


        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Other");
        xVals.add(mQuote.getsymbol());

        PieData data = new PieData(xVals,pieDataSet);
        mChart.setData(data);

        return pieChart;
    }






}
