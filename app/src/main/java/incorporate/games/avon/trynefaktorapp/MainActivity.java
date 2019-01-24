package incorporate.games.avon.trynefaktorapp;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    LinkedList<Player> database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new LinkedList<Player>();
    }
    public void goToQuiz(View v){
        Intent intent = new Intent(MainActivity.this,QuizActivity.class);
        startActivity(intent);
    }
    public void goToPlayers(View v){
        Intent intent = new Intent(MainActivity.this,PlayersActivity.class);
        startActivity(intent);
    }
}
