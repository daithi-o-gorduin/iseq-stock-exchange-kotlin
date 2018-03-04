package finalyearproject.drawer.REST;

/**
 * Created by Dvaid on 24/11/2014.
 */

import android.os.Handler;

import finalyearproject.drawer.Constants.Constants;
import finalyearproject.drawer.POJO.ResultWrapper;
import retrofit.RestAdapter;
import retrofit.http.GET;

public class RetrofitInterface {
    private static StockApiInterface sStockService;

    public static StockApiInterface getStockApiClient() {

                if (sStockService == null) {
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint("https://query.yahooapis.com/v1/public")
                                    // .setLogLevel(RestAdapter.LogLevel.FULL)
                            .build();
                    sStockService = restAdapter.create(StockApiInterface.class);
                }


        return sStockService;

    }



    public interface StockApiInterface {
        @GET("/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(" + Constants.STOCK_INDEXES +")%0A%09%09&format=json&diagnostics=false&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=")
        ResultWrapper listQuotes();
    }
}
