package finalyearproject.iseqStockExchange.SplashScreen;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.squareup.otto.Subscribe;

import finalyearproject.iseqStockExchange.Dialogs.TransHistoryMaterialDialogView;
import finalyearproject.iseqStockExchange.EventBus.BusProvider;
import finalyearproject.iseqStockExchange.EventBus.Event;
import finalyearproject.iseqStockExchange.REST.RetrofitRESTCall;
import finalyearproject.iseqStockExchange.SplashScreen.views.Scroller;
import finalyearproject.iseqStockExchange.Main.MainActivity;
import finalyearproject.iseqStockExchange.POJO.ResultWrapper;
import finalyearproject.iseqStockExchange.R;
import finalyearproject.iseqStockExchange.REST.RESTCall;
import retrofit.RetrofitError;


public class FragmentMain extends Fragment {

    private Scroller mScroller;
    private CircleProgressBar mProgress;

    private ResultWrapper mResult;




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
       // mProgress = (ProgressDialog) view.findViewById(R.id.progressBar_splash);
 //       mProgress.setColorSchemeResources(R.color.list_divider);

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
            startActivity(main);
            getActivity().finish();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Cannot connect to a network, please restart application")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            System.exit(-1);

                        }
                    });

            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            final Button buttonPositiveInvolvement = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPositiveInvolvement.setTextColor(getActivity().getResources().getColor(R.color.app_orange));

        }




 }




}
