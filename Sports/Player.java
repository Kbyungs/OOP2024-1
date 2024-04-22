
public class Player {
    static int num_Players;
    public String name;
    private int age;

    public Player() {
        num_Players++;
    }

    public Player(String n) {
        name = n;
        num_Players++;
    }
    public Player(String n, int a) {
        name = n;
        age = a;
        num_Players++;
    }

    public void setName(String n) { name = n; }

    public String getName( ) { return name; }

    public void setAge(int a) { age = a; }

    public int getAge( ) { return age; }
}
