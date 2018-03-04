package finalyearproject.iseqStockExchange.PortfolioTransactionHistory;

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

import butterknife.ButterKnife;
import butterknife.InjectView;
import finalyearproject.iseqStockExchange.Constants.Constants;
import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.RecyclerViewAddOns.DividerItemDecoration;
import finalyearproject.iseqStockExchange.SQLiteDatabase.MySQLiteHelper;
import finalyearproject.iseqStockExchange.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 23/02/2015.
 */
public class PortfolioTransactionListFragment extends Fragment {


    @InjectView(R.id.rv_trans_list) RecyclerView mTransListRecycler;
    ArrayList<StockPurchase> mStockPurchases;
    PortfolioTransactionListAdapter mTransListRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View transListView = inflater.inflate(R.layout.frag_trans_list, container, false);

        ButterKnife.inject(this, transListView);

        MySQLiteHelper stock_individual = new MySQLiteHelper(getActivity());
        stock_individual.open();
        mStockPurchases = new ArrayList<>();
        mStockPurchases = stock_individual.getStockGroupEntry(Constants.BOTH);
        stock_individual.close();


        mTransListRecyclerAdapter = new PortfolioTransactionListAdapter(getActivity(),mStockPurchases);

        mTransListRecycler.setAdapter(mTransListRecyclerAdapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mTransListRecycler.setLayoutManager(layoutManager);
        mTransListRecycler.addItemDecoration(new DividerItemDecoration(getActivity().getDrawable(R.drawable.divider)));
        mTransListRecyclerAdapter.notifyDataSetChanged();

        return transListView;
    }
}
