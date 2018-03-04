package finalyearproject.iseqStockExchange.PortfolioTransactionHistory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import finalyearproject.iseqStockExchange.Constants.Constants;
import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.SQLiteDatabase.MySQLiteHelper;
import finalyearproject.iseqStockExchange.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 24/02/2015.
 */
public class PortfolioTransactionCircleFragment extends Fragment {


    @InjectView(R.id.fl_trans_circle_container)FrameLayout mTransCircleContainer;
    private ArrayList<StockPurchase> mLastTenRecords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View android = inflater.inflate(R.layout.frag_trans_circle, container, false);
        ButterKnife.inject(this,android);
        MySQLiteHelper stock_individual = new MySQLiteHelper(getActivity());
        stock_individual.open();
        mLastTenRecords = new ArrayList<>();
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
            public int compare(StockPurchase leftHandSide, StockPurchase rightHandSide) {
                return rightHandSide.getNum()-leftHandSide.getNum();
            }
        });
        return  mLastTenRecords;
    }
}