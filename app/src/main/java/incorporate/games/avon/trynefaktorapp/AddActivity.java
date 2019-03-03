package incorporate.games.avon.trynefaktorapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.reflect.Modifier.TRANSIENT;

public class AddActivity extends AppCompatActivity {
        EditText nameIN;
        String imagePath;
        String name;
        Uri photoURI;
        Button getPic;
        SharedPreferences prefs;
        ImageView pic;
        static final int REQUEST_TAKE_PHOTO = 1;
    private void setOwnerName(){

        AlertDialog.Builder adb = new AlertDialog.Builder(AddActivity.this);
        adb.setTitle("Owner");
        adb.setMessage("Enter the name of the owner of this phone");

        final EditText input = new EditText(AddActivity.this);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        pic =(ImageView) findViewById(R.id.thisOne);
        getPic = (Button) findViewById(R.id.btnGallery);
        nameIN = (EditText) findViewById(R.id.nameInput);
        Button btnCamera = (Button)findViewById(R.id.btnCamera);

        //Creates and starts intent to choose and fetch image from Gallery
        getPic.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                if(nameIN.getText().toString().equals("")){
                    Toast.makeText(AddActivity.this, "Please input name, thank you.", Toast.LENGTH_SHORT).show();
                }else {
                    //Using content-provider MediaStore.
                    Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 3);
                }
            }
        });
        //Runs method to take a new picture.
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameIN.getText().toString().equals("")){
                    Toast.makeText(AddActivity.this, "Please input name, thank you.", Toast.LENGTH_SHORT).show();
                }else {
                    dispatchTakePictureIntent();
                }
            }
        });
    }

    //Callback for activityresult. Is called when intent for gallery has returned a result
    //For this purpose it is used to fetch the pictureURI from gallery.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK && null != data) {

            final Uri selectedImage = data.getData();

            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            cursor.moveToFirst();
            cursor.close();


            confirmPicture(selectedImage);
        }
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == AddActivity.RESULT_OK){
            confirmPicture(photoURI);
        }
        else{
            Toast.makeText(AddActivity.this, "Please choose a picture", Toast.LENGTH_SHORT).show();
        }

    }
    //Sets a confirm button to make sure user is happy with picture and name
    //then creates a player, appends it to list of players, and updates the stored list in preferences.
    private void confirmPicture(final Uri selectedImage) {
        pic.setImageURI(selectedImage);
        final Button myButton = new Button(this);
        myButton.setText("Confirm");

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameIN.getText().toString();

                Player player = new Player(name, selectedImage.toString());
                player.setFromGallery(true);

                prefs = getSharedPreferences("MyPrefsFile5", MODE_PRIVATE);
                Gson gson = new Gson();
                ((PlayerList) getApplication()).appendPlayer(player);
                String jsonString = gson.toJson(((PlayerList) getApplication()).getList());

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("offline", jsonString);
                editor.apply();

                nameIN.setText("");
                myButton.setVisibility(View.GONE);
            }
        });
        LinearLayout ll = (LinearLayout) findViewById(R.id.linLay);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);
    }


    //dispatching image capture
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "incorporate.games.avon.trynefaktorapp.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }



    public void goToQuiz(View v){
        Intent intent = new Intent(AddActivity.this,QuizActivity.class);
        startActivity(intent);
    }

    public void goToPlayers(View v){
        Intent intent = new Intent(AddActivity.this,PlayersActivity.class);
        startActivity(intent);
    }

    //creating file for the image capture to save in
        private File createImageFile() throws IOException {
        // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" +timeStamp +"_" ;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagePath = image.getAbsolutePath();
        return image;
    }
}
