package finalyearproject.iseqStockExchange.Portfolio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import finalyearproject.iseqStockExchange.Dialogs.TransHistoryMaterialDialogView;
import finalyearproject.iseqStockExchange.EventBus.BusProvider;
import finalyearproject.iseqStockExchange.EventBus.ObserverEvent;
import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.SQLiteDatabase.MySQLiteHelper;
import finalyearproject.iseqStockExchange.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 31/03/2015.
 */
public class PortfolioSellView extends View {

    Context mContext;
    Integer mRadius = 70;
    Canvas mCanvas;
    TypedArray mISEQIcons;
    Set<Integer> mPurchasedStockIds = new TreeSet<Integer>(); // order is preserved
    ArrayList<Integer> xPositions = new ArrayList<Integer>();
    ArrayList<Integer> yPositions = new ArrayList<Integer>();
    ArrayList<StockPurchase> mStocksPurchased;

    public PortfolioSellView(Context context, Set<Integer> purchasedStockIds, ArrayList<StockPurchase> stocksPurchased) {
        super(context);
        this.mContext = context;
        this.mISEQIcons = context.getResources().obtainTypedArray(R.array.iseq_icons);
        this.mPurchasedStockIds = purchasedStockIds;
        this.mStocksPurchased = stocksPurchased;

        int spaceCountCol = 0,spaceCountRow =0;

        for(int i = 1;i<=15;i++) {

            spaceCountCol += 20;
            spaceCountRow = 0;
            for (int j = 1; j <= 4; j++) {
                xPositions.add(2 * (mRadius * j)+spaceCountRow);
                yPositions.add(2 * (mRadius * i)+spaceCountCol);
                spaceCountRow +=20;
            }
        }



        this.setOnTouchListener(new OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float xTouchPosition = event.getX();
                float yTouchPosition = event.getY();
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    for (int i = 0; i < 60; i++) {
                        if ((xTouchPosition <= xPositions.get(i) + mRadius && xTouchPosition >= xPositions.get(i) - mRadius)
                                && (yTouchPosition <= yPositions.get(i) + mRadius && yTouchPosition >= yPositions.get(i) - mRadius)) {
                            for (Iterator<Integer> it = mPurchasedStockIds.iterator(); it.hasNext(); ) {
                                if(i == it.next()){

                                    StockPurchase clickedStockPurchase = null;
                                    for(int j = 0; j< mStocksPurchased.size(); j++){
                                        if(mStocksPurchased.get(j).getId() == i){
                                            clickedStockPurchase = mStocksPurchased.get(j);
                                        }
                                    }
                                    final StockPurchase temp = clickedStockPurchase;

                                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);


                                    builder.setView(new TransHistoryMaterialDialogView(mContext,clickedStockPurchase ,i))

                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();

                                                }
                                            }
                                            ).setNegativeButton("SELL", new DialogInterface.OnClickListener(){

                                                @Override
                                                 public void onClick(DialogInterface dialog, int which) {
                                                    MySQLiteHelper stock_purchases = new MySQLiteHelper(mContext);
                                                    stock_purchases.open();
                                                    stock_purchases.sellStockItem(temp);
                                                    stock_purchases.close();
                                                    for(Iterator<Integer> it = mPurchasedStockIds.iterator(); it.hasNext();) {
                                                        if(temp.getId()== it.next()){

                                                         it.remove();

                                                        }
                                                    }
                                                    BusProvider.getInstance().post(new ObserverEvent());

                                                    invalidate();
                                                  }
                                            });




                                    final AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    final Button buttonPositiveInvolvement = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                    final Button buttonNegativeInvolvement = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                                    buttonPositiveInvolvement.setTextColor(mContext.getResources().getColor(R.color.app_orange));
                                    buttonNegativeInvolvement.setTextColor(mContext.getResources().getColor(R.color.change_neg));

                                }
                            }

                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.app_orange));
        Bitmap image;
        int count = 0;

        for(int i = 1;i<=15;i++) {
            for (int j = 0; j < 4; j++) {
                for (Iterator<Integer> it = mPurchasedStockIds.iterator(); it.hasNext(); ) {
                    if(count == it.next()){
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(2);
                        paint.setColor(getResources().getColor(R.color.change_pos));
                        break;
                    }else{

                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(1);
                        paint.setColor(getResources().getColor(R.color.app_orange));

                    }
                }
                canvas.drawCircle(xPositions.get(j), yPositions.get(i*4-1), mRadius, paint);
                image = BitmapFactory.decodeResource(this.getResources(),
                        mISEQIcons.getResourceId(count, -1));
                image = Bitmap.createScaledBitmap(image, 100, 100, false);
                canvas.drawBitmap(image,(xPositions.get(j)-(image.getWidth()/2)), ( yPositions.get(i*4-1)-(image.getHeight()/2)),  paint);
                count++;

            }
        }
    }

}
