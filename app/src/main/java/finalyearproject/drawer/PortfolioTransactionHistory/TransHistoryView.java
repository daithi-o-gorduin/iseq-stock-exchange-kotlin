package finalyearproject.drawer.PortfolioTransactionHistory;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import finalyearproject.drawer.Constants.Constants;
import finalyearproject.drawer.Dialogs.TransHistoryMaterialDialog;
import finalyearproject.drawer.R;
import finalyearproject.drawer.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 20/02/2015.
 */


public class TransHistoryView extends View {

    Context mContext;
    Canvas mCanvas;
    private static int xScreen,yScreen;
    String mColor = "#009A49";
    private String mColorBackup = "#ffb347";
    private int temp;
    private boolean isHit;
    public float[] mRadii = new float[10];
    public String[] mColors = new String[10];
    public String[] mTextColors = new String[10];
    public int[] mAlpha = new int[10];
    public int[] mImages = new int[10];
    public int[] mScales = new int[10];
    public Paint.Style[] mBrushStyles = new Paint.Style[10];
    ArrayList<Float> mXArray,mYArray;

    public float[] mTextSizes = new float[10];

    public static final int NUM_OF_ELEMENTS = 10;

    public String[] mArrowColors = new String[2];

    int arrowDrawable;

    ArrayList<StockPurchase> mLastTenRecords;


    public TransHistoryView(Context context, final ArrayList<StockPurchase> mLastTenRecords) {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.mLastTenRecords = mLastTenRecords;
        mRadii = new float[] {185f,150f,110f,110f,110f,95f,95f,70f,70f,70f};
        mImages = new int[10];
        mColors = new String[10];//{"#009A49","#009A49","#009A49","#009A49","#009A49","#009A49","#009A49","#009A49","#009A49","#009A49"};
        for(int i = 0;i < 10;i++){
            try {
                if (mLastTenRecords.get(i).getType().equals(Constants.BUY)) {
                    mColors[i] = "#009A49";
                    if(i < 7){
                        mImages[i] =  R.drawable.bought_image;
                    }else{
                        mImages[i] = R.drawable.bought_image_green;
                    }

                } else if (mLastTenRecords.get(i).getType().equals(Constants.SELL)) {
                    mColors[i] = "#BB202F";
                    if(i < 7){
                        mImages[i] =  R.drawable.sold_image;
                    }else{
                        mImages[i] = R.drawable.sold_image_red;
                    }
                }
            }catch(IndexOutOfBoundsException e){
                mColors[i] = "#ffb347";
                if(i < 7){
                    mImages[i] =  R.drawable.icon_image;
                }else{
                    mImages[i] = R.drawable.icon_image_orange;
                }
            }
        }
        mTextColors = new String[]{"#ffffff","#ffffff","#ffffff","#ffffff","#ffffff","#ffffff","#ffffff","#ffffff","#ffffff","#ffffff"};

        mAlpha = new int[]{255,255,200,200,200,200,200,255,255,255};
       //{R.drawable.bought_image,R.drawable.bought_image,R.drawable.bought_image,R.drawable.bought_image,
              //  R.drawable.bought_image,R.drawable.bought_image,R.drawable.bought_image,R.drawable.bought_image_green,
                //R.drawable.bought_image_green,R.drawable.bought_image_green};
        mScales = new int[]{150,150,100,100,100,80,80,60,60,60};
        mBrushStyles = new Paint.Style[]{Paint.Style.FILL,Paint.Style.FILL,Paint.Style.FILL,Paint.Style.FILL,Paint.Style.FILL,
                Paint.Style.FILL,Paint.Style.FILL,Paint.Style.STROKE,Paint.Style.STROKE,Paint.Style.STROKE};
        mXArray = new ArrayList<Float>();
        mYArray = new ArrayList<Float>();
        mArrowColors = new String[]{"#ffffff","#ffb347"};
        mTextSizes = new float[]{20f,20f,17f,17f,17f,14f,14f,11f,11f,11f};
        arrowDrawable = R.drawable.arrow_back;



        this.setOnTouchListener(new OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {



                float xTouchPosition = event.getX();
                float yTouchPosition = event.getY();


                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {

                    for (int i = 0; i < NUM_OF_ELEMENTS; i++) {
                        if ((xTouchPosition <= mXArray.get(i) + mRadii[i] && xTouchPosition >= mXArray.get(i) - mRadii[i])
                                && (yTouchPosition <= mYArray.get(i) + mRadii[i] && yTouchPosition >= mYArray.get(i) - mRadii[i])) {
                            setTemp(i);
                            setIsHit(true);
                            mColorBackup = mColors[i];
                            mColors[i] = "#ffb347";

                         /*   if (i > 6) {
                                mImages[i] = R.drawable.bought_image_orange;
                            }*/

                            v.invalidate();
                        }
                    }

                    if(xTouchPosition<=(float)1*(xScreen/8)+50&& xTouchPosition >= (float)1*(xScreen/8)-50
                    && (yTouchPosition <= (float)1*(yScreen/10) + 50 && yTouchPosition >=  (float)1*(yScreen/10)- 50)){
                        String temp =  mArrowColors[0];
                        mArrowColors[0] = mArrowColors[1];
                        mArrowColors[1] = temp;
                        arrowDrawable = R.drawable.arrow_back_orange;
                        v.invalidate();

                    }
                }else if(event.getAction() == android.view.MotionEvent.ACTION_UP){

                    mColors[getTemp()] = mColorBackup;
                    /*if(getTemp()>6) {
                       // mImages[getTemp()] = R.drawable.bought_image_green;
                    }*/
                    v.invalidate();
                    if(getIsHit() == true) {
                        try {
                            TransHistoryMaterialDialog dialog = new TransHistoryMaterialDialog(mContext, mLastTenRecords, getTemp());
                            dialog.build();
                        }catch(IndexOutOfBoundsException e){
                            e.printStackTrace();
                        }

                        setIsHit(false);
                    }
                }

                return true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        mCanvas = canvas;
        int xScreen = getWidth();
        int yScreen = getHeight();
        setXScreen(xScreen);
        setYScreen(yScreen);
        mXArray = putXYValsInArray(mXArray,"X");
        mYArray = putXYValsInArray(mYArray,"Y");


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        Bitmap boughtImage;

        for(int i = 0;i<NUM_OF_ELEMENTS;i++){
            paint.setColor(Color.parseColor(mColors[i]));
            paint.setAlpha(mAlpha[i]);
            paint.setStyle(mBrushStyles[i]);
            canvas.drawCircle(mXArray.get(i),mYArray.get(i),mRadii[i], paint);

            boughtImage = BitmapFactory.decodeResource(this.getResources(),
                    mImages[i]);
            boughtImage = Bitmap.createScaledBitmap(boughtImage, mScales[i],mScales[i], false);
            canvas.drawBitmap(boughtImage,(mXArray.get(i)-(boughtImage.getWidth()/2)), (mYArray.get(i)-(boughtImage.getHeight()/2)),  paint);
            paint.setColor(Color.parseColor(mTextColors[i]));
            paint.setTextSize(mTextSizes[i]);
            try {
                canvas.drawText(mLastTenRecords.get(i).getTickerId(), mXArray.get(i) - (boughtImage.getWidth() / 4), mYArray.get(i) + (boughtImage.getHeight() / 2) + 20, paint);
                canvas.drawText("(" + Integer.toString(mLastTenRecords.get(i).getNum()) + ")", mXArray.get(i) - 15, mYArray.get(i) + (boughtImage.getHeight() / 2) + 40, paint);
            }catch(IndexOutOfBoundsException e){
                e.printStackTrace();
            }

        }





    }



    public ArrayList putXYValsInArray(ArrayList xyArray,String key){

        if(key.equals("X")){
            xyArray.add((float)xScreen / 2);//,y/2);
            xyArray.add((float)3*(xScreen/4)); //3*(y/4)+50);
            xyArray.add(3.60f*(xScreen/(4)));//y/2+30);
            xyArray.add((float)(xScreen/(10)));//y/2+140);
            xyArray.add((float)6*(xScreen/10));//1*(y/5));
            xyArray.add((float)(xScreen/(10)));//y/2-120);
            xyArray.add((float)4*(xScreen/10));//3*(y/4)+50);
            xyArray.add(0.8f*(xScreen/(6)));//8.5f*(y/10));
            xyArray.add(3.1f*(xScreen/(10)));//2.5f*(y/10));
            xyArray.add(8.2f*(xScreen/(10)));//3.4f*(y/10));
        }else if (key.equals("Y")){
            xyArray.add((float)yScreen/2);
            xyArray.add((float)3*(yScreen/4)+50);
            xyArray.add((float)yScreen/2+30);
            xyArray.add((float)yScreen/2+140);
            xyArray.add((float)(yScreen/5)-20);
            xyArray.add((float)yScreen/2-120);
            xyArray.add((float)3*(yScreen/4)+50);
            xyArray.add(8.5f*(yScreen/10));
            xyArray.add(2.35f*(yScreen/10));
            xyArray.add(3.25f*(yScreen/10));
        }
        return xyArray;
    }



    public int getXScreen(){
        return xScreen;
    }

    public int getYScreen(){
        return yScreen;
    }

    public void setXScreen(int x){
        this.xScreen = x;
    }

    public void setYScreen(int y){
        this.yScreen = y;
    }

    public int getTemp(){
        return temp;
    }

    public void setTemp (int temp){
        this.temp = temp;
    }

    public boolean getIsHit(){
        return isHit;
    }

    public void setIsHit (boolean isHit){
        this.isHit = isHit;
    }
}


