package me.arkits.dabba;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    private List<String> mStrings;
    private LayoutParams mLayoutParams;
    private KitsIME mIME;

    public KeyboardAdapter(Context context, KitsIME ime) {
        mStrings = new ArrayList<>();

        DatabaseHandler db = new DatabaseHandler(context);

        List<Emoji> emojis = db.getAllEmojis();

        for(Emoji em : emojis){
            String log =  em.getLabel();
            mStrings.add(log);
        }


        mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        mIME = ime;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        TextView string = new TextView(viewGroup.getContext());
        string.setPadding(10, 10, 0, 10);
        string.setLayoutParams(mLayoutParams);
        string.setOnClickListener(mIME);
        Holder holder = new Holder(string);
        return holder;
    }
    @Override
    public void onBindViewHolder(Holder holder, int i) {
        holder.mStringView.setText(mStrings.get(i));
    }
    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView mStringView;
        public Holder(View itemView) {
            super(itemView);
            mStringView = (TextView) itemView;
        }
    }
}