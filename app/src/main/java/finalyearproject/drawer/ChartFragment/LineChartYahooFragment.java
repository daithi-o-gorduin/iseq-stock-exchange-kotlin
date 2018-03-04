package finalyearproject.drawer.ChartFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import java.util.ArrayList;

import finalyearproject.drawer.POJO.Quote;
import finalyearproject.drawer.POJO.ResultWrapper;
import finalyearproject.drawer.R;
import finalyearproject.drawer.REST.RetrofitInterface;
import finalyearproject.drawer.SQLiteDatabase.MySQLiteHelper;

/**
 * Created by Dvaid on 26/03/2015.
 */
public class LineChartYahooFragment extends Fragment {


    ImageView mYahooChart;
    private static int ZOOM_LEVEL = 125;
    Quote mQuote;
    String tickerIdBeforeFullStop;
    CircleProgressBar mCircularProgress;

    public LineChartYahooFragment(Quote quote) {
        this.mQuote = quote;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View yahooBarGraph = inflater.inflate(R.layout.barchart_yahoo, container, false);

        tickerIdBeforeFullStop = mQuote.getsymbol().split("\\.")[0];

        mYahooChart = (ImageView) yahooBarGraph.findViewById(R.id.iv_yahoo_chart);
        mCircularProgress = (CircleProgressBar) yahooBarGraph.findViewById(R.id.progressBar_chart);
        mCircularProgress.setColorSchemeResources(R.color.list_divider);
        networkTask task = new networkTask();
        task.execute();

        return yahooBarGraph;
    }


    private class networkTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {
            String url = "http://www.marketwatch.com/investing/stock/" + tickerIdBeforeFullStop + "/charts?CountryCode=ie";
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = null;
            String html = "";
            StringBuilder str = new StringBuilder();
            try {
                response = client.execute(request);
                // String html = "";
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