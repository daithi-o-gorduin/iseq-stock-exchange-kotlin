package finalyearproject.drawer.Dialogs;

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

import finalyearproject.drawer.Formatter.NumberFormatter;
import finalyearproject.drawer.POJO.Quote;
import finalyearproject.drawer.R;

/**
 * Created by Dvaid on 08/04/2015.
 */
public class PortfolioDialog extends LinearLayout {


    View mView;
    double mCost,mValue;
    Context mContext;
    TextView mCostView,mValueView,mChangePercent;

    public PortfolioDialog(Context context, double cost, double value) {
        super(context);

        this.mContext = context;
        this.mCost = cost;
        this.mValue = value;

        NumberFormatter formatter = new NumberFormatter();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null){
            mView =  inflater.inflate(R.layout.dialog_portfolio_value, null);
        }
        mCostView = (TextView) mView.findViewById(R.id.tv_port_purchased_val);
        mValueView = (TextView) mView.findViewById(R.id.tv_port_current_val);
        mChangePercent = (TextView) mView.findViewById(R.id.tv_port_change_percent);

        mCostView.setText("Purchase Cost: €" + mCost);
        mValueView.setText("Current Value: €" + mValue);
        mChangePercent.setText("Change Percentage: " + formatter.percentChange(mCost,mValue));

        addView(mView);
    }
}

