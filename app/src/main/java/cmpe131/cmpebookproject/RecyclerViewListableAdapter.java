package cmpe131.cmpebookproject;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerViewListableAdapter extends RecyclerView.Adapter<RecyclerViewListableAdapter.ListableViewHolder> {

    public static class ListableViewHolder extends RecyclerView.ViewHolder {
        public ListableViewHolder(View itemView) { super(itemView); }
    }




    private ArrayList<? extends Listable> dataset;
    private int viewLayoutRes;
    private boolean launchIntentOnClick;

    public RecyclerViewListableAdapter(ArrayList<? extends Listable> dataset, @LayoutRes int viewLayoutRes, boolean launchIntentOnClick) {
        this.dataset = dataset;
        this.viewLayoutRes = viewLayoutRes;
        this.launchIntentOnClick = launchIntentOnClick;
    }


    @NonNull
    @Override
    public ListableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(parent.getContext()).inflate(viewLayoutRes, parent, false);
        return new ListableViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListableViewHolder holder, int position) {
        dataset
                .get(position)
                .populateListView(
                        holder
                                .itemView);
        holder.itemView.setTag(position);

        if (launchIntentOnClick) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //holder.getAdapterPosition()
                    Listable thisItem = dataset.get((int)v.getTag());
                    v.getContext().startActivity(thisItem.getDisplayIntent(v.getContext()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void updateList (ArrayList<? extends Listable> dataset) {
        this.dataset = dataset;
        this.notifyDataSetChanged();
    }

}
