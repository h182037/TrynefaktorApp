package incorporate.games.avon.trynefaktorapp;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends ArrayAdapter<Player> {
    private Context mContext;
    private List<Player> playerList = new ArrayList<>();

    public PlayerAdapter(@NonNull Context context, @LayoutRes List<Player> list) {
        super(context, 0 , list);
        mContext = context;
        playerList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Player currentPlayer = playerList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentPlayer.getName());

        return listItem;
    }
}
