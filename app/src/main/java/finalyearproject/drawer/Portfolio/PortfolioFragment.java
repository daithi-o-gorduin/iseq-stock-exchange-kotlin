package finalyearproject.drawer.Portfolio;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import finalyearproject.drawer.ChartFragment.BarChartFragment;
import finalyearproject.drawer.ChartFragment.LineChartFragment;
import finalyearproject.drawer.Dialogs.PortfolioDialog;
import finalyearproject.drawer.EventBus.BusProvider;
import finalyearproject.drawer.EventBus.FavouritesEmptyEvent;
import finalyearproject.drawer.EventBus.ObserverEvent;
import finalyearproject.drawer.Main.MainActivity;
import finalyearproject.drawer.Main.WatchListEmptyFragment;
import finalyearproject.drawer.PortfolioTransactionHistory.PortfolioTransactionActivity;
import finalyearproject.drawer.R;

/**
 * Created by Dvaid on 06/02/2015.
 */
public class PortfolioFragment extends Fragment{

    TextView mPortfolioValueView;
    ImageButton mPortTransHistory, mPortChart , mPortSell;

    String portfolioStringValue,portfolioStringCost;
    TextView mPortValueInfo;
    RelativeLayout  mRelatveLayoutFragment;
    LinearLayout mPorfoilioValueBackground;
    Double mPortfolioCost,mPortfolioValue;
    //SeekBar mPortfolioSeekbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View android = inflater.inflate(R.layout.frag_portfolio, container, false);
        mPortfolioValueView = (TextView) android.findViewById(R.id.tv_portfolio_value);
        mPortTransHistory = (ImageButton) android.findViewById(R.id.ib_port_history);
        mPortSell = (ImageButton) android.findViewById(R.id.ib_port_sell);
        mPortChart = (ImageButton) android.findViewById(R.id.ib_port_chart);
        mPortValueInfo = (TextView) android.findViewById(R.id.tv_portfolio_info);
        mPorfoilioValueBackground = (LinearLayout) android.findViewById(R.id.ll_portfolio_value_background);

        mPortfolioCost = ((MainActivity) this.getActivity()).getPortfolioCost();
        mPortfolioValue = ((MainActivity) this.getActivity()).getPortfolioValue();

        if(mPortfolioValue > mPortfolioCost){
            mPorfoilioValueBackground.setBackgroundResource(R.drawable.portfolio_green);
        }else if (mPortfolioValue < mPortfolioCost){
            mPorfoilioValueBackground.setBackgroundResource(R.drawable.portfolio_red);
        }else{
            mPorfoilioValueBackground.setBackgroundResource(R.drawable.portfolio_orange);
        }

        portfolioStringCost = ("€" + Double.toString(mPortfolioCost));
        portfolioStringValue = ("€" + Double.toString(mPortfolioValue));
        mPortfolioValueView.setText(portfolioStringValue);

        portfolioStringValue = ("€" + Double.toString(((MainActivity) this.getActivity()).getPortfolioValue()));
        portfolioStringCost = ("€" + Double.toString(((MainActivity) this.getActivity()).getPortfolioCost()));
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

                        //builder.setTitle("Portfolio Value")

                        //.setMessage("Current Value: " + portfolioStringValue + "\n" + "BoughtValue: " + portfolioStringCost)
                                //.setBackgroundResource(R.drawable.dublin_watchlist)
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
                buttonPositiveInvolvement.setTextColor(getActivity().getResources().getColor(R.color.list_divider));



            }
        });


        /*mPortChart= new LineChartFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fl_chart_port,mPortChart);*/
       /* transaction.commit();*/

        /*mPortfolioSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 100){
                    Intent sell_stocks = new Intent(getActivity(), PortfolioSellActivity.class);
                    startActivity(sell_stocks);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/

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
                /*Intent port_chart = new Intent(getActivity(), PortfolioChartActivity.class);
                startActivity(port_chart);*/
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new LineChartFragment())
                        .commit();
            }
        });

        return android;
    }




    @Override
    public void onResume(){
        super.onResume();
        mPortfolioValue = ((MainActivity) this.getActivity()).getPortfolioValue();
        mPortfolioCost = ((MainActivity) this.getActivity()).getPortfolioCost();
        portfolioStringCost = ("€" + Double.toString(mPortfolioCost));
        portfolioStringValue = ("€" + Double.toString(mPortfolioValue));
        mPortfolioValueView.setText(portfolioStringValue);

    }







}
