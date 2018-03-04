package finalyearproject.iseqStockExchange.ISEQ;

/**
 * Created by Dvaid on 24/11/2014.
 */

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.yalantis.pulltorefresh.library.PullToRefreshView;

import java.util.ArrayList;
import java.util.Arrays;

import finalyearproject.iseqStockExchange.Constants.Constants;
import finalyearproject.iseqStockExchange.EventBus.BusProvider;
import finalyearproject.iseqStockExchange.EventBus.ObserverEvent;
import finalyearproject.iseqStockExchange.Main.MainActivity;
import finalyearproject.iseqStockExchange.Main.StockItemRow;
import finalyearproject.iseqStockExchange.Observer.Observer;
import finalyearproject.iseqStockExchange.POJO.Quote;
import finalyearproject.iseqStockExchange.POJO.ResultWrapper;
import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.REST.RESTCall;
import finalyearproject.iseqStockExchange.REST.RetrofitRESTCall;
import finalyearproject.iseqStockExchange.RecyclerViewAddOns.DividerItemDecoration;
import finalyearproject.iseqStockExchange.SQLiteDatabase.MySQLiteHelper;
import finalyearproject.iseqStockExchange.SharedPreferences.SharedPref;
import finalyearproject.iseqStockExchange.Subject.Subject;


public class ISEQFragment extends Fragment implements Subject {

    private ArrayList stockMarketObservers;
    private ArrayList StockItems;
    private ArrayList <Quote> QuoteItems;
    private MySQLiteHelper stock_group;

    RecyclerView recyclerView;
    RecyclerViewAdapter recylerViewAdapter;
    String value;
    boolean mWatchlist = false;
    ArrayList<Integer> favourites;
    SharedPref pref;
    ResultWrapper result;
    PullToRefreshView mPullToRefreshView;



    public ISEQFragment(Observer observer) {
        // TODO Auto-generated constructor stub

        stockMarketObservers = new ArrayList();
        StockItems = new ArrayList();
        QuoteItems = new ArrayList<>();
        this.favourites = new ArrayList<>();
        this.attach(observer);
        BusProvider.getInstance().register(this);
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            this.result = ((MainActivity) this.getActivity()).getRESTResult();
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            QuoteItems.addAll(new ArrayList(Arrays.asList(result.getQuery().getResults().getQuote())));
        }catch(NullPointerException e){

            e.printStackTrace();
        }
        Bundle bundle = getArguments();
        try {
            mWatchlist = bundle.getBoolean("Watchlist");
        }catch(NullPointerException e){
            mWatchlist = false;
        }
        pref = new SharedPref(getActivity());
        favourites = pref.loadFavSavedPreferences(Constants.FAVOURITES);

        View iseqView = inflater.inflate(R.layout.frag_iseq, container, false);


        recyclerView = (RecyclerView) iseqView.findViewById(R.id.recyclerView);
        recylerViewAdapter = new RecyclerViewAdapter(getActivity(),StockItems,mWatchlist,QuoteItems);

        recyclerView.setAdapter(recylerViewAdapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getDrawable(R.drawable.divider)));




        populateView(inflater,container);


        mPullToRefreshView = (PullToRefreshView) iseqView.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        try {
                            result = performAPICall();
                        }catch(Exception e){

                        }
                        ((MainActivity)getActivity()).setRESTResult(result);
                        stock_group = new MySQLiteHelper(getActivity());
                        stock_group.open();
                        ((MainActivity)getActivity()).setPortfolioValue(stock_group.getPortfolioValueFromSQLLiteDB());
                        ((MainActivity)getActivity()).setPortfolioCost(stock_group.getPortfolioCostFromSQLLiteDB());
                        stock_group.close();
                        populateView(inflater,container);

                    }
                }, 2000);
            }
        });


        return iseqView;


    }



    public void populateView(LayoutInflater inflater, ViewGroup container) {

        MySQLiteHelper stock_backup = new MySQLiteHelper(getActivity());

        stock_backup.open();
        StockItems.clear();
        stock_backup.truncateTable("STOCK_BACKUP");

            if(result!=null) {

                if (mWatchlist == true) {

                    for (int i = 0; i < favourites.size(); i++) {
                        StockItems.add(new StockItemRow(QuoteItems.get(favourites.get(i)).getsymbol(), QuoteItems.get(favourites.get(i)).getName(), QuoteItems.get(favourites.get(i)).getChangeInPercent(), QuoteItems.get(favourites.get(i)).getLastTradePriceOnly()));
                        stock_backup.createStockBackupEntry(QuoteItems.get(favourites.get(i)).getsymbol(), QuoteItems.get(favourites.get(i)).getName(), QuoteItems.get(favourites.get(i)).getLastTradePriceOnly(), QuoteItems.get(favourites.get(i)).getChangeInPercent());
                    }
                } else {
                    for (int i = 0; i < QuoteItems.size(); i++) {
                        StockItemRow item = new StockItemRow(QuoteItems.get(i).getsymbol(), QuoteItems.get(i).getName(), QuoteItems.get(i).getChangeInPercent(), QuoteItems.get(i).getLastTradePriceOnly());
                        StockItems.add(item);
                        stock_backup.createStockBackupEntry(QuoteItems.get(i).getsymbol(), QuoteItems.get(i).getName(), QuoteItems.get(i).getLastTradePriceOnly(), QuoteItems.get(i).getChangeInPercent());
                    }

                }
            }else{
                StockItems.addAll(getStockItemsFromBackup());
            }

           recylerViewAdapter.notifyDataSetChanged();
   }



    @Override
    public void attach(Observer observer) {
        // TODO Auto-generated method stub
        stockMarketObservers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        // TODO Auto-generated method stub
        int i = stockMarketObservers.indexOf(observer);
        if (i >= 0) {
            stockMarketObservers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        // TODO Auto-generated method stub
        for (int i = 0; i < stockMarketObservers.size(); i++) {
            Observer observer = (Observer)stockMarketObservers.get(i);
            observer.update(this);
        }
    }

    public String getValue(){
        return value;
    }


    private ResultWrapper performAPICall(){
        RESTCall task = new RetrofitRESTCall();
        ResultWrapper result = null;
        result = task.doTask(getActivity());

        return result;
    }


    @Subscribe
    public void adapterCallback(ObserverEvent event) {
        notifyObservers();
    }

    public ArrayList<StockItemRow> getStockItemsFromBackup(){
        MySQLiteHelper stock_backup = new MySQLiteHelper(getActivity());
        ArrayList<StockItemRow> temp = new ArrayList<StockItemRow>();
        stock_backup.open();
        temp = stock_backup.getStockBackup();
        stock_backup.close();
        return temp;
    }


}
