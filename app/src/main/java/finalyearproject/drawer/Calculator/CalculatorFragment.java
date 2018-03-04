package finalyearproject.drawer.Calculator;

/**
 * Created by Dvaid on 09/01/2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import finalyearproject.drawer.Formatter.NumberFormatter;
import finalyearproject.drawer.R;




public class CalculatorFragment extends Fragment {


    EditText mPERatioEdit,mEstimateNextEdit,mEstimateCurrentEdit;
    TextView mCalculate, mResult;
    NumberFormatter formatter;

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

        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double priceToEarnings = Integer.parseInt(mPERatioEdit.getText().toString());
                    double estimateNext = Integer.parseInt(mEstimateNextEdit.getText().toString());
                    double estimateCurrent = Integer.parseInt(mEstimateCurrentEdit.getText().toString());

                    mResult.setText(Double.toString(formatter.getFoolsRatio(priceToEarnings,estimateNext,estimateCurrent)));

                }catch(Exception e){
                    e.printStackTrace();
                    mResult.setText("0.0");
                }
            }
        });

        return android;
    }}
