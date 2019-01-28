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
    //main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get playerlist
        database = ((PlayerList) this.getApplication()).getList();


        //check if empty, and make toast
        if(database.isEmpty()){
            Toast.makeText(MainActivity.this, "Please add a player.", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToPlayers(View v){
        Intent intent = new Intent(MainActivity.this,PlayersActivity.class);
        startActivity(intent);
    }
}
