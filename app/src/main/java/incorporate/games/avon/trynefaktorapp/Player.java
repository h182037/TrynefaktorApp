package incorporate.games.avon.trynefaktorapp;

public class Player {

    private String name;
    private String photo;

    public Player(String name, String photo){
        this.name=name;
        this.photo=photo;
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
}
