package finalyearproject.iseqStockExchange.Dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import finalyearproject.iseqStockExchange.Formatter.NumberFormatter;
import finalyearproject.iseqStockExchange.R;

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

        mCostView.setText("Purchase Cost: €" + formatter.round(mCost,3));
        mValueView.setText("Current Value: €" + formatter.round(mValue,3));
        mChangePercent.setText("Change Percentage: " + formatter.percentChange(mCost,mValue));

        addView(mView);
    }
}

