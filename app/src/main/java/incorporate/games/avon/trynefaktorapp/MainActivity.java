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

    private void setOwnerName(){

        final AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Owner");
        adb.setMessage("Enter the name of the owner of this phone");

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        input.setLayoutParams(lp);
        adb.setView(input);
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Enter name.", Toast.LENGTH_SHORT).show();
                    owner = findViewById(R.id.owner);
                    owner.setText("Mr. NoName");
                }else {
                    SharedPreferences.Editor editor;
                    editor = getSharedPreferences("MyPrefsFile5", MODE_PRIVATE).edit();
                    editor.putString("name", input.getText().toString());
                    editor.putInt("idName", 22);
                    editor.apply();
                    owner = findViewById(R.id.owner);
                    owner.setText("Owner: " + prefs.getString("name", "Mr. NoName"));
                }
            }});
        input.setHint("name");
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
    //main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        prefs = getSharedPreferences("MyPrefsFile5", MODE_PRIVATE);
        owner = (TextView) findViewById(R.id.owner);
        owner.setText("Owner: "+prefs.getString("name","Mr. NoName"));

        Gson gson = new Gson();
        //get playerlist
        String jsonString = prefs.getString("offline","");
        if(jsonString.equals("")){
            ((PlayerList) this.getApplication()).init();
            String jsonString2 = gson.toJson(((PlayerList) getApplication()).getList());

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("offline", jsonString2);
            editor.apply();

        }else {
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


    public void goToPlayers(View v){
        Intent intent = new Intent(MainActivity.this,PlayersActivity.class);
        startActivity(intent);
    }
}
