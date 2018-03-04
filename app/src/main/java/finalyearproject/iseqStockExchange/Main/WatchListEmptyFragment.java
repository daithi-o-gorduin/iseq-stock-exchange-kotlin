package finalyearproject.iseqStockExchange.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import finalyearproject.iseqStockExchange.EventBus.BusProvider;
import finalyearproject.iseqStockExchange.EventBus.WatchlistToISEQEvent;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 16/02/2015.
 */
public class WatchListEmptyFragment extends Fragment{

    ImageButton mWatchListEmpty;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View android = inflater.inflate(R.layout.frag_watchlist_empty, container, false);
        mWatchListEmpty = (ImageButton) android.findViewById(R.id.ib_watchlist_empty);

        mWatchListEmpty.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(new WatchlistToISEQEvent());
            }
        });
        return android;
    }

}
