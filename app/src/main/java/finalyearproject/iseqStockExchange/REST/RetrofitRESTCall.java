package finalyearproject.iseqStockExchange.REST;

import android.content.Context;
import android.os.AsyncTask;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import finalyearproject.iseqStockExchange.POJO.ResultWrapper;
import finalyearproject.iseqStockExchange.SQLiteDatabase.MySQLiteHelper;
import retrofit.RetrofitError;

/**
 * Created by Dvaid on 19/01/2015.
 */
public class RetrofitRESTCall implements RESTCall{

    ResultWrapper result;
    private Context mContext;

    @Override
    public ResultWrapper doTask(Context context) {
        this.mContext = context;
        extraThread thread = new extraThread();
        try {
            result = thread.execute().get();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch(RetrofitError e){
            e.printStackTrace();
        }
        return getResultWrapper();
    }

    private class extraThread extends AsyncTask<Void, Void, ResultWrapper> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ResultWrapper doInBackground(Void... params) {

                ResultWrapper result = RetrofitInterface.getStockApiClient()
                        .listQuotes();
                MySQLiteHelper stock_group = new MySQLiteHelper(mContext);
                stock_group.open();
                stock_group.updateStockValues(result);
                stock_group.close();



            return result;
        }


        @Override
        protected void onPostExecute(ResultWrapper result) {
            super.onPostExecute(result);

        }



    }

    public ResultWrapper getResultWrapper(){
        return result;
    }

}
