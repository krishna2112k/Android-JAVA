package com.cityofnewyork.project.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cityofnewyork.project.R;
import com.cityofnewyork.project.ui.Activities.SATDetailsPage;
import com.cityofnewyork.project.utils.BaseClass;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    Context context;
    RecyclerView recyclerView;

    public List<String> mItemList;

    public RecyclerViewAdapter(List<String> itemList, Context context, RecyclerView recyclerView ) {

        this.mItemList = itemList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int pos = recyclerView.getChildAdapterPosition(v);
                    if (pos >= 0 && pos < getItemCount()) {
                        Toast.makeText(context, mItemList.get(pos), Toast.LENGTH_SHORT).show();
                        Intent satDetails = new  Intent(context,SATDetailsPage.class);
                        satDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        satDetails.putExtra(BaseClass.SCHOOL_NAME,mItemList.get(pos));
                        context.startActivity(satDetails);
                    }
                }
            });

            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.tvItem);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {

        String item = mItemList.get(position);
        viewHolder.tvItem.setText(item);

    }
}
