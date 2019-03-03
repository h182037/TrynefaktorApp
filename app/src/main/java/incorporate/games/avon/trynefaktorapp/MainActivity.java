package incorporate.games.avon.trynefaktorapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    PlayerList database;
    SharedPreferences prefs;
    TextView owner;

    //Creating an alertdialog box to set owner name in shared preferences
    //This is duplicated in every activity (could be turned into fragment?)
    private void setOwnerName(){
        //Building dialog
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Owner");
        adb.setMessage("Enter the name of the owner of this phone");

        //Creating input field to write the owner name
        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT

        );

        input.setLayoutParams(lp);
        adb.setView(input);
        //Setting buttons to interact with dialog box.
        //Ok-button applies input string to preferences.
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor;

                editor = getSharedPreferences("MyPrefsFile5", MODE_PRIVATE).edit();
                editor.putString("name", input.getText().toString());
                editor.putInt("idName", 22);

                //Using apply instead of commit so that it does this asynchronously.
                editor.apply();
                //Updating textview in UI to show the new owner name.
                owner = findViewById(R.id.owner);
                owner.setText("Owner: "+prefs.getString("name","Mr. NoName"));
            }});
        input.setHint("name");
        adb.show();
    }
    //inflating options when toolbar button is pressed.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool_menu, menu);
        return true;
    }

    //Calls setOwnerName() when option is pressed.
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
        setContentView(R.layout.activity_main);

        //sets toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        //Sets owner name in UI to owner name in preferences.
        prefs = getSharedPreferences("MyPrefsFile5", MODE_PRIVATE);
        owner = (TextView) findViewById(R.id.owner);
        owner.setText("Owner: "+prefs.getString("name","Mr. NoName"));


        Gson gson = new Gson();
        //get list of players (faces and names for the quiz) from preferences.
        String jsonString = prefs.getString("offline","");
        //if preferences does not hold any data we initialize the list and store it in preferences.
        if(jsonString.equals("")){
            ((PlayerList) this.getApplication()).init();
            //converts list object to jsonString
            String jsonString2 = gson.toJson(((PlayerList) getApplication()).getList());
            //Stores json string in preferences.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("offline", jsonString2);
            editor.apply();

        }else {

            //Converts jsonString to List object and sets application list.
            Type listType = new TypeToken<List<Player>>(){}.getType();
            List<Player> tmp = gson.fromJson(jsonString, listType);
            ((PlayerList) this.getApplication()).setList(tmp);
        }


    database = ((PlayerList) this.getApplication());
        //check if empty, and make toast
        if(database.getList().isEmpty()){
            Toast.makeText(MainActivity.this, "Please add a player.", Toast.LENGTH_SHORT).show();
        }
    }

    //Intent to navigate to PlayersActivity
    public void goToPlayers(View v){
        Intent intent = new Intent(MainActivity.this,PlayersActivity.class);
        startActivity(intent);
    }
}
