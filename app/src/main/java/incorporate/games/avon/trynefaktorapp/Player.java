package incorporate.games.avon.trynefaktorapp;

import android.net.Uri;

import java.io.Serializable;


//dataclass for our players
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String photo;
    private boolean fromGallery;


    public Player(String name, String photo){
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

    public String getName(){
        return name;
    }
    public String getPhoto(){
        return photo;
    }

}
