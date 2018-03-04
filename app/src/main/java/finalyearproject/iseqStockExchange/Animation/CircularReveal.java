package finalyearproject.iseqStockExchange.Animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by Dvaid on 24/03/2015.
 * code modified version of implementation found at
 * //http://ptrprograms.blogspot.ie/2014/08/getting-started-with-android-l.html as mentioned in report
 */
public class CircularReveal {

    View mView;
    Context mContext;

    public CircularReveal(View view, Context context){
        this.mView = view;
        this.mContext = context;
    }

    public void hideImageCircular() {


       Animator anim = ViewAnimationUtils.createCircularReveal(mView, mView.getWidth(),
                        mView.getHeight(),   0,
                        mView.getHeight() * 2);

        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mView.setVisibility( View.INVISIBLE );
            }
        });

        anim.start();
    }


    public void revealImageCircular(final int drawable) {

        Animator anim = ViewAnimationUtils.createCircularReveal(mView, mView.getWidth(),
                mView.getHeight(),   0,
                mView.getHeight() * 2);

        anim.setDuration( 1000 );
        anim.addListener( new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mView.setVisibility( View.VISIBLE );
                mView.setBackgroundResource(drawable);
            }
        });

        anim.start();
    }
}
