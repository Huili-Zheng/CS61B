package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

class Dog implements Comparable<Dog> {
    private static class NaturalComparator implements Comparator<Dog> {

        @Override
        public int compare(Dog o1, Dog o2) {
            return o1.compareTo(o2);
        }
    }

    private static class NameComparator implements Comparator<Dog> {

        @Override
        public int compare(Dog o1, Dog o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    public static Comparator<Dog> getNaturalComparator() {
        return new NaturalComparator();
    }

    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }

    public String name;
    private int size;

    public Dog(String n, int s) {
        name = n;
        size = s;
    }

    @Override
    public int compareTo(Dog uddaDog) {
        //assume nobody is messing up and giving us
        //something that isn't a dog.
        return size - uddaDog.size;
    }

    public void bark() {
        System.out.println(name + " says: bark");
    }
}

public class MaxArrayDequeTest {

    @Test
    public void maxTest() {
        MaxArrayDeque<Dog> dogs = new MaxArrayDeque<>(Dog.getNameComparator());
        dogs.addLast(new Dog("Puffy", 8));
        dogs.addLast(new Dog("Nico", 6));
        dogs.addLast(new Dog("Giant", 18));
        Dog maxDog = dogs.max();

        assertEquals(maxDog.name, "Puffy");
    }

    @Test
    public void maxWithArgumentTest() {
        MaxArrayDeque<Dog> dogs = new MaxArrayDeque<>(Dog.getNameComparator());
        dogs.addLast(new Dog("Puffy", 8));
        dogs.addLast(new Dog("Nico", 6));
        dogs.addLast(new Dog("Giant", 18));
        Dog maxDog = dogs.max(Dog.getNaturalComparator());

        assertEquals(maxDog.name, "Giant");
    }

}
