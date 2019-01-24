package incorporate.games.avon.trynefaktorapp;

import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class PlayerList extends Application {

    private List<Player> list;

    public PlayerList(){
        list = new LinkedList<Player>();
    }

    public void appendPlayer(Player p){
        list.add(p);
    }
    public void removePlayer(Player p){
        list.remove(p);
    }

    public List<Player> getList(){
        return list;
    }
}
