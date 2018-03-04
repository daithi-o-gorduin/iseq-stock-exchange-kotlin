package finalyearproject.drawer.Dialogs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import finalyearproject.drawer.R;

/**
 * Created by Dvaid on 09/03/2015.
 */



public class ISEQDialogRecyclerViewAdapter extends RecyclerView.Adapter<ISEQDialogRecyclerViewAdapter.ViewHolder>{


    private ArrayList<String> data;
    private int itemLayout;
    String[] mListText;
    Context mContext;

    public ISEQDialogRecyclerViewAdapter(Context context, ArrayList<String> data) {
        this.data = data;
        mListText = new String[]{"Name: ","Symbol: ","Current Price: ","Change Percent: ","Days Low: ","Days High: ","Years Low: ","Years High: ","Volumes: ","Last Trade Time: "};
        this.mContext = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item_row, parent, false);
        return new ViewHolder(v);
    }



    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.mListText.setText(mListText[position]+ singleData);
        holder.stockText.setText(mListText[position] + data.get(position));

    }

    @Override public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView stockText;


        public ViewHolder(View itemView) {
            super(itemView);
            stockText = (TextView) itemView.findViewById(R.id.tv_stock_dialog_data);

        }
    }

}
