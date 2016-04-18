package siu.example.com.omdbapi.recyclerview;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import siu.example.com.omdbapi.R;
import siu.example.com.omdbapi.omdb.Omdb;

/**
 * Created by samsiu on 4/18/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.OmdbViewHolder>{

    List<Omdb> omdbList;

    public static class OmdbViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView omdbNameTextView;

        OmdbViewHolder(View itemView){
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.item_cardView);
            omdbNameTextView = (TextView)itemView.findViewById(R.id.card_omdbName_textView);
        }
    }

    public RecyclerViewAdapter(List<Omdb> omdbList){
        this.omdbList = omdbList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecyclerViewAdapter.OmdbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        OmdbViewHolder omdbViewHolder = new OmdbViewHolder(v);
        return omdbViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.OmdbViewHolder holder, int position) {
        holder.omdbNameTextView.setText(omdbList.get(position).getDirector());
    }

    @Override
    public int getItemCount() {
        return omdbList.size();
    }
}
