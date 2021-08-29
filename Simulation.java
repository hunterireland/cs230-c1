import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * A simple simulation of a society with two modes- 
 * society, and anarchy, determined by command line argument
 * 
 * @author hunterireland
 */
public class Simulation {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments...");
            System.out.println("Usage: $ java Simulation (-a for anarchy or -s for society mode)");
            System.exit(1);
        }

        Mode mode = null;
        switch (args[0]) {
            case "-a":
                mode = Mode.ANARCHY;
                System.out.println("\nRunning in anarchy mode...");
                break;
            case "-s":
                mode = Mode.SOCIETY;
                System.out.println("\nRunning in society mode...");
                break;
            default:
                System.out.println("Unknown argument specified...");
                System.out.println("Usage: $ java Simulation (-a for anarchy or -s for society mode)");
                System.exit(1);
                break;
        }

        Person hunter = new Person(Skill.HUNTER);
        List<Person> alive = new LinkedList<Person>(Arrays.asList(
            new Person(Skill.DOCTOR), 
            new Person(Skill.FARMER), 
            new Person(Skill.CARPENTER), 
            hunter));

        Disaster[] disasters = Disaster.values();
        Random random = new Random();

        int days;
        for (days = 1; days < 366; days++) {
            for (Person p : alive) {
                p.applySkill(alive, mode, days);
            }
            for (Person p : alive) {
                p.eat();
            }
            
            Disaster disaster = disasters[random.nextInt(disasters.length)];
            for (Person p : alive) {
                p.applyDisaster(disaster, hunter, mode);
            }

            System.out.println(String.format("\n\nDay %d: %s", days, disaster == Disaster.NONE ? "" : disaster.name().toLowerCase()));
            System.out.println("-".repeat(30));

            List<Person> dead = new ArrayList<Person>();
            for (Person p : alive) {
                System.out.println(p.getStatus());
                if (p.isDead()) {
                    dead.add(p);
                }
            }

            if (!dead.isEmpty()) {
                System.out.println();
                for (Person p : dead) {
                    alive.remove(p);
                    System.out.println(p.getSkill().name().toLowerCase() + " is dead");
                }
            }

            if (alive.isEmpty()) {
                System.out.println("Everybody is dead");
                break;
            }
        }

        if (days == 366) {
            System.out.println("\nSociety is stable. Simulation ended.");
            days = 365;
        }
        System.out.println("\n" + days + " days\n");
    }
}
