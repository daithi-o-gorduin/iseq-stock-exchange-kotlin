package finalyearproject.iseqStockExchange.ISEQ;

/**
 * Created by Dvaid on 24/11/2014.
 */

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import com.google.inject.Inject;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import finalyearproject.iseqStockExchange.Constants.Constants;
import finalyearproject.iseqStockExchange.Dialogs.ISEQDialog;
import finalyearproject.iseqStockExchange.EventBus.BusProvider;
import finalyearproject.iseqStockExchange.EventBus.FavouritesEmptyEvent;
import finalyearproject.iseqStockExchange.EventBus.FavouritesEvent;
import finalyearproject.iseqStockExchange.EventBus.ObserverEvent;
import finalyearproject.iseqStockExchange.Formatter.NumberFormatter;
import finalyearproject.iseqStockExchange.Main.StockItemRow;
import finalyearproject.iseqStockExchange.POJO.Quote;
import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.SQLiteDatabase.MySQLiteHelper;
import finalyearproject.iseqStockExchange.SharedPreferences.SharedPref;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListItemViewHolder> {


    private List<StockItemRow> stockItems;
    private List<Quote> quoteItems;
    private TypedArray ISEQIcons;
    private Context mContext;
    private ArrayList<Integer> favourites;
    private SharedPref pref;
    private TreeMap<Integer,Integer> fav_res = new TreeMap<Integer,Integer>();
    private boolean isWatchList;
    LayoutInflater inflater;
    EditText mNumberStocks;





    RecyclerViewAdapter(Context context, List modelData, boolean isWatchList,List quoteData) {
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.stockItems = modelData;
        this.quoteItems = quoteData;
        this.ISEQIcons = context.getResources().obtainTypedArray(R.array.iseq_icons);
        this.mContext = context;
        this.favourites = new ArrayList<Integer>();
        this.pref = new SharedPref(mContext);
        this.favourites = pref.loadFavSavedPreferences(Constants.FAVOURITES);
        this.isWatchList = isWatchList;

    }


    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_row, viewGroup, false);
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ListItemViewHolder(itemView);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ListItemViewHolder viewHolder, int position) {
        final StockItemRow stockModel = stockItems.get(position);
        viewHolder.txtViewSymbol.setText(stockModel.getSymbol());
        viewHolder.txtViewChange.setText(stockModel.getChange());
        viewHolder.txtViewPrice.setText("€" +   Double.toString(stockModel.getLastTradePrice()));
        if(isWatchList == true){
           Picasso.with(mContext).load(ISEQIcons.getResourceId(favourites.get(position), -1)).into(viewHolder.icon);
        }else {
           Picasso.with(mContext).load(ISEQIcons.getResourceId(position, -1)).into(viewHolder.icon);
        }

        String temp = stockModel.getChange();
        if(Double.parseDouble(temp.substring(0,temp.length()-1))>0){
            viewHolder.change.setImageResource(R.drawable.change_pos);
            viewHolder.txtViewChange.setTextColor(mContext.getResources().getColor(R.color.change_pos));
            viewHolder.txtViewSymbol.setTextColor(mContext.getResources().getColor(R.color.change_pos));
            viewHolder.txtViewPrice.setTextColor(mContext.getResources().getColor(R.color.change_pos));

        }else if (Double.parseDouble(temp.substring(0,temp.length()-1))<0){
            viewHolder.change.setImageResource(R.drawable.change_neg);
            viewHolder.txtViewChange.setTextColor(mContext.getResources().getColor(R.color.change_neg));
            viewHolder.txtViewSymbol.setTextColor(mContext.getResources().getColor(R.color.change_neg));
            viewHolder.txtViewPrice.setTextColor(mContext.getResources().getColor(R.color.change_neg));
        }else if(Double.parseDouble(temp.substring(0,temp.length()-1))==0){
            viewHolder.change.setImageResource(R.drawable.change_neu);
            viewHolder.txtViewChange.setTextColor(mContext.getResources().getColor(R.color.change_neu));
            viewHolder.txtViewSymbol.setTextColor(mContext.getResources().getColor(R.color.change_neu));
            viewHolder.txtViewPrice.setTextColor(mContext.getResources().getColor(R.color.change_neu));
        }

        if(isWatchList==true){
            fav_res.put(position,R.drawable.star_pressed);
        }else {
            int test = R.drawable.star;
            for (int i = 0; i < favourites.size(); i++) {
                if (position == favourites.get(i)) {
                    test = R.drawable.star_pressed;
                    break;
                }
            }
            fav_res.put(position,test);
        }


        viewHolder.star.setImageResource(fav_res.get(position));


        viewHolder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isWatchList == true) {
                  removeAt(viewHolder.getPosition());
                  favourites.remove(viewHolder.getPosition());

                    pref.saveFavPreferences(favourites,Constants.FAVOURITES);
                    if(favourites.size()==0){
                        BusProvider.getInstance().post(new FavouritesEmptyEvent());
                    }
                } else if (isWatchList == false) {
                    if (fav_res.get(viewHolder.getPosition()) == R.drawable.star) {


                        HashSet tempHash = new HashSet();
                        tempHash.addAll(favourites);
                        tempHash.add(viewHolder.getPosition());
                        favourites.clear();
                        favourites.addAll(tempHash);

                        Collections.sort(favourites);
                        pref.saveFavPreferences(favourites, Constants.FAVOURITES);
                        fav_res.remove(viewHolder.getPosition());
                        fav_res.put(viewHolder.getPosition(), R.drawable.star_pressed);


                    } else if (fav_res.get(viewHolder.getPosition()) == R.drawable.star_pressed) {


                        for (int i = 0; i < favourites.size(); i++) {
                            if (viewHolder.getPosition() == favourites.get(i)) {
                                favourites.remove(i);
                            }
                        }
                        pref.saveFavPreferences(favourites, Constants.FAVOURITES);
                        fav_res.remove(viewHolder.getPosition());
                        fav_res.put(viewHolder.getPosition(), R.drawable.star);


                    }

                    BusProvider.getInstance().post(new FavouritesEvent(favourites));
                    viewHolder.star.setImageResource(fav_res.get(viewHolder.getPosition()));


                }
            }
        });


       viewHolder.extra.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final int position;
               if(isWatchList == true){
                   position = favourites.get(viewHolder.getPosition());
               }else {
                   position = viewHolder.getPosition();
               }

               Quote tempQuoteItem = quoteItems.get(position);
               final View iseqDialog = new ISEQDialog(mContext,tempQuoteItem,position);
               mNumberStocks = (EditText) iseqDialog.findViewById(R.id.et_num_stocks);


               AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                       builder.setView(iseqDialog)

                       .setPositiveButton("BUY", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int id) {


                               int numOfStocksBought = Integer.parseInt(mNumberStocks.getText().toString());
                               if (numOfStocksBought != 0) {
                                   double indCost = 0.0, totCost = 0.0;

                                   Calendar c = Calendar.getInstance();
                                   SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                   String formattedDate = df.format(c.getTime());
                                   NumberFormatter formatter = new NumberFormatter();

                                   MySQLiteHelper stock_group = new MySQLiteHelper(mContext);
                                   stock_group.open();
                                   indCost = stockItems.get(viewHolder.getPosition()).getLastTradePrice();
                                   totCost = formatter.round(stockItems.get(viewHolder.getPosition()).getLastTradePrice()*numOfStocksBought,3);
                                   stock_group.createStockItemEntry(position, stockItems.get(viewHolder.getPosition()).getSymbol(), stockItems.get(viewHolder.getPosition()).getName(), numOfStocksBought, indCost, totCost, totCost, formattedDate, Constants.BUY);
                                   BusProvider.getInstance().post(new ObserverEvent());
                                   stock_group.close();


                               }

                           }
                       })
                               .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {

                                   }
                               });




               final AlertDialog alertDialog = builder.create();
               mNumberStocks.addTextChangedListener(new TextWatcher() {

                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) {
                   }

                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                   }

                   @Override
                   public void afterTextChanged(Editable s) {
                       try {
                           if (Integer.parseInt(s.toString()) == 0) {
                               alertDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
                           } else {
                               alertDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
                           }
                       }catch(NumberFormatException e){
                           e.printStackTrace();
                           alertDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
                       }
                   }
               });

               alertDialog.show();
               setDialogSpecifics(alertDialog);

           }
       });


    }

    public void setDialogSpecifics(AlertDialog alertDialog){
        final Button buttonPositiveInvolvement = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button buttonNegativeInvolvement = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        buttonPositiveInvolvement.setTextColor(mContext.getResources().getColor(R.color.app_orange));
        buttonNegativeInvolvement.setTextColor(mContext.getResources().getColor(R.color.app_orange));
        buttonPositiveInvolvement.setEnabled(false);
    }


    public void removeAt(int position){
        stockItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,stockItems.size());
    }



    @Override
    public int getItemCount() {
        return stockItems.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewSymbol,txtViewPrice,txtViewChange;
        ImageView icon,change,star;
        ImageView extra;


        public ListItemViewHolder(final View itemView) {
            super(itemView);
            txtViewSymbol= (TextView) itemView.findViewById(R.id.tv_ticker);
            txtViewPrice = (TextView) itemView.findViewById(R.id.tv_last_trade_price);
            txtViewChange = (TextView) itemView.findViewById(R.id.tv_change);
            icon = (ImageView) itemView.findViewById(R.id.logo);
            change = (ImageView) itemView.findViewById(R.id.change_indicator);
            extra = (ImageView) itemView.findViewById(R.id.extra);
            star= (ImageView) itemView.findViewById(R.id.star);

        }
    }




}

