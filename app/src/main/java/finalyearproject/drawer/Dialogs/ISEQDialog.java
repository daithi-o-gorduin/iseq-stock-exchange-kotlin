package finalyearproject.drawer.Dialogs;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import finalyearproject.drawer.Main.NavDrawerListAdapter;
import finalyearproject.drawer.Main.StockItemRow;
import finalyearproject.drawer.POJO.Quote;
import finalyearproject.drawer.R;
import finalyearproject.drawer.RecyclerViewAddOns.DividerItemDecoration;

/**
 * Created by Dvaid on 09/03/2015.
 */
public class ISEQDialog extends LinearLayout {

    SeekBar mBuySeekBar;
    TextView mStockHeading;
    ImageView mStockImage;
    EditText mNumberOfStocks;
    Context mContext;
    View mView;
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
            mView =  inflater.inflate(R.layout.stock_dialog, null);
        }

        mStockHeading = (TextView) mView.findViewById(R.id.tv_stock_dialog_heading);
        mStockDataList = (RecyclerView) mView.findViewById(R.id.rv_stock_data_list);
        mBuySeekBar = (SeekBar) mView.findViewById(R.id.sb_buy_stocks);
        mNumberOfStocks= (EditText) mView.findViewById(R.id.et_num_stocks);
        mStockImage = (ImageView) mView.findViewById(R.id.iv_stock_dialog_icon);

        mBuySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                  mNumberOfStocks.setText(Integer.toString(progress));
            }
        });

        mStockHeading.setText(mStockItem.getName());
        mStockImage.setImageResource(ISEQIcons.getResourceId(position,-1));


        ArrayList<String> test = new ArrayList<String>();
        test.add(stockItem.getName());
        test.add(stockItem.getsymbol());
        test.add("€"+Double.toString(stockItem.getLastTradePriceOnly()));
        test.add(stockItem.getChangeInPercent());
        test.add("€"+stockItem.getDaysLow());
        test.add("€"+stockItem.getDaysHigh());
        test.add("€"+stockItem.getYearsLow());
        test.add("€"+stockItem.getYearsHigh());
        test.add(stockItem.getVolume());
        test.add(stockItem.getLastTradeTime());

        /*test.add("HEY");
        test.add("HEY");
        test.add("HEY");
        test.add("HEY");
        test.add("HEY");
        test.add("HEY");
        test.add("HEY");
        test.add("HEY");


       /* test.add("HEY");test.add("HEY");
        test.add("HEY");
        test.add("HEY");
        test.add("HEY");*/

       /* ArrayAdapter adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, test);*/
        ISEQDialogRecyclerViewAdapter adapter = new ISEQDialogRecyclerViewAdapter(mContext,test);
        mStockDataList.setAdapter(adapter);
        //lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //mStockDataList.setAdapter(new ISEQDialofRecyclerViewAdapter());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mStockDataList.setLayoutManager(layoutManager);
        //mStockDataList.addItemDecoration(new DividerItemDecoration(mContext.getDrawable(R.drawable.divider)));

       // adapter.notifyDataSetChanged();
        addView(mView);
    }
}
