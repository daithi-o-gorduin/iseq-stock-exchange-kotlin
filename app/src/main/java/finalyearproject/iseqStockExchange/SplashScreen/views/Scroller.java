package finalyearproject.iseqStockExchange.SplashScreen.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

//import com.google.inject.Inject;
import com.squareup.otto.Bus;

import finalyearproject.iseqStockExchange.EventBus.BusProvider;
import finalyearproject.iseqStockExchange.EventBus.Event;
import finalyearproject.iseqStockExchange.SplashScreen.listener.ScrollViewListener;
import finalyearproject.iseqStockExchange.R;

public class Scroller extends LinearLayout implements ScrollViewListener {

    private Bus bus;
    // recommended to set a value between 2 and 5
    private static final int SCROLL_VELOCITY = 2;

    private ObservableScrollView observableScrollView;
    private BottomCropImage mMaskImage;
    private ImageView mImage;

    private int totalHeight = 0;
    private float percentageFilling = 0;
    private Listener mListener;
    private ViewGroup centeredLayout;

    public Scroller(Context context) {
        this(context, null);
    }

    public Scroller(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Scroller(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        bus = new Bus();
        bus.register(this);
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.scroller, this, true);
        observableScrollView = (ObservableScrollView) root.findViewById(R.id.scroll_view);
        centeredLayout = (RelativeLayout) root.findViewById(R.id.centered_layout);

        mMaskImage = (BottomCropImage) root.findViewById(R.id.mask_image);
        mImage = (ImageView) root.findViewById(R.id.image);

        Drawable unfilledIcon = context.getResources().getDrawable(R.drawable.crest_unfilled);

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;

        ViewGroup.LayoutParams params = centeredLayout.getLayoutParams();
        params.height = ((BitmapDrawable) unfilledIcon).getBitmap().getHeight();
        centeredLayout.setLayoutParams(params);
        totalHeight = height * SCROLL_VELOCITY;
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new LayoutParams(width, totalHeight));
        relativeLayout.setBackgroundResource(R.drawable.scroller_background);

        observableScrollView.addView(relativeLayout);


    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldX, int oldY) {
        float percentage = (float) ((y) * 100) / (float) (totalHeight - scrollView.getHeight());
        if (y > oldY) {
            // scrolling up
        }
        if (y < oldY) {
            // scrolling down
        }
        //setLevelOfMask(100);
        if (mListener != null) {
            mListener.onPercentageChange(percentage);
        }
       // scrollTo(100);
    }

    @Override
    public void onEndScroll(ObservableScrollView scrollView) {
        // write your logic

    }

    public void setLevelOfMask(float percentageOfFilling) {
        this.percentageFilling = percentageOfFilling;
        float finalHeight = ((float) (mImage.getHeight()) / 100 * percentageOfFilling);
        ViewGroup.LayoutParams params = mMaskImage.getLayoutParams();
        params.height = (int) finalHeight;
        mMaskImage.setLayoutParams(params);
    }

    /**
     * Call this method after inflating the view
     * @param percentageOfFilling percentage of the height of the mask
     */
    public void initializeScrollView(final float percentageOfFilling) {
        this.percentageFilling = percentageOfFilling;

        final Handler timerHandler = new Handler();


        observableScrollView.post(new Runnable() {
        int count = 0;
            @Override
            public void run() {
                if(count <= 100) {
                    setLevelOfMask(count);
                    scrollTo(count);
                    //observableScrollView.setScrollViewListener(Scroller.this);
                    count += 1;
                    timerHandler.postDelayed(this, 25);
                }else{
                    timerHandler.removeCallbacks(this);
                    BusProvider.getInstance().post(new Event());
                }
            }
        });


    }

    /**
     * Scroll to x and y
     * @param x x value
     * @param y y value
     */
    public void scrollTo(int x, int y) {
        observableScrollView.scrollTo(x, y);
    }

    /**
     * Rezie the mask to given percentage
     * @param percentage
     */
    public void scrollTo(float percentage) {
        int newY;
        if (percentage == 100) {
            newY = getTotalHeight() - observableScrollView.getBottom();
        } else {
            newY = (int) ((getTotalHeight() - observableScrollView.getBottom()) / 100 * percentage);
        }
        observableScrollView.scrollTo(0, newY);
    }

    public Listener getListener() {
        return mListener;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public int getTotalHeight() {
        return observableScrollView.getChildAt(0).getHeight();
    }

    public float getPercentageFilling() {
        return percentageFilling;
    }

    public interface Listener {

        void onPercentageChange(float percentage);

    }


}
