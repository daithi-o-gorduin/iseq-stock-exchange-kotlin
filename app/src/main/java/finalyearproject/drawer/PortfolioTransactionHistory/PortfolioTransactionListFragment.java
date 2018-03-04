package finalyearproject.drawer.PortfolioTransactionHistory;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import finalyearproject.drawer.Constants.Constants;
import finalyearproject.drawer.ISEQ.RecyclerViewAdapter;
import finalyearproject.drawer.R;
import finalyearproject.drawer.RecyclerViewAddOns.DividerItemDecoration;
import finalyearproject.drawer.SQLiteDatabase.MySQLiteHelper;
import finalyearproject.drawer.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 23/02/2015.
 */
public class PortfolioTransactionListFragment extends Fragment {

    ArrayList<StockPurchase> mStockPurchases;
    RecyclerView mTransListRecycler;
    PortfolioTransactionListAdapter mTransListRecyclerAdapter;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View android = inflater.inflate(R.layout.frag_trans_list, container, false);

        MySQLiteHelper stock_individual = new MySQLiteHelper(getActivity());
        stock_individual.open();
        mStockPurchases = new ArrayList<StockPurchase>();
        mStockPurchases = stock_individual.getStockGroupEntry(Constants.BOTH);
        stock_individual.close();

        mTransListRecycler = (RecyclerView) android.findViewById(R.id.rv_trans_list);
        mTransListRecyclerAdapter = new PortfolioTransactionListAdapter(getActivity(),mStockPurchases);

        mTransListRecycler.setAdapter(mTransListRecyclerAdapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mTransListRecycler.setLayoutManager(layoutManager);
        mTransListRecycler.addItemDecoration(new DividerItemDecoration(getActivity().getDrawable(R.drawable.divider)));
        mTransListRecyclerAdapter.notifyDataSetChanged();

        return android;
    }
}
