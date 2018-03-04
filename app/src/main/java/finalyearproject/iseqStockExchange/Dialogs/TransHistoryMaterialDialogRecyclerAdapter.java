package finalyearproject.iseqStockExchange.Dialogs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import finalyearproject.iseqStockExchange.Constants.Constants;
import finalyearproject.iseqStockExchange.R;

/**
 * Created by Dvaid on 20/02/2015.
 */
public class TransHistoryMaterialDialogRecyclerAdapter extends RecyclerView.Adapter<TransHistoryMaterialDialogRecyclerAdapter.ViewHolder>{


        private ArrayList<String> mModelData;

        public TransHistoryMaterialDialogRecyclerAdapter(ArrayList<String> modelData) {
            this.mModelData = modelData;
        }

        @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item_row, parent, false);
            return new ViewHolder(v);
        }

        @Override public void onBindViewHolder(ViewHolder holder, int position) {
            String singleData = mModelData.get(position);
            holder.mListText.setText(Constants.HISTORY_DIALOG_LIST_DATA[position]+ singleData);
            holder.itemView.setTag(singleData);
        }

        @Override public int getItemCount() {
            return mModelData.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView mListText;

            public ViewHolder(View itemView) {
                super(itemView);
                mListText= (TextView) itemView.findViewById(R.id.tv_stock_dialog_data);
            }
        }

}
