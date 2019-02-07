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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PlayersActivity extends AppCompatActivity {

    ListView playerListView;
    PlayerAdapter arrayAdapter;

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
    private void setOwnerName(){
        AlertDialog.Builder adb = new AlertDialog.Builder(PlayersActivity.this);
        adb.setTitle("Owner");
        adb.setMessage("Enter the name of the owner of this phone");

        final EditText input = new EditText(PlayersActivity.this);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);


        playerListView = (ListView) findViewById(R.id.playerView);
        final List<Player> playerList = ((PlayerList) this.getApplication()).getList();
        arrayAdapter = new PlayerAdapter(
                this,
                playerList);
        playerListView.setAdapter(arrayAdapter);
        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // Making items removable by clicken them in the view
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(PlayersActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete Player " + (position+1)+"?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //restraining from deleting images from your beloved gallery.
                        if(!playerList.get(positionToRemove).getFromGallery()){
                            getApplicationContext().getContentResolver().delete(Uri.parse(playerList.get(positionToRemove).getPhoto()),null,null);
                        }

                        playerList.remove(positionToRemove);
                        arrayAdapter.notifyDataSetChanged();
                        Gson gson = new Gson();
                        SharedPreferences prefs = getSharedPreferences("MyPrefsFile4", MODE_PRIVATE);
                        ((PlayerList) getApplication()).setList(playerList);
                        String jsonString2 = gson.toJson(((PlayerList) getApplication()).getList());

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("offline", jsonString2);
                        editor.commit();

                    }});
                adb.show();
            }
        });

    }

    public void goToQuiz(View v){
        Intent intent = new Intent(PlayersActivity.this,QuizActivity.class);
        startActivity(intent);
    }

    public void goToAdd(View v){
        Intent intent = new Intent(PlayersActivity.this, AddActivity.class);
        startActivity(intent);
    }
}
