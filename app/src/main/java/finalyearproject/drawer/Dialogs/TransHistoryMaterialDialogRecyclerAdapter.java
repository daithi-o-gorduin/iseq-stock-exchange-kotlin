package finalyearproject.drawer.Dialogs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import finalyearproject.drawer.R;
import finalyearproject.drawer.SQLiteDatabase.StockPurchase;

/**
 * Created by Dvaid on 20/02/2015.
 */
public class TransHistoryMaterialDialogRecyclerAdapter extends RecyclerView.Adapter<TransHistoryMaterialDialogRecyclerAdapter.ViewHolder>{


        private ArrayList<String> data;
        private int itemLayout;
        String[] mListText;


        public TransHistoryMaterialDialogRecyclerAdapter(ArrayList<String> data) {
            this.data = data;
            mListText = new String[]{"Ticker ID: ","Date of Purchase: ","Number of Stocks Bought: ","Cost of Purchase: ","Current Value: ","Current Individual Value: "};
        }

        @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item_row, parent, false);
            return new ViewHolder(v);
        }

        @Override public void onBindViewHolder(ViewHolder holder, int position) {
            String singleData = data.get(position);
            holder.mListText.setText(mListText[position]+ singleData);
            holder.itemView.setTag(singleData);
        }

        @Override public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mListText;


            public ViewHolder(View itemView) {
                super(itemView);
                mListText= (TextView) itemView.findViewById(R.id.tv_stock_dialog_data);
            }
        }

}
