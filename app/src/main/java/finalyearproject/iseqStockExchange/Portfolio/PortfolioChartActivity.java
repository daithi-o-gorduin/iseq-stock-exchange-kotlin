package finalyearproject.iseqStockExchange.Portfolio;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;

import finalyearproject.iseqStockExchange.ChartFragment.PortfolioLineChartFragment;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 12/02/2015.
 */
public class PortfolioChartActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_chart);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_porfolio_chart_container, new PortfolioLineChartFragment())
                .commit();

    }
}
