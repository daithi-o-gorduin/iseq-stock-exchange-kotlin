package finalyearproject.iseqStockExchange.Exit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import finalyearproject.iseqStockExchange.EventBus.BusProvider;
import finalyearproject.iseqStockExchange.EventBus.ReOpenNavEvent;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 06/02/2015.
*/

public class ExitFragment extends Fragment {

    Button mExitTrue,mExitFalse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View exitView = inflater.inflate(R.layout.frag_exit, container, false);
        mExitTrue = (Button) exitView.findViewById(R.id.bt_yes);
        mExitFalse = (Button) exitView.findViewById(R.id.bt_no);

        mExitTrue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.exit(-1);

                }
                return false;
            }
        });

        mExitFalse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
              if (event.getAction() == MotionEvent.ACTION_UP) {
                    BusProvider.getInstance().post(new ReOpenNavEvent());
                }
                return false;
            }
        });

         return exitView;
    }
}



