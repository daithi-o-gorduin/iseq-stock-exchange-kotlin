package finalyearproject.drawer.PortfolioTransactionHistory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import finalyearproject.drawer.Constants.Constants;
import finalyearproject.drawer.PortfolioTransactionHistory.TransHistoryView;
import finalyearproject.drawer.R;
import finalyearproject.drawer.SQLiteDatabase.MySQLiteHelper;
import finalyearproject.drawer.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 24/02/2015.
 */
public class PortfolioTransactionCircleFragment extends Fragment {

    FrameLayout mTransCircleContainer;
    private ArrayList<StockPurchase> mLastTenRecords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View android = inflater.inflate(R.layout.frag_trans_circle, container, false);
        mTransCircleContainer = (FrameLayout) android.findViewById(R.id.fl_trans_circle_container);
        MySQLiteHelper stock_individual = new MySQLiteHelper(getActivity());
        stock_individual.open();
        mLastTenRecords = new ArrayList<StockPurchase>();
        mLastTenRecords = stock_individual.getStockGroupEntry(Constants.BOTH);
        stock_individual.close();
        mLastTenRecords = getLastTenRecords();
        mTransCircleContainer.addView(new TransHistoryView(getActivity(), mLastTenRecords));
        return android;
    }


    public ArrayList<StockPurchase> getLastTenRecords() {
        for(int i = mLastTenRecords.size()-1;i >=10;i--){
            mLastTenRecords.remove(i);
        }
        Collections.sort(mLastTenRecords, new Comparator<StockPurchase>() {
            @Override
            public int compare(StockPurchase lhs, StockPurchase rhs) {
                return rhs.getNum()-lhs.getNum();
            }
        });
        return  mLastTenRecords;
    }



    public class CustomComparator implements Comparator<StockPurchase> {

        @Override
        public int compare(StockPurchase one, StockPurchase two) {
            Integer p1 = one.getNum();
            Integer p2 = two.getNum();
            if(p1>=p2){
                return p1;

            }else{
                return p2;
            }
        }
    }
}