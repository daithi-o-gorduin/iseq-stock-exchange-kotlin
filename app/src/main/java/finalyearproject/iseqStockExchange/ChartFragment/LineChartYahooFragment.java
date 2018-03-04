package finalyearproject.iseqStockExchange.ChartFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import finalyearproject.iseqStockExchange.POJO.Quote;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 26/03/2015.
 */
public class LineChartYahooFragment extends Fragment {

    Quote mQuote;
   /* ImageView mYahooChart;
    CircleProgressBar mCircularProgress;*/
    @InjectView(R.id.iv_yahoo_chart)ImageView mYahooChart;
    @InjectView(R.id.progressBar_chart)CircleProgressBar mCircularProgress;

    public LineChartYahooFragment(Quote quote) {
        this.mQuote = quote;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View yahooBarGraph = inflater.inflate(R.layout.barchart_yahoo, container, false);
        ButterKnife.inject(this,yahooBarGraph);
        /*mYahooChart = (ImageView) yahooBarGraph.findViewById(R.id.iv_yahoo_chart);
        mCircularProgress = (CircleProgressBar) yahooBarGraph.findViewById(R.id.progressBar_chart);*/
        mCircularProgress.setColorSchemeResources(R.color.app_orange);

        String tickerIdBeforeFullStop = mQuote.getsymbol().split("\\.")[0];//needed to query webpage
        networkTask task = new networkTask();
        task.execute(tickerIdBeforeFullStop);

        return yahooBarGraph;
    }


    private class networkTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String url = "http://www.marketwatch.com/investing/stock/" + params[0] + "/charts?CountryCode=ie";
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = null;
            String html = "";
            StringBuilder str = new StringBuilder();
            try {
                response = client.execute(request);
                InputStream in = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = null;

                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            html = str.toString();

            Document doc = Jsoup.parse(html);
            Elements pngs = doc.select("div[id=advchart] img[src]");
            String imgSrc = pngs.attr("src");
            return imgSrc;

        }

        @Override
        protected void onPostExecute(String result)
        {
            Picasso.with(getActivity()).load(result).into(mYahooChart);
            mCircularProgress.setVisibility(View.GONE);
        }

    }


}