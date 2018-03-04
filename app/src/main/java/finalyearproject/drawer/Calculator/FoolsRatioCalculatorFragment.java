package finalyearproject.drawer.Calculator;

/**
 * Created by Dvaid on 09/01/2015.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import finalyearproject.drawer.Animation.CircularReveal;
import finalyearproject.drawer.Dialogs.TransHistoryMaterialDialogView;
import finalyearproject.drawer.Formatter.NumberFormatter;
import finalyearproject.drawer.R;




public class FoolsRatioCalculatorFragment extends Fragment {


    EditText mPERatioEdit,mEstimateNextEdit,mEstimateCurrentEdit;
    TextView mCalculate, mResult,mReveal;
    NumberFormatter formatter;
    CircularReveal mCircularReveal;
    double mFoolsRatio;
    int mCurrentColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        formatter = new NumberFormatter();

        View android = inflater.inflate(R.layout.frag_fools_ratio, container, false);
        mPERatioEdit =  (EditText) android.findViewById(R.id.et_fools_pe);
        mEstimateNextEdit = (EditText) android.findViewById(R.id.et_fools_estimate_next);
        mEstimateCurrentEdit =(EditText) android.findViewById(R.id.et_fools_estimate_current);
        mCalculate = (TextView) android.findViewById(R.id.tv_fools_calculate);
        mResult =  (TextView) android.findViewById(R.id.tv_fools_result);
        mReveal = (TextView) android.findViewById(R.id.tv_reveal);
        mCircularReveal = new CircularReveal(mReveal,getActivity());//http://ptrprograms.blogspot.ie/2014/08/getting-started-with-android-l.html

        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double priceToEarnings = Integer.parseInt(mPERatioEdit.getText().toString());
                    double estimateNext = Integer.parseInt(mEstimateNextEdit.getText().toString());
                    double estimateCurrent = Integer.parseInt(mEstimateCurrentEdit.getText().toString());

                    mFoolsRatio = formatter.getFoolsRatio(priceToEarnings,estimateNext,estimateCurrent);
                    DecimalFormat roundDecimal = new DecimalFormat("##.00");
                    mResult.setText(roundDecimal.format(mFoolsRatio));
                    mCircularReveal.revealImageCircular(getResultAsFoolsIndicator(mFoolsRatio));


                }catch(Exception e){
                    e.printStackTrace();
                    mResult.setText("0.0");
                }
            }
        });

        mReveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


                builder.setView(new FoolsDialogView(getActivity(),mCurrentColor,mReveal,Double.parseDouble(mResult.getText().toString())))

                        //.setBackgroundResource(R.drawable.dublin_watchlist)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();

                            }
                        });


                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                final Button buttonPositiveInvolvement = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                buttonPositiveInvolvement.setTextColor(mCurrentColor);

            }
        });

        return android;
    }

    public int getResultAsFoolsIndicator(double result){

        if(result <= 0.5){
            mReveal.setText("1");
            this.mCurrentColor = getResources().getColor(R.color.change_pos);
            return R.drawable.mycircle_green;
        }else if(result <= 0.65 ){
            this.mCurrentColor = getResources().getColor(R.color.change_pos);
            mReveal.setText("2");
            return R.drawable.mycircle_green_transparent;
        }else if(result <= 1.00 ){
            this.mCurrentColor = getResources().getColor(R.color.list_divider);
            mReveal.setText("3");
            return R.drawable.mycircle;
        }else if(result <= 1.30 ){
            this.mCurrentColor = getResources().getColor(R.color.change_neg);
            mReveal.setText("4");
            return R.drawable.mycircle_red_transparent_two;
        }else if(result <= 1.70 ){
            this.mCurrentColor = getResources().getColor(R.color.change_neg);
            mReveal.setText("5");
            return R.drawable.mycircle_red_transparent;
        }else {
            this.mCurrentColor = getResources().getColor(R.color.change_neg);
            mReveal.setText("6");
            return R.drawable.mycircle_red;
        }


    }


}

