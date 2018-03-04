package finalyearproject.drawer.Dialogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import finalyearproject.drawer.Formatter.NumberFormatter;
import finalyearproject.drawer.R;
import finalyearproject.drawer.RecyclerViewAddOns.DividerItemDecoration;
import finalyearproject.drawer.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 20/02/2015.
 */
public class TransHistoryMaterialDialogView extends FrameLayout {

    Context mContext;
    View view;
    ArrayList<String> mRecyclerViewData;
   // String[] mListText;
    TextView mTransDialogHeading,mTransDialogChangePercent;
    ImageView mTransDialogIcon,mTransDialogChangeIndicator;
    StockPurchase dataSource;
    private TypedArray ISEQIcons;
    int mPosition;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TransHistoryMaterialDialogView(Context context,StockPurchase dataSource, int position) {
        super(context);
        this.mContext = context;
        this.dataSource = dataSource;
        mRecyclerViewData = new ArrayList<String>();
        addData(dataSource);
        this.ISEQIcons = context.getResources().obtainTypedArray(R.array.iseq_icons);
        this.mPosition = position;


       // mListText = new String[]{"Ticker ID: ","Number of Stocks Bought: ","Cost of Purchase: ","Current Value: "};
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null){
            view =  inflater.inflate(R.layout.custom_dialog_view, null);
        }
        mTransDialogHeading =(TextView) view.findViewById(R.id.tv_custom_dialog_heading);
        mTransDialogChangePercent =(TextView) view.findViewById(R.id.tv_trans_dialog_change);
        mTransDialogIcon =(ImageView) view.findViewById(R.id.iv_trans_dialog_icon);
        mTransDialogChangeIndicator =(ImageView) view.findViewById(R.id.iv_trans_dialog_change_indicator);

        mTransDialogHeading.setText(dataSource.getName());
        Picasso.with(mContext).load(ISEQIcons.getResourceId(dataSource.getId(),0)).into(mTransDialogIcon);


        changeIndicator();

        NumberFormatter formatter = new NumberFormatter();

        mTransDialogChangePercent.setText(formatter.percentChange(dataSource.getTotalCost(),dataSource.getTotalValue()));



      /*  for(int i = 0;i<mListText.length;i++) {
            //mListTextView.setText(mListText[i]+mRecyclerViewData.get(i)+"\n");
        }*/

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_trans_data_list);
      //
        recyclerView.setAdapter(new TransHistoryMaterialDialogRecyclerAdapter(mRecyclerViewData));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
       // recyclerView.addItemDecoration(new DividerItemDecoration(mContext.getDrawable(R.drawable.divider)));

        addView(view);

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
