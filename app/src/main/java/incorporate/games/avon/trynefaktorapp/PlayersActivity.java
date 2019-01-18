package incorporate.games.avon.trynefaktorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class PlayersActivity extends AppCompatActivity {

    ListView playerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        playerListView = (ListView) findViewById(R.id.playerView);
        List<String> playerList = new ArrayList<String>();
        playerList.add("Sondrus");
        playerList.add("Snudre");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                playerList);
        playerListView.setAdapter(arrayAdapter);
    }

    public void goToAdd(View v){
        Intent intent = new Intent(PlayersActivity.this, AddActivity.class);
        startActivity(intent);
    }
}
