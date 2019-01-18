package incorporate.games.avon.trynefaktorapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class PlayersActivity extends AppCompatActivity {

    ListView playerListView;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        playerListView = (ListView) findViewById(R.id.playerView);
        final List<String> playerList = new ArrayList<String>();
        playerList.add("Sondrus");
        playerList.add("Snudre");
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                playerList);
        playerListView.setAdapter(arrayAdapter);
        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(PlayersActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete Player " + (position+1)+"?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        playerList.remove(positionToRemove);
                        arrayAdapter.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });

    }


    public void goToAdd(View v){
        Intent intent = new Intent(PlayersActivity.this, AddActivity.class);
        startActivity(intent);
    }
}
