import java.util.ArrayList;

public class Team {
    public static int num_teams=0;
    public String name;
    public int rank = 0;
    public Player captain;
    public ArrayList<Player> members= new ArrayList<>();

    public Team() {
        num_teams++;
    }

    public Team(String name) {
        this.name = name;
        num_teams++;
    }

    public Team(String name, int rank) {
        this.name = name;
        this.rank = rank;
        num_teams++;
    }

    public Team(String name, int rank, Player player) {
        this.name = name;
        this.rank = rank;
        num_teams++;
        members.add(player);
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setRank(int rank) { this.rank = rank; }

    public int getRank() { return rank; }

    public int getSize() { return members.size(); }

    public void setCaptain(Player player) { captain = player; }

    public Player getCaptain() { return captain; }

    public void addMember(Player player) { members.add(player); }

    public void removeMember(String name) {
        for (int i = getSize()-1; i >= 0; i--) {
            if (members.get(i).name.equals(name)) members.remove(i);
        }
    }

    public void printTeam() {
        System.out.println(name + ", " +getSize()+" 명, "+ rank+" 등");
        for (int i = 0; i < getSize(); i++) System.out.println(members.get(i).name);
        System.out.println("captin is "+captain.name);
    }
}
