package finalyearproject.drawer.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import finalyearproject.drawer.R;
import finalyearproject.drawer.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 19/02/2015.
 */
public class TransHistoryMaterialDialog {

    Context mContext;
    ArrayList<StockPurchase> mLastTenRecords;
    int mPosition;

    public TransHistoryMaterialDialog(Context context,ArrayList<StockPurchase> lastTenRecords, int position){
        this.mContext = context;
        this.mLastTenRecords = lastTenRecords;
        this.mPosition = position;
    }

    public void build() {

       // ListView contentView = new ListView(mContext);

        String[] stringArray = new String[] { mLastTenRecords.get(mPosition).getTickerId(),
                Integer.toString(mLastTenRecords.get(mPosition).getNum()),Double.toString(mLastTenRecords.get(mPosition).getTotalCost()),
                Double.toString(mLastTenRecords.get(mPosition).getTotalValue()) };

        //ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
        //contentView.setAdapter(modeAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);


                               builder.setView(new TransHistoryMaterialDialogView(mContext, mLastTenRecords.get(mPosition),mPosition))
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
        buttonPositiveInvolvement.setTextColor(mContext.getResources().getColor(R.color.list_divider));


        }
    }
