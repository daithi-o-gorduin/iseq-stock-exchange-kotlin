package finalyearproject.drawer.PortfolioTransactionHistory;

/**
 * Created by Dvaid on 17/02/2015.
 */

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import finalyearproject.drawer.R;


public class PortfolioTransactionActivity extends FragmentActivity{
    /**
     * Called when the activity is first created.
     */

    public static final String TAG_LIST= "Tag_Line";
    public static final String TAG_CIRCLE = "Tag_Circle";

    FrameLayout mTransContainer;
    ImageButton mArrow;
    Fragment[] mFragments = new Fragment[2];



    //private StockPurchase[] mLastTenRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        mTransContainer = (FrameLayout) findViewById(R.id.fl_trans);
        mArrow = (ImageButton) findViewById(R.id.ib_arrow);

        mFragments[0] = new PortfolioTransactionListFragment();
        mFragments[1] = new PortfolioTransactionCircleFragment();

        getSupportFragmentManager().beginTransaction()

                .add(R.id.fl_trans, mFragments[0])
                .commit();

        mArrow.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                int rotateFrom = 0,rotateTo = 0;
                Fragment nextFragment = null;
                int animIn = 0,animOut = 0;
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fl_trans);
                if(currentFragment instanceof PortfolioTransactionListFragment){
                    rotateFrom = 0;
                    rotateTo = 180;
                    animIn = R.anim.slide_in_left;
                    animOut = R.anim.slide_out_left;
                    nextFragment = new PortfolioTransactionCircleFragment();
                }else if(currentFragment instanceof  PortfolioTransactionCircleFragment){
                    rotateFrom = 180;
                    rotateTo = 0;
                    animIn = R.anim.slide_in_right;
                    animOut = R.anim.slide_out_right;
                    nextFragment = new PortfolioTransactionListFragment();
                }

                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(animIn,
                                animOut)
                        .replace(R.id.fl_trans, nextFragment)
                        .commit();

                RotateAnimation ra =new RotateAnimation(rotateFrom,rotateTo,mArrow.getWidth()/2,mArrow.getHeight()/2);

                ra.setFillAfter(true);
                ra.setDuration(500);

                mArrow.startAnimation(ra);
            }
        });




    }



}