package incorporate.games.avon.trynefaktorapp;

import android.app.Application;
import android.net.Uri;

import java.util.LinkedList;
import java.util.List;


//our database
public class PlayerList extends Application {

    private List<Player> list;

    public PlayerList(){

        list = new LinkedList<Player>();
        //initializing list with lil bub

    }
    public void init(){
        Uri uri1 = Uri.parse("android.resource://incorporate.games.avon.trynefaktorapp/"+R.drawable.lil);
        Uri uri2 = Uri.parse("android.resource://incorporate.games.avon.trynefaktorapp/"+R.drawable.bub);
        Player p1 = new Player("lil", uri1.toString());
        p1.setFromGallery(true);
        Player p2 = new Player("bub", uri2.toString());
        p2.setFromGallery(true);
        appendPlayer(p1);
        appendPlayer(p2);
    }

    public void appendPlayer(Player p){
        list.add(p);
    }
    public void removePlayer(Player p){
        list.remove(p);
    }
    public void setList(List<Player> l){
        list = l;
    }
    public List<Player> getList(){
        return list;
    }
}
