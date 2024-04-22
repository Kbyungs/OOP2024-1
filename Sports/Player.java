
public class Player {
    static int num_Players;
    public String name;
    private int age;

    public Player() { num_Players++; }

    public Player(String name) {
        this.name = name;
        num_Players++;
    }
    public Player(String name, int age) {
        this.name = name;
        this.age = age;
        num_Players++;
    }

    public void setName(String name) { this.name = name; }

    public String getName( ) { return name; }

    public void setAge(int age) { this.age = age; }

    public int getAge( ) { return age; }
}
