package incorporate.games.avon.trynefaktorapp;

import android.net.Uri;

import java.io.Serializable;


//dataclass for our players
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String photo;
    private boolean fromGallery;
    private String PhotoTitle;


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

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getName(){
        return name;
    }
    public String getPhoto(){
        return photo;
    }

    public String getPhotoTitle() {
        return PhotoTitle;
    }

    public void setPhotoTitle(String photoTitle) {
        PhotoTitle = photoTitle;
    }
}
