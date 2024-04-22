import java.util.ArrayList;

public class Team {
    public static int num_teams=0;
    public String name;
    public int rank=0;
    public Player captain;
    public ArrayList<Player> members= new ArrayList<>();

    public Team() {
        num_teams++;
    }

    public Team(String n) {
        name = n;
        num_teams++;
    }

    public Team(String n, int r) {
        name = n;
        rank = r;
        num_teams++;
    }

    public Team(String n, int r, Player p) {
        name = n;
        rank = r;
        num_teams++;
        members.add(p);
    }

    public void setName(String n) { name = n; }

    public String getName() { return name; }

    public void setRank(int r) { rank = r; }

    public int getRank() { return rank; }

    public int getSize() { return members.size(); }

    public void setCaptain(Player p) { captain = p; }

    public Player getCaptain() { return captain; }

    public void addMember(Player p) { members.add(p); }

    public void removeMember(String n) {
        for (int i = 0; i < getSize(); i++) {
            if (members.get(i).name == n) members.remove(i);
        }
    }

    public void printTeam() {
        System.out.println(name + ", " +getSize()+" 명, "+ rank+"등");
        for (int i = 0; i < getSize(); i++) System.out.println(members.get(i).name);
        System.out.println("captin is "+captain.name);
    }
}
