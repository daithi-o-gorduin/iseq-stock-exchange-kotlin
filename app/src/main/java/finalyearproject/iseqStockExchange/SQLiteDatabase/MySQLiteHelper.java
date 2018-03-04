package finalyearproject.iseqStockExchange.SQLiteDatabase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import finalyearproject.iseqStockExchange.Constants.Constants;
import finalyearproject.iseqStockExchange.Main.StockItemRow;
import finalyearproject.iseqStockExchange.POJO.Quote;
import finalyearproject.iseqStockExchange.POJO.ResultWrapper;

public class MySQLiteHelper {


    public static final String S_ID = "STOCK_ID";
    public static final String S_TID = "TICKER_ID";
    public static final String S_NAME = "NAME";
    public static final String S_NUM = "NUMBER_BOUGHT";
    public static final String S_INDVAL = "INDIVIDUAL_VALUE";
    public static final String S_TVAL= "TOTAL_VALUE";
    public static final String S_TCOST = "TOTAL_COST";
    public static final String S_DATE = "DATE";
    public static final String S_TYPE = "TYPE";
    public static final String S_SOLD = "IS_SOLD";
    public static final String NAME_PURCHASE = "STOCK_PURCHASE";


    public static final String KEY_SYMBOL = "SYMBOL";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_PRICE = "PRICE";
    public static final String KEY_CHANGE_PERCENT = "CHANGE_PERCENT";
    public static final String NAME_BACKUP = "STOCK_BACKUP";

    private static final String DATABASE_NAME = "stock_portfolio.db";
    private static final int DATABASE_VERSION = 2;



    private DatabaseHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;



    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            db.execSQL("CREATE TABLE "  + NAME_PURCHASE + " (" +

                            S_ID + " INTEGER, "+
                            S_TID + " TEXT, "+
                            S_NAME + " TEXT, " +
                            S_NUM + " INTEGER, " +
                            S_INDVAL + " DOUBLE, " +
                            S_TVAL + " DOUBLE, " +
                            S_TCOST + " DOUBLE, " +
                            S_DATE + " TEXT, " +
                            S_TYPE + " TEXT, " +
                            S_SOLD + " INTEGER);"
            );


            db.execSQL("CREATE TABLE "  + NAME_BACKUP + " (" +
                            KEY_SYMBOL + " TEXT, "+
                            KEY_NAME + " TEXT, "+
                            KEY_PRICE + " INTEGER, " +
                            KEY_CHANGE_PERCENT+ " TEXT);"
            );



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + NAME_PURCHASE);
            db.execSQL("DROP TABLE IF EXISTS " + NAME_BACKUP);

            onCreate(db);
        }

    }


    public MySQLiteHelper(Context c){
        ourContext = c;
        ourHelper = new DatabaseHelper(ourContext);
    }



    public MySQLiteHelper open()throws SQLException {
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }



    public void close(){
        ourHelper.close();
    }



    public long createStockItemEntry(int s_id, String s_tid, String s_name, int s_num, double s_indv, double s_tval,double s_tcost, String s_date, String s_type) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(S_ID, s_id);
        cv.put(S_TID, s_tid);
        cv.put(S_NAME, s_name);
        cv.put(S_NUM, s_num);
        cv.put(S_INDVAL, s_indv);
        cv.put(S_TVAL,s_tval);
        cv.put(S_TCOST,s_tcost);
        cv.put(S_DATE,s_date);
        cv.put(S_TYPE, s_type);
        cv.put(S_SOLD, -1);

        return ourDatabase.insert(NAME_PURCHASE, null, cv);

    }


    public ArrayList<StockPurchase> getStockGroupEntry(String type) {
        // TODO Auto-generated method stub

        String[] columns = new String[]{S_ID, S_TID, S_NAME, S_NUM, S_INDVAL, S_TVAL, S_TCOST, S_DATE, S_TYPE,S_SOLD};
        Cursor c = ourDatabase.query(NAME_PURCHASE, columns, null, null, null, null, null);

        ArrayList<StockPurchase> stockGroupData = new ArrayList<StockPurchase>();

        int i_id = c.getColumnIndex(S_ID);
        int i_tid = c.getColumnIndex(S_TID);
        int i_name = c.getColumnIndex(S_NAME);
        int i_num = c.getColumnIndex(S_NUM);
        int i_cval = c.getColumnIndex(S_INDVAL);
        int i_tval = c.getColumnIndex(S_TVAL);
        int i_tcost = c.getColumnIndex(S_TCOST);
        int i_date = c.getColumnIndex(S_DATE);
        int i_type = c.getColumnIndex(S_TYPE);
        int i_sold = c.getColumnIndex(S_SOLD);


        for (c.moveToLast(); !c.isBeforeFirst(); c.moveToPrevious()) {

            if (type.equals(Constants.BUY)) {
                if (c.getString(i_type).equals(Constants.BUY) && c.getInt(i_sold) == -1) {
                    stockGroupData.add(new StockPurchase(c.getInt(i_id), c.getString(i_tid), c.getString(i_name), c.getInt(i_num), c.getDouble(i_cval), c.getDouble(i_tval), c.getDouble(i_tcost), c.getString(i_date), c.getString(i_type)));
                }
            } else if(type.equals(Constants.SELL)) {
                if (c.getString(i_type).equals(Constants.SELL)) {
                    stockGroupData.add(new StockPurchase(c.getInt(i_id), c.getString(i_tid), c.getString(i_name), c.getInt(i_num), c.getDouble(i_cval), c.getDouble(i_tval), c.getDouble(i_tcost), c.getString(i_date), c.getString(i_type)));
                }
            }else{
                stockGroupData.add(new StockPurchase(c.getInt(i_id), c.getString(i_tid), c.getString(i_name), c.getInt(i_num), c.getDouble(i_cval), c.getDouble(i_tval), c.getDouble(i_tcost), c.getString(i_date), c.getString(i_type)));
            }
        }

        return stockGroupData;

    }






    public double getPortfolioValueFromSQLLiteDB() {

        double portfolioValue = 0.0;
        Cursor cursor = ourDatabase.rawQuery(
                "SELECT SUM(" + S_TVAL + ") FROM " + NAME_PURCHASE + " WHERE " + S_TYPE + " LIKE " + "'" + Constants.BUY + "'" + " AND " + S_SOLD + " = -1" , null);
        if (cursor.moveToFirst()) {
            portfolioValue = cursor.getDouble(0);
        }
        return portfolioValue;
    }

    public double getStockItemPortfolioValueFromSQLLiteDB(String symbol) {

        double portfolioValue = 0.0;
        Cursor cursor = ourDatabase.rawQuery(
                "SELECT SUM(" + S_TVAL + ") FROM " + NAME_PURCHASE + " WHERE " + S_TID +" LIKE "+ "'" + symbol + "'" + " AND " +  S_TYPE + " LIKE " + "'" + Constants.BUY + "'" +" AND " + S_SOLD + " = -1" , null);
        if (cursor.moveToFirst()) {
            portfolioValue = cursor.getDouble(0);
        }
        return portfolioValue;
    }

    public double getPortfolioCostFromSQLLiteDB() {

        double portfolioCost = 0.0;
        Cursor cursor = ourDatabase.rawQuery(
                "SELECT SUM(" + S_TCOST + ") FROM " + NAME_PURCHASE  + " WHERE " + S_TYPE + " LIKE " + "'" + Constants.BUY + "'" + " AND " + S_SOLD + " = -1", null);
        if (cursor.moveToFirst()) {
            portfolioCost = cursor.getDouble(0);
    }
    return portfolioCost;
    }



    public long createStockBackupEntry(String b_symbol, String b_name, double b_price, String b_change_percent) {
        // TODO Auto-generated method stub
        ourDatabase.delete(NAME_BACKUP, null, null);
        ContentValues cv = new ContentValues();
        cv.put(KEY_SYMBOL, b_symbol);
        cv.put(KEY_NAME, b_name);
        cv.put(KEY_PRICE, b_price);
        cv.put(KEY_CHANGE_PERCENT, b_change_percent);

        return ourDatabase.insert(NAME_BACKUP, null, cv);

    }

    public void truncateTable(String TABLE_NAME) {
        // TODO Auto-generated method stub
        ourDatabase.delete(TABLE_NAME, null, null);
 }

    public ArrayList<StockItemRow> getStockBackup() {
        // TODO Auto-generated method stub


        String[] columns = new String[]{KEY_SYMBOL,KEY_NAME,KEY_CHANGE_PERCENT,KEY_PRICE};
        Cursor c = ourDatabase.query(NAME_BACKUP, columns, null, null, null, null, null);

        ArrayList<StockItemRow> backups = new ArrayList<StockItemRow>();

        int b_symbol = c.getColumnIndex(KEY_SYMBOL);
        int b_name = c.getColumnIndex(KEY_NAME);
        int b_change_percent = c.getColumnIndex(KEY_CHANGE_PERCENT);
        int b_price = c.getColumnIndex(KEY_PRICE);

        for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
            backups.add(new StockItemRow(c.getString(b_symbol),c.getString(b_name),c.getString(b_change_percent),c.getDouble(b_price)));
        }

        return backups ;

    }


    public void updateStockValues(ResultWrapper result)
    {
        Quote[] quotes = result.getQuery().getResults().getQuote();
        ContentValues args = new ContentValues();

        int numRows = (int)DatabaseUtils.queryNumEntries(ourDatabase, NAME_PURCHASE);

        try {
            for (int i = 0; i < quotes.length; i++) {
                args.put(S_INDVAL, quotes[i].getLastTradePriceOnly());
                ourDatabase.update(NAME_PURCHASE, args, S_ID + "=" + i, null);
            }
            for(int i = 0; i<numRows;i++){

                ContentValues cv = new ContentValues();
                cv.put(S_TVAL, getValueFromRow(i,S_INDVAL) * getValueFromRow(i,S_NUM));
                Log.i("SQLITE CHECKER",Integer.toString(ourDatabase.update(NAME_PURCHASE, cv, "ROWID" + " = " + (i+1) , null)));// +1 accounts for autoincrementing S_AUTO_ID starting at 1

            }

        }catch (NullPointerException e){
              e.printStackTrace();
    }
    }


    public void sellStockItem(StockPurchase stockItemToSell){

        ContentValues args =new ContentValues();
        args.put(S_SOLD,0);

        Log.i("UPDATE CHECKER", Integer.toString(ourDatabase.update(NAME_PURCHASE, args, S_TID + " LIKE " + "'" + stockItemToSell.getTickerId() + "'", null)));

        ContentValues cv = new ContentValues();
        cv.put(S_ID, stockItemToSell.getId());
        cv.put(S_TID, stockItemToSell.getTickerId());
        cv.put(S_NAME, stockItemToSell.getName());
        cv.put(S_NUM, stockItemToSell.getNum());
        cv.put(S_INDVAL, stockItemToSell.getCurValue());
        cv.put(S_TVAL,stockItemToSell.getTotalValue());
        cv.put(S_TCOST,stockItemToSell.getTotalCost());
        cv.put(S_DATE,stockItemToSell.getDate());
        cv.put(S_TYPE, Constants.SELL);
        cv.put(S_SOLD,0);


        ourDatabase.insert(NAME_PURCHASE, null, cv);




    }


    public double getValueFromRow(int position, String key) {
        String[] columns = new String[]{key};
        Cursor c = ourDatabase.query(NAME_PURCHASE, columns, null, null, null, null, null);

        double result = 0.0;

        try {
            int i_num = c.getColumnIndex(key);


            c.moveToPosition(position);
            result = c.getDouble(i_num);
        }catch(CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        return result;

    }


}








