
import java.util.Scanner;

public class Mechanics_project2 {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);


        float m0 = 0;
        float m1, m2, m3;
        float friction1, friction2, friction3;
        float gravity = 10;
        float f;


        System.out.println("Insert m1");
        m1 = myObj.nextFloat();


        System.out.println("Insert m2");
        m2 = myObj.nextFloat();


        System.out.println("Insert m3");
        m3 = myObj.nextFloat();

        System.out.println("Insert friction 1");
        friction1 = myObj.nextFloat();


        System.out.println("Insert friction 2");
        friction2 = myObj.nextFloat();


        System.out.println("Insert friction 3");
        friction3 = myObj.nextFloat();
    }
    
    private boolean checkIfDataIsInsertedInRange(){
        //TO BE IMPLEMENTED
        return false;
    }
}

