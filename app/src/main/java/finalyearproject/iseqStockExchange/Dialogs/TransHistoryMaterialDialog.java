package finalyearproject.iseqStockExchange.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;

import java.util.ArrayList;

import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 19/02/2015.
 */
public class TransHistoryMaterialDialog {

    Context mContext;
    ArrayList<StockPurchase> mRecords;
    int mPosition;

    public TransHistoryMaterialDialog(Context context,ArrayList<StockPurchase> records, int position){
        this.mContext = context;
        this.mRecords = records;
        this.mPosition = position;
    }

    public void build() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(new TransHistoryMaterialDialogView(mContext, mRecords.get(mPosition),mPosition))
                      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int id) {
                                               dialog.dismiss();

                                           }
                                       });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final Button buttonPositiveInvolvement = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonPositiveInvolvement.setTextColor(mContext.getResources().getColor(R.color.app_orange));
    }
}
