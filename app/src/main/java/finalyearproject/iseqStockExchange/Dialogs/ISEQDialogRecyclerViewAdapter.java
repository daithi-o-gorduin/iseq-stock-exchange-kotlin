package finalyearproject.iseqStockExchange.Dialogs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import finalyearproject.iseqStockExchange.Constants.Constants;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 09/03/2015.
 */



public class ISEQDialogRecyclerViewAdapter extends RecyclerView.Adapter<ISEQDialogRecyclerViewAdapter.ViewHolder>{


    private ArrayList<String> mModelData;
    Context mContext;

    public ISEQDialogRecyclerViewAdapter(Context context, ArrayList<String> modelData) {
        this.mModelData = modelData;
        this.mContext = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item_row, parent, false);
        return new ViewHolder(v);
    }



    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mStockText.setText(Constants.ISEQ_DIALOG_LIST_DATA[position] +
                mModelData.get(position));

    }

    @Override public int getItemCount() {
        return mModelData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mStockText;

        public ViewHolder(View itemView) {
            super(itemView);
            mStockText = (TextView) itemView.findViewById(R.id.tv_stock_dialog_data);

        }
    }

}
