package incorporate.games.avon.trynefaktorapp;

import android.net.Uri;

import java.net.URI;

public class Player {

    private String name;
    private Uri photo;

    public Player(String name, Uri photo){
        this.name=name;
        this.photo=photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }
    public String getName(){
        return name;
    }
    public Uri getPhoto(){
        return photo;
    }
}
