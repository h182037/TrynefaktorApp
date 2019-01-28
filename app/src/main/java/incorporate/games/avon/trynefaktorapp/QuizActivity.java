package incorporate.games.avon.trynefaktorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    ImageView img;
    Button RL;
    Button RR;
    EditText guess;
    String guessText;
    List<Player> playerList;
    int i;
    int correct;
    int tries;
    TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        correct = 0;
        tries = 0;
        i = 0;
        RL = (Button) findViewById(R.id.rotLeft);
        RR = (Button) findViewById(R.id.rotRight);
        score = (TextView) findViewById(R.id.scoreText);
        img = (ImageView) findViewById(R.id.playerImage);
        guess = (EditText) findViewById(R.id.guessInput);


        score.setText("Score: "+correct+"/"+tries);
        playerList = ((PlayerList) this.getApplication()).getList();
        //returning to main activity if the list is empty
        if(playerList.isEmpty()){
            Intent intent = new Intent(QuizActivity.this, MainActivity.class);
            startActivity(intent);
        }
        img.setImageURI(playerList.get(i).getPhoto());

        RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setRotation(img.getRotation()-90);
            }
        });
        RR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setRotation(img.getRotation()+90);
            }
        });


    }

    public void goToMain(View v){
        Intent returnIntent = new Intent(QuizActivity.this, MainActivity.class);
        startActivity(returnIntent);
    }
    //setting score and letting user know if the guess was correct or not.
    public void testAnswerMethod(View v){
        guessText = guess.getText().toString();
        tries++;
        if(guessText.toLowerCase().equals(playerList.get(i).getName().toLowerCase())){
            Toast.makeText(QuizActivity.this, "Your guess was correct.", Toast.LENGTH_SHORT).show();
            correct++;
        }else{
            Toast.makeText(QuizActivity.this, "Your guess was incorrect.", Toast.LENGTH_SHORT).show();
        }
        score.setText("Score: "+correct+"/"+tries);
        if(i == playerList.size()-1){
            i=0;
        }else{
            i++;
        }

        img.setImageURI(playerList.get(i).getPhoto());

    }
}
