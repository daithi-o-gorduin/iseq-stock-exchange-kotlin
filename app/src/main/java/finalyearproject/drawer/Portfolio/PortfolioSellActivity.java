package finalyearproject.drawer.Portfolio;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import finalyearproject.drawer.Constants.Constants;
import finalyearproject.drawer.PortfolioTransactionHistory.TransHistoryView;
import finalyearproject.drawer.R;
import finalyearproject.drawer.SQLiteDatabase.MySQLiteHelper;
import finalyearproject.drawer.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 31/03/2015.
 */
public class PortfolioSellActivity extends Activity{

    FrameLayout mSellContainer;
    ArrayList<StockPurchase> mTemp = new ArrayList<StockPurchase>();
    Set<Integer> mPurchasedStockIds = new TreeSet<Integer>(); // order is preserved

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_sell);
        mSellContainer = (FrameLayout) findViewById(R.id.fl_portfolio_sell);

        MySQLiteHelper stock_individual = new MySQLiteHelper(this);
        stock_individual.open();
        mTemp = stock_individual.getStockGroupEntry(Constants.BUY);
        stock_individual.close();
        for (int i = 0; i < mTemp.size(); i++) {
              mPurchasedStockIds.add(mTemp.get(i).getId());
        }
        mTemp = newArrayList(mTemp);

        mSellContainer.addView(new PortfolioSellView(this,mPurchasedStockIds,mTemp));

    }

    public StockPurchase combineStockPurchases(StockPurchase temp1,StockPurchase temp2){
        StockPurchase combinedStockPurchase = new StockPurchase(temp1.getId(),temp1.getTickerId(),temp1.getName(),temp1.getNum()+temp2.getNum(),
                temp1.getCurValue(),temp1.getTotalValue()+temp2.getTotalValue(),temp1.getTotalCost()+temp2.getTotalCost(),temp1.getDate(), Constants.BUY);
        return combinedStockPurchase;
    }


    public ArrayList<StockPurchase> newArrayList(ArrayList<StockPurchase> test){
        boolean match = false;
        for(int i = 0;i<test.size();i++){
            if(match == true){
                i = 0;
                match = false;
            }
            for(int j = i+1;j<test.size();j++){
                if(test.get(i).getId()== test.get(j).getId()){

                    test.add(0,combineStockPurchases(test.get(i),test.get(j)));
                    test.remove(j+1);
                    test.remove(i+1);
                    match = true;
                }

            }


        }
        return test;
    }

 }
