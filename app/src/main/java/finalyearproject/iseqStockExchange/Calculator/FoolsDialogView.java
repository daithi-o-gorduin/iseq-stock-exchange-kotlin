package finalyearproject.iseqStockExchange.Calculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import finalyearproject.iseqStockExchange.Constants.Constants;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 25/03/2015.
 */



/**
 * Dialog that pops up when hitting fools ratio revealed score
 */
public class FoolsDialogView extends LinearLayout {

    private TextView mFoolsScore, mFoolsExplanation , mNumberView;
    private View mView;
    private TextView mReveal;
    private double mNumber;

    public FoolsDialogView(Context context, int color, TextView reveal, double number) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mView = inflater.inflate(R.layout.fools_dialog, null);
        }
        this.mReveal = reveal;
        this.mNumber = number;

        mFoolsScore = (TextView) mView.findViewById(R.id.tv_fools_dialog_score);
        mFoolsExplanation = (TextView) mView.findViewById(R.id.tv_fools_dialog_explanation);
        mNumberView= (TextView) mView.findViewById(R.id.tv_fools_dialog_number);



        mNumberView.setTextColor(color);
        mNumberView.setText(Double.toString(mNumber));
        mFoolsScore.setText(mReveal.getText());
        mFoolsScore.setTextColor(color);
        mFoolsExplanation.setText(Constants.myFoolsMapping.get(mReveal.getText()));//Mapping between scores and meanings in constants class
        mFoolsExplanation.setTextColor(color);

        addView(mView);

    }
}
