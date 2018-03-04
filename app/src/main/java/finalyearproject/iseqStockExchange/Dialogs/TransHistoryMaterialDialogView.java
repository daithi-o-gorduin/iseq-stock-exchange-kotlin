package finalyearproject.iseqStockExchange.Dialogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import finalyearproject.iseqStockExchange.Formatter.NumberFormatter;
import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 20/02/2015.
 */
public class TransHistoryMaterialDialogView extends FrameLayout {

    Context mContext;
    View mHistoryDialogView;
    ArrayList<String> mRecyclerViewData;
    TextView mTransDialogHeading,mTransDialogChangePercent;
    ImageView mTransDialogIcon,mTransDialogChangeIndicator;
    StockPurchase dataSource;
    private TypedArray ISEQIcons;
    int mPosition;

    public TransHistoryMaterialDialogView(Context context,StockPurchase dataSource, int position) {
        super(context);
        this.mContext = context;
        this.dataSource = dataSource;
        mRecyclerViewData = new ArrayList<>();
        addData(dataSource);
        this.ISEQIcons = context.getResources().obtainTypedArray(R.array.iseq_icons);
        this.mPosition = position;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null){
            mHistoryDialogView =  inflater.inflate(R.layout.custom_dialog_view, null);
        }
        mTransDialogHeading =(TextView) mHistoryDialogView.findViewById(R.id.tv_custom_dialog_heading);
        mTransDialogChangePercent =(TextView) mHistoryDialogView.findViewById(R.id.tv_trans_dialog_change);
        mTransDialogIcon =(ImageView) mHistoryDialogView.findViewById(R.id.iv_trans_dialog_icon);
        mTransDialogChangeIndicator =(ImageView) mHistoryDialogView.findViewById(R.id.iv_trans_dialog_change_indicator);

        mTransDialogHeading.setText(dataSource.getName());
        Picasso.with(mContext).load(ISEQIcons.getResourceId(dataSource.getId(),0)).into(mTransDialogIcon);


        changeIndicator();

        NumberFormatter formatter = new NumberFormatter();

        mTransDialogChangePercent.setText(formatter.percentChange(dataSource.getTotalCost(),dataSource.getTotalValue()));


        RecyclerView recyclerView = (RecyclerView) mHistoryDialogView.findViewById(R.id.rv_trans_data_list);
        recyclerView.setAdapter(new TransHistoryMaterialDialogRecyclerAdapter(mRecyclerViewData));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        addView(mHistoryDialogView);

    }


    public void addData(StockPurchase dataSource){
        mRecyclerViewData.add(dataSource.getTickerId());
        mRecyclerViewData.add(dataSource.getDate());
        mRecyclerViewData.add(Integer.toString(dataSource.getNum()));
        mRecyclerViewData.add(Double.toString(dataSource.getTotalCost()));
        mRecyclerViewData.add(Double.toString(dataSource.getTotalValue()));
        mRecyclerViewData.add(Double.toString(dataSource.getCurValue()));

    }

    public void changeIndicator(){
        if(dataSource.getTotalValue()> dataSource.getTotalCost()){
            mTransDialogChangeIndicator.setImageResource(R.drawable.change_pos);
            mTransDialogChangePercent.setTextColor(getResources().getColor(R.color.change_pos));
        }else if (dataSource.getTotalValue() < dataSource.getTotalCost()){
            mTransDialogChangeIndicator.setImageResource(R.drawable.change_neg);
            mTransDialogChangePercent.setTextColor(getResources().getColor(R.color.change_neg));
        }else{
            mTransDialogChangeIndicator.setImageResource(R.drawable.change_neu);
            mTransDialogChangePercent.setTextColor(getResources().getColor(R.color.change_neu));
        }
    }
}
