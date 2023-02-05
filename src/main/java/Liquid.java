public class Liquid
{
    private int currentTemp;
    private int boilingPoint;

    public boolean isBoiling()
    {
        return currentTemp >= boilingPoint;
    }

    public static void main(String[] args) {
        Liquid potOfMilk = new Liquid();
        potOfMilk.boilingPoint = 202;
        potOfMilk.currentTemp = 198;

        Liquid potOfWater = new Liquid();
        potOfWater.boilingPoint = 212;
        potOfWater.currentTemp = 230;

        System.out.println( potOfWater.isBoiling() );  //#1
        System.out.println( potOfMilk.isBoiling() );   //#2
        potOfMilk.currentTemp = 205;
        System.out.println( potOfMilk.isBoiling() );   //#3
    }
}
