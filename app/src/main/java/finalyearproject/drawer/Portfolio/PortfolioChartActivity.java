package finalyearproject.drawer.Portfolio;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;

import finalyearproject.drawer.ChartFragment.LineChartFragment;
import finalyearproject.drawer.Main.WatchListEmptyFragment;
import finalyearproject.drawer.R;

/**
 * Created by Dvaid on 12/02/2015.
 */
public class PortfolioChartActivity extends FragmentActivity {

    LinearLayout mToBeInvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_chart);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_porfolio_chart_container, new LineChartFragment())
                .commit();
      /*  mToBeInvisible = (LinearLayout) findViewById(R.id.ll_chart_ticker);
        mToBeInvisible.setVisibility(View.GONE);*/
    }
}
