package incorporate.games.avon.trynefaktorapp;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<Player> database;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    TextView owner;

    private void setOwnerName(){
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
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
                editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();
                editor.putString("name", input.getText().toString());
                editor.putInt("idName", 22);
                editor.apply();
                owner = (TextView) findViewById(R.id.owner);
                owner.setText("Owner: "+prefs.getString("name","Mr. NoName"));
            }});
        adb.show();
    }
    //main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        if(prefs.getString("name", "Mr. NoName").equals("Mr. NoName")){
            setOwnerName();
        }
        owner = (TextView) findViewById(R.id.owner);
        owner.setText("Owner: "+prefs.getString("name","Mr. NoName"));

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
