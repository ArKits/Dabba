package me.arkits.dabba;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by archi on 4/10/2016.
 */
public class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.Holder> {
    private static final String TAG = "KeyboardAdapter";

    private List<Emoji> emojiList;

    private LayoutParams mLayoutParams;
    private KitsIME mIME;



    public KeyboardAdapter(KitsIME ime, List<Emoji> emojiList) {

        this.emojiList = emojiList;
        mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mIME = ime;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emoji_list_kb, parent, false);

        return new Holder(itemView);
    }


    @Override
    public void onBindViewHolder(Holder holder, int i) {

        Emoji emoji = emojiList.get(i);
        holder.mlabel.setText(emoji.getLabel());
    }



    @Override
    public int getItemCount() {
        return emojiList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView mlabel;

        public Holder(View view) {
            super(view);
            mlabel = (TextView) view.findViewById(R.id.rlabl);
        }
    }
}