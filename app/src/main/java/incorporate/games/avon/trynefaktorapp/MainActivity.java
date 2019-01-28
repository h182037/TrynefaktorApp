package incorporate.games.avon.trynefaktorapp;

import android.content.Intent;

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

    }

    public void goToPlayers(View v){
        Intent intent = new Intent(MainActivity.this,PlayersActivity.class);
        startActivity(intent);
    }
}
