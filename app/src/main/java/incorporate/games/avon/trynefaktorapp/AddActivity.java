package incorporate.games.avon.trynefaktorapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
        EditText nameIN;
        String imagePath;
        String name;
        Uri photoURI;
        Button getPic;
    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getPic = (Button) findViewById(R.id.btnGallery);
        nameIN = (EditText) findViewById(R.id.nameInput);
        Button btnCamera = (Button)findViewById(R.id.btnCamera);

        getPic.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                if(nameIN.getText().toString().equals("")){
                    Toast.makeText(AddActivity.this, "Please input name, thank you.", Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 3);
                }
            }
        });

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
    // result of getting picture from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            name = nameIN.getText().toString();
            Player player = new Player(name, selectedImage);
            player.setFromGallery(true);
            ((PlayerList) this.getApplication()).appendPlayer(player);
        }else{
            Toast.makeText(AddActivity.this, "Please choose a picture", Toast.LENGTH_SHORT).show();
        }

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

                name = nameIN.getText().toString();
                Player player = new Player(name, photoURI);
                ((PlayerList) this.getApplication()).appendPlayer(player);
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
