package finalyearproject.iseqStockExchange.PortfolioTransactionHistory;

/**
 * Created by Dvaid on 17/02/2015.
 */

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import finalyearproject.iseqStockExchange.R;


public class PortfolioTransactionActivity extends FragmentActivity{

    public static final int NUM_PAGES = 2;
    @InjectView(R.id.ib_arrow)ImageView mArrow;
    Fragment[] mFragments = new Fragment[NUM_PAGES];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans_main);
        ButterKnife.inject(this);

        mFragments[0] = new PortfolioTransactionListFragment();
        mFragments[1] = new PortfolioTransactionCircleFragment();

        getSupportFragmentManager().beginTransaction()

                .add(R.id.fl_trans, mFragments[0])
                .commit();

    }


   @OnClick(R.id.ib_arrow)
    public void ClickArrow(){
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

       RotateAnimation rotateAnimation =new RotateAnimation(rotateFrom,rotateTo,mArrow.getWidth()/2,mArrow.getHeight()/2);

       rotateAnimation.setFillAfter(true);
       rotateAnimation.setDuration(500);

       mArrow.startAnimation(rotateAnimation);
   }



}