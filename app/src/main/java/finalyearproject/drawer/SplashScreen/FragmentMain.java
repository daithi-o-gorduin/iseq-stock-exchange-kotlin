package finalyearproject.drawer.SplashScreen;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.squareup.otto.Subscribe;

import java.util.concurrent.ExecutionException;

import finalyearproject.drawer.EventBus.BusProvider;
import finalyearproject.drawer.EventBus.Event;
import finalyearproject.drawer.REST.RetrofitRESTCall;
import finalyearproject.drawer.SplashScreen.views.Scroller;
import finalyearproject.drawer.Main.MainActivity;
import finalyearproject.drawer.POJO.ResultWrapper;
import finalyearproject.drawer.R;
import finalyearproject.drawer.REST.RESTCall;


public class FragmentMain extends Fragment {

    private Scroller mScroller;
    private CircleProgressBar mProgress;
    private ProgressBar progress;
    private int count = 0;
    private Runnable runnable;
    private Handler handler;
    private ResultWrapper mResult;
    private boolean mAnimationOver = false;


    public static FragmentMain newInstance() {
        FragmentMain fragment = new FragmentMain();
        return fragment;
    }

    public FragmentMain() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = createView(inflater, container);
        BusProvider.getInstance().register(this);
        buildViews();
        return view;
    }

        private void buildViews() {
        mScroller.initializeScrollView(0);
    }

    private View createView(final LayoutInflater inflater, final ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mScroller = (Scroller) view.findViewById(R.id.scroller);
        mProgress = (CircleProgressBar) view.findViewById(R.id.progressBar_splash);
        mProgress.setColorSchemeResources(R.color.list_divider);
        return view;
    }

    private ResultWrapper performAPICall(){
        RESTCall task = new RetrofitRESTCall();
        ResultWrapper result = null;
        result = task.doTask(getActivity());

        return result;
    }

    @Subscribe
    public void handleAnimationFinished(Event event) {
        Intent main = new Intent(getActivity(), MainActivity.class);
        this.mResult = performAPICall();
        if(mResult!=null) {
             main.putExtra("ResultWrapper", new Gson().toJson(mResult));
        }

        startActivity(main);
        getActivity().finish();
 }


    public CircleProgressBar getProgressBar(){
        return mProgress;
    }

}
