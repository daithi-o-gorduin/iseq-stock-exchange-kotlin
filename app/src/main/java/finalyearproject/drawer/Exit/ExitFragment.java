package finalyearproject.drawer.Exit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import finalyearproject.drawer.EventBus.BusProvider;
import finalyearproject.drawer.EventBus.ExitEvent;
import finalyearproject.drawer.EventBus.ObserverEvent;
import finalyearproject.drawer.R;

/**
 * Created by Dvaid on 06/02/2015.
*/

public class ExitFragment extends Fragment {

    Button mExitTrue,mExitFalse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View android = inflater.inflate(R.layout.frag_exit, container, false);
        mExitTrue = (Button) android.findViewById(R.id.bt_yes);
        mExitFalse = (Button) android.findViewById(R.id.bt_no);

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
                    BusProvider.getInstance().post(new ExitEvent());
                }
                return false;
            }
        });




        return android;
    }}



