package finalyearproject.iseqStockExchange.Main;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import finalyearproject.iseqStockExchange.Calculator.MasterCalculatorFragment;
import finalyearproject.iseqStockExchange.ChartFragment.MasterChartFragment;
import finalyearproject.iseqStockExchange.Constants.Constants;
import finalyearproject.iseqStockExchange.EventBus.BusProvider;
import finalyearproject.iseqStockExchange.EventBus.ReOpenNavEvent;
import finalyearproject.iseqStockExchange.EventBus.FavouritesEmptyEvent;
import finalyearproject.iseqStockExchange.EventBus.FavouritesEvent;
import finalyearproject.iseqStockExchange.EventBus.WatchlistToISEQEvent;
import finalyearproject.iseqStockExchange.Exit.ExitFragment;
import finalyearproject.iseqStockExchange.ISEQ.ISEQFragment;
import finalyearproject.iseqStockExchange.Observer.Observer;
import finalyearproject.iseqStockExchange.POJO.ResultWrapper;
import finalyearproject.iseqStockExchange.Portfolio.PortfolioFragment;
import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.SQLiteDatabase.MySQLiteHelper;
import finalyearproject.iseqStockExchange.SharedPreferences.SharedPref;
import finalyearproject.iseqStockExchange.Subject.Subject;


public class MainActivity extends ActionBarActivity implements Observer {

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerLinLay;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private ViewFlipper flipper;
    public ResultWrapper result;
    private double mPortfolioValue,mPortfolioCost;
    private MySQLiteHelper stock_group;
    private SharedPref pref;
    private ArrayList<Integer> favourites;
    private ArrayList<Double> chart_values;
    private boolean[] selectedVisible = new boolean[]{true,false,false,false,false,false};

    Fragment fragment = null;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = new SharedPref(this);
        chart_values = pref.loadChartValueFavSavedPreferences(Constants.CHART_VALUES);

        stock_group = new MySQLiteHelper(this);
        stock_group.open();
        setPortfolioValue(stock_group.getPortfolioValueFromSQLLiteDB());
        setPortfolioCost(stock_group.getPortfolioCostFromSQLLiteDB());
        stock_group.close();

        String callResult = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            callResult = bundle.getString("ResultWrapper");
        }
        result = new Gson().fromJson(callResult, ResultWrapper.class);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        final ViewFlipper v = (ViewFlipper) findViewById(R.id.switcher);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLinLay = (LinearLayout) findViewById(R.id.drawer_lin_lay);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<>();

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1),selectedVisible[0]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1),selectedVisible[1]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1),selectedVisible[2]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1),selectedVisible[3]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1),selectedVisible[4]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1),selectedVisible[5]));

        navMenuIcons.recycle();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });



        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, //nav menu toggle icon
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                mDrawerLayout.closeDrawer(mDrawerLinLay);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment)
                        .commit();

                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                adapter.notifyDataSetChanged();
                invalidateOptionsMenu();
            }
        };



        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            fragment = new ISEQFragment(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
        }

        flipper = (ViewFlipper) findViewById(R.id.switcher);
        flipper.setAutoStart(true);
        flipper.setFlipInterval(10000);

        BusProvider.getInstance().register(this);

        favourites = pref.loadFavSavedPreferences(Constants.FAVOURITES);
    }


    private void displayView(int position) {
        switch (position) {
            case 0:
                fragment = new ISEQFragment(this);
                updateNavDrawerItems(position);
                break;
            case 1:
                fragment = new PortfolioFragment();
                updateNavDrawerItems(position);
                break;
            case 2:
                if(favourites.size()==0){
                    fragment = new WatchListEmptyFragment();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("Watchlist", true);
                    fragment = new ISEQFragment(this);
                    fragment.setArguments(bundle);
                }

                updateNavDrawerItems(position);
                break;
            case 3:
                 fragment = new MasterChartFragment(result,0);
                 updateNavDrawerItems(position);
                break;
            case 4:
                fragment = new MasterCalculatorFragment();
                updateNavDrawerItems(position);
                break;

            case 5:
                fragment = new ExitFragment();
                updateNavDrawerItems(position);
                break;


            default:
                break;
        }

        if (fragment != null) {
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mDrawerLayout.closeDrawer(mDrawerLinLay);
                }
            }, 150);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


    public void updateNavDrawerItems(int position){
        //navDrawerItems.clear();
      /*  */
        for(int i = 0;i<selectedVisible.length;i++) {
            if (i != position){
                selectedVisible[i] = false;
            }else{
                selectedVisible[i] = true;
            }
            navDrawerItems.get(i).setSelected(selectedVisible[i]);
        }

    }


    public ResultWrapper getRESTResult(){
        return result;
    }

    public void setRESTResult(ResultWrapper result){
        this.result = result;
    }


    public double getPortfolioValue(){
        return mPortfolioValue;
    }

    public void setPortfolioValue(double portfolioValue){
        mPortfolioValue = portfolioValue;
    }

    public double getPortfolioCost(){
        return mPortfolioCost;
    }

    public void setPortfolioCost(double portfolioCost){
        mPortfolioCost = portfolioCost;
    }




    @Override
    public void update(Subject subject) {
        stock_group.open();
        setPortfolioValue(stock_group.getPortfolioValueFromSQLLiteDB());
        setPortfolioCost(stock_group.getPortfolioCostFromSQLLiteDB());
        chart_values.add(stock_group.getPortfolioValueFromSQLLiteDB());
        stock_group.close();
        pref.saveChartValuePreferences(chart_values,Constants.CHART_VALUES);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinLay);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            System.exit(-1);
        }else{
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }




    @Subscribe
    public void navDrawerCallback(ReOpenNavEvent event) {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Subscribe
    public void favouritesChangedCallback(FavouritesEvent event) {
        this.favourites = event.getFavourites();
    }

    @Subscribe
    public void watchlistToISEQCallback(WatchlistToISEQEvent event) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new ISEQFragment(this))
                .commit();
        updateNavDrawerItems(0);
     }

    @Subscribe
    public void favouritesEmptyCallback(FavouritesEmptyEvent event) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new WatchListEmptyFragment())
                .commit();
        favourites.clear();

    }
}