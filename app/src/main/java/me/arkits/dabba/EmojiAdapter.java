package me.arkits.dabba;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by archi on 4/8/2016.
 */
public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.MyViewHolder> {
    private List<Emoji> emojiList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mlabel, mtext;

        public MyViewHolder(View view) {
        super(view);
        mlabel = (TextView) view.findViewById(R.id.rlabl);
        mtext = (TextView) view.findViewById(R.id.rtxt);
        }
    }

    public EmojiAdapter(List<Emoji> emojiList) {
        this.emojiList = emojiList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emoji_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Emoji emoji = emojiList.get(position);
        holder.mlabel.setText(emoji.getLabel());
        holder.mtext.setText(emoji.getText());
    }

    @Override
    public int getItemCount() {
        return emojiList.size();
    }

}
