package incorporate.games.avon.trynefaktorapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private void setOwnerName(){

        AlertDialog.Builder adb = new AlertDialog.Builder(QuizActivity.this);
        adb.setTitle("Owner");
        adb.setMessage("Enter the name of the owner of this phone");

        final EditText input = new EditText(QuizActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        input.setLayoutParams(lp);
        adb.setView(input);
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor;

                editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();
                editor.putString("name", input.getText().toString());
                editor.putInt("idName", 22);
                editor.apply();
            }});
        adb.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.pref:
                setOwnerName();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);


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
        img.setImageURI(Uri.parse(playerList.get(i).getPhoto()));

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

        img.setImageURI(Uri.parse(playerList.get(i).getPhoto()));

    }
}
