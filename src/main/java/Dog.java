public class Dog {
    private String name;
    private int weight;
    private boolean isWaggingTail;
    private boolean likesBaths;

    public void giveTreat() {
        isWaggingTail = true;
        weight++;
    }

    public void giveBath() {
        isWaggingTail = likesBaths;
    }

    public static void bark(String greeting) {
        System.out.println(greeting + "!");
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", isWaggingTail=" + isWaggingTail +
                ", likesBaths=" + likesBaths +
                '}';
    }

    public static void main(String[] args) {

        Dog fido = new Dog();
        fido.name = "Fido";
        fido.weight = 45;

        Dog snoopy = new Dog();
        snoopy.name = "Snoopy";
        snoopy.weight = 20;
        snoopy.likesBaths = true;

        fido.giveTreat();
        snoopy.giveTreat();
        fido.giveBath();
        snoopy.giveBath();

        System.out.println(fido);
        System.out.println(snoopy);

        bark("Woof");
        bark("Arf");
    }
}
