package incorporate.games.avon.trynefaktorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PlayersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
    }

    public void goToAdd(View v){
        Intent intent = new Intent(PlayersActivity.this, AddActivity.class);
        startActivity(intent);
    }
}
