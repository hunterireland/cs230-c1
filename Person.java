import java.util.List;

/**
 * A Person that will live in a society for
 * a simple simulation
 * 
 * @author hunterireland
 */
public class Person {
    private Skill skill;
    private int health;
    private int food;
    private int shelter;

    /** Default person constructor */
    public Person(Skill skill) {
        health = food = shelter = 10;
        this.skill = skill;
    }

    /**
     * Applies the Person's specific skill determined by the given mode
     * @param mode of simulation
     */
    public void applySkill(List<Person> people, Mode mode, int day) {
        switch (skill) {
            case DOCTOR:
                if (food == 1) {
                    food++;
                } else {
                    if (mode == Mode.SOCIETY) {
                        for (Person p : people) {
                            p.health += 2;
                        }
                    } else {
                        health += 2;
                    }
                }
                break;
            case FARMER:
                if (day % 3 == 0) {
                    if (mode == Mode.SOCIETY) {
                        for (Person p : people) {
                            p.food += 3;
                        }
                    } else {
                        food += 3;
                    }
                }
                break;
            case CARPENTER:
                if (food == 1) {
                    food++;
                } else {
                    if (mode == Mode.SOCIETY) {
                        for (Person p : people) {
                            p.shelter++;
                        }
                    } else {
                        shelter += 2;
                    }
                }
                break;
            case HUNTER:
                double d = Math.random();
                if (d < 0.2) { // meat found
                    if (mode == Mode.SOCIETY) {
                        for (Person p : people) {
                            p.food += 2;
                        }
                    } else {
                        food += 2;
                    }
                }
                break;
        }
    }

    /** Decrements the food attribute of each person */
    public void eat() {
        food--;
    }

    /**
     * Applies a disaster with a given name to the Person
     * @param Disaster to be applied
     */
    public void applyDisaster(Disaster disaster, Person hunter, Mode mode) {
        switch (disaster) {
            case HURRICANE:
                if (shelter <= 0) {
                    health -= 5;
                }
                shelter -= 3;
                break;
            case FAMINE:
                food -= 2;
                break;
            case DISEASE:
                health -= 2;
                break;
            case WOLVES:
                if (mode == Mode.SOCIETY && hunter.health > 0 || skill == Skill.HUNTER) {
                    health--;
                } else {
                    health -= 3;
                }
                break;
            case NONE:
                break;
        }
    }

    /** Returns true if the Person is out of health or food, false otherwise */
    public boolean isDead() {
        return health == 0 || food == 0;
    }

    /** Returns a string containing the skill and attributes for a Person */
    public String getStatus() {
        return String.format("%s : %d %d %d", skill, health > 0 ? health : 0, food > 0 ? food : 0, shelter > 0 ? shelter : 0);
    }
}