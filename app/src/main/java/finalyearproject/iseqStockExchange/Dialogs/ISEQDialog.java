package finalyearproject.iseqStockExchange.Dialogs;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import finalyearproject.iseqStockExchange.CustomListeners.CustomOnSeekBarChangeListener;
import finalyearproject.iseqStockExchange.POJO.Quote;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 09/03/2015.
 */
public class ISEQDialog extends LinearLayout {

    SeekBar mBuySeekBar;
    TextView mStockHeading;
    ImageView mStockImage;
    EditText mNumberOfStocks;
    Context mContext;
    View mISEQDialogView;
    RecyclerView mStockDataList;
    private TypedArray ISEQIcons;
    Quote mStockItem;
    int mPosition;


    public ISEQDialog(Context context, Quote stockItem, int position) {
        super(context);
        this.mContext = context;
        this.mStockItem = stockItem;
        this.ISEQIcons = context.getResources().obtainTypedArray(R.array.iseq_icons);
        this.mPosition = position;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null){
            mISEQDialogView =  inflater.inflate(R.layout.stock_dialog, null);
        }

        mStockHeading = (TextView) mISEQDialogView.findViewById(R.id.tv_stock_dialog_heading);
        mStockDataList = (RecyclerView) mISEQDialogView.findViewById(R.id.rv_stock_data_list);
        mBuySeekBar = (SeekBar) mISEQDialogView.findViewById(R.id.sb_buy_stocks);
        mNumberOfStocks= (EditText) mISEQDialogView.findViewById(R.id.et_num_stocks);
        mStockImage = (ImageView) mISEQDialogView.findViewById(R.id.iv_stock_dialog_icon);

        mBuySeekBar.setOnSeekBarChangeListener(new CustomOnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                  mNumberOfStocks.setText(Integer.toString(progress));
            }
        });

        mStockHeading.setText(mStockItem.getName());
        mStockImage.setImageResource(ISEQIcons.getResourceId(position,-1));


        ArrayList<String> data = new ArrayList<String>();
        data.add(stockItem.getName());
        data.add(stockItem.getsymbol());
        data.add("€"+Double.toString(stockItem.getLastTradePriceOnly()));
        data.add(stockItem.getChangeInPercent());
        data.add("€"+stockItem.getDaysLow());
        data.add("€"+stockItem.getDaysHigh());
        data.add("€"+stockItem.getYearsLow());
        data.add("€"+stockItem.getYearsHigh());
        data.add(stockItem.getVolume());
        data.add(stockItem.getLastTradeTime());


        ISEQDialogRecyclerViewAdapter adapter = new ISEQDialogRecyclerViewAdapter(mContext,data);
        mStockDataList.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mStockDataList.setLayoutManager(layoutManager);

        addView(mISEQDialogView);
    }
}
