package incorporate.games.avon.trynefaktorapp;

import android.net.Uri;

import java.net.URI;

//dataclass for our players
public class Player {

    private String name;
    private Uri photo;
    private boolean fromGallery;

    public Player(String name, Uri photo){
        this.name=name;
        this.photo=photo;
        this.fromGallery=false;
    }

    public void setFromGallery(boolean b){
        this.fromGallery=b;
    }

    public boolean getFromGallery(){
        return fromGallery;
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
