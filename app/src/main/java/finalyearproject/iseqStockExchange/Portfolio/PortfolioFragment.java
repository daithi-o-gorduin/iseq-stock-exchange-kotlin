package finalyearproject.iseqStockExchange.Portfolio;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import finalyearproject.iseqStockExchange.ChartFragment.PortfolioLineChartFragment;
import finalyearproject.iseqStockExchange.Dialogs.PortfolioDialog;
import finalyearproject.iseqStockExchange.Formatter.NumberFormatter;
import finalyearproject.iseqStockExchange.Main.MainActivity;
import finalyearproject.iseqStockExchange.PortfolioTransactionHistory.PortfolioTransactionActivity;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 06/02/2015.
 */
public class PortfolioFragment extends Fragment{

    @InjectView(R.id.tv_portfolio_value)TextView mPortfolioValueView;
    @InjectView(R.id.tv_portfolio_info) TextView mPortValueInfo;
    @InjectView(R.id.ib_port_history) ImageButton mPortTransHistory;
    @InjectView(R.id.ib_port_chart) ImageButton mPortChart;
    @InjectView(R.id.ib_port_sell) ImageButton mPortSell;
    @InjectView(R.id.ll_portfolio_value_background) LinearLayout mPortfolioValueBackground;

    NumberFormatter mFormatter;
    String portfolioStringValue,portfolioStringCost;
    Double mPortfolioCost,mPortfolioValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View portfolioView = inflater.inflate(R.layout.frag_portfolio, container, false);
        ButterKnife.inject(this,portfolioView);

        mFormatter = new NumberFormatter();
        mPortfolioCost = mFormatter.round(((MainActivity) this.getActivity()).getPortfolioCost(), 3);
        mPortfolioValue = mFormatter.round(((MainActivity) this.getActivity()).getPortfolioValue(), 3);

        setPortfolioBackground();

        portfolioStringCost = ("€" + Double.toString(mPortfolioCost));
        portfolioStringValue = ("€" + Double.toString(mPortfolioValue));
        try {
            mPortfolioValueView.setText(portfolioStringValue.substring(0, 8));
        }catch(Exception e){
            e.printStackTrace();
            mPortfolioValueView.setText(portfolioStringValue);
        }


        mPortfolioValueView.setText(portfolioStringValue);

        mPortTransHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trans_history = new Intent(getActivity(), PortfolioTransactionActivity.class);
                startActivity(trans_history);
            }
        });

        mPortValueInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setView(new PortfolioDialog(getActivity(),mPortfolioCost,mPortfolioValue))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();


                            }
                        });

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                final Button buttonPositiveInvolvement = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                buttonPositiveInvolvement.setTextColor(getActivity().getResources().getColor(R.color.app_orange));



            }
        });



        mPortSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sell_stocks = new Intent(getActivity(), PortfolioSellActivity.class);
                startActivity(sell_stocks);
            }

        });

        mPortChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new PortfolioLineChartFragment())
                        .commit();
            }
        });

        return portfolioView;
    }




    @Override
    public void onResume(){
        super.onResume();
        mPortfolioValue = ((MainActivity) this.getActivity()).getPortfolioValue();
        mPortfolioCost = ((MainActivity) this.getActivity()).getPortfolioCost();
        portfolioStringCost = ("€" + Double.toString(mPortfolioCost));
        portfolioStringValue = ("€" + Double.toString(mPortfolioValue));
        try {
            mPortfolioValueView.setText(portfolioStringValue.substring(0, 8));
        }catch(Exception e){
            e.printStackTrace();
            mPortfolioValueView.setText(portfolioStringValue);
        }
        setPortfolioBackground();

    }


    public void setPortfolioBackground(){
        if(mPortfolioValue > mPortfolioCost){
            mPortfolioValueBackground.setBackgroundResource(R.drawable.portfolio_green);
        }else if (mPortfolioValue < mPortfolioCost){
            mPortfolioValueBackground.setBackgroundResource(R.drawable.portfolio_red);
        }else{
            mPortfolioValueBackground.setBackgroundResource(R.drawable.portfolio_orange);
        }

    }





}
