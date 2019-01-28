package incorporate.games.avon.trynefaktorapp;

import android.content.Intent;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Player> database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = ((PlayerList) this.getApplication()).getList();
        if(database.isEmpty()){
            Toast.makeText(MainActivity.this, "Please add a player.", Toast.LENGTH_SHORT).show();
        }
        Uri uri1 = Uri.parse("android.resource://incorporate.games.avon.trynefaktorapp/"+R.drawable.lil);
        Uri uri2 = Uri.parse("android.resource://incorporate.games.avon.trynefaktorapp/"+R.drawable.bub);
        Player p1 = new Player("lil", uri1);
        p1.setFromGallery(true);
        Player p2 = new Player("bub", uri2);
        p2.setFromGallery(true);
        database.add(p1);
        database.add(p2);

    }

    public void goToPlayers(View v){
        Intent intent = new Intent(MainActivity.this,PlayersActivity.class);
        startActivity(intent);
    }
}
