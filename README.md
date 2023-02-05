# This

## Learning Goals

- Introduce the keyword `this`.
- Define `this` as an instance method's invocation object.
- Access an instance variable using `this`.
- Use the debugger to step into methods.

## Code Along

Fork and clone this lesson to run the example code.

## The `this` keyword

Let's update the `Dog` class as follows:

- Define  instance variables `name`, `weight`, `isWaggingTail`, and `likesBaths`.
- The `giveTreat` method assigns `isWaggingTail` to `true` and increments the `weight` value.
- The `giveBath` method assigns`isWaggingTail` based on the value stored in `likesBaths`:

We'll also update the `main` method to set each dog's name and weight.
Fido does not like baths (i.e. default value of `false`), but Snoopy does.

```java
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
```

Consider the object state prior to executing the statement `fido.giveTreat();`:

![dog state](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/dog_this_0.png)

Take a close look at the `giveTreat()` instance method in the `Dog` class.
Notice the method *does not* take a parameter that references a dog.
If we create two dogs, how does Java know which dog's `isWaggingTail`
and `weight` variables to update?  

```java
public void giveTreat() {
    isWaggingTail = true;
    weight++;
}
```

## This

Every Java instance method has a special variable named `this`.
Java assigns `this` to reference  the object used to call the method,
which is called the **invocation object**.

| Instance Method Call  | `this` reference | Java Visualizer                                                                                                                              |
|-----------------------|------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| `fido.giveTreat();`   | fido             | ![this fido](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/this_fido.png) <br>`this` points to same dog as `fido`       |
| `snoopy.giveTreat();` | snoopy           | ![this snoopy](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/this_snoopy.png) <br>`this` points to same dog as `snoopy` |

The `giveTreat` method will assign the `isWaggingTail` and `weight` variables
of the invocation object referenced by `this`.  We can imagine
the compiler evolves the statements as shown:

| Programmer writes:      | Compiler generates:          |
|-------------------------|------------------------------|
| `isWaggingTail = true;` | `this.isWaggingTail = true;` |
| `weight++;`             | `this.weight++;`             |


We can in fact rewrite all instance methods, but not the static `bark` method, to
explicitly use dot notation and `this` as shown below.  Static methods do not have
a `this` variable because they are not called using an object.  For example,
notice how the `main` method can call the `bark` method without `fido` or `snoopy`.

```java
public class Dog {
    private String name;
    private int weight;
    private boolean isWaggingTail;
    private boolean likesBaths;

    public void giveTreat() {
        this.isWaggingTail = true;
        this.weight++;
    }

    public void giveBath() {
        this.isWaggingTail = this.likesBaths;
    }

    public static void bark(String greeting) {
        System.out.println(greeting + "!");
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + this.name + '\'' +
                ", weight=" + this.weight +
                ", isWaggingTail=" + this.isWaggingTail +
                ", likesBaths=" + this.likesBaths +
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
```

However, we usually  omit `this`  inside instance methods
and rely on the compiler to add it.  The only time we need
to explicitly use `this` is when the  method contains a parameter or local
variable with the same name as an instance variable, which
is a situation we'll encounter when we define constructor
methods.

## Debugging Instance Methods  (Code Along)

Let's use the debugger to step into an instance method and see how it
changes the state of the object referenced by `this`. 

So far we've mostly used IntelliJ's "Step Over" button while debugging.
If the current line of code contains a method call, the "Step Over" button
executes the method call and then proceeds to the next line of code.

![step over](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/step_over.png)

However, we would like to "Step Into" the instance methods to
see how `this` is used to access the instance variables. 

![step into](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/step_into.png)


1. Set a breakpoint at the line of code `fido.giveTreat();` and launch the debugger.    
   ![breakpoint](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/fido_breakpoint.png)
2. Switch to the Java Visualizer view. The visualizer shows the current object state *prior* to
   executing `fido.giveTreat()`.    
   ![dog state](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/dog_this_0.png)
3. Press "Step Into" to execute the method call `fido.giveTreat();`.
   Notice the method `getTreat()` is added to the call stack and the variable `this` references the same object as
   `fido`. Since "Step Into" was pressed, the debugger stops at the first line
   of code in the `getTreat()` method.    
   ![fido debug](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/debugger_this_fido.png)
4. You can press either "Step Over"  or "Step Into" to execute the code in the `giveTreat()`
   method since neither line makes a method call.  Keep in mind that the compiler
   evolves the code to use `this` to reference the instance variables:    
    
   | Programmer writes:      | Compiler generates:          |
   |-------------------------|------------------------------|
   | `isWaggingTail = true;` | `this.isWaggingTail = true;` |
   | `weight++;`             | `this.weight++;`             |
   
   Step twice to execute the two lines of code. The object referenced by `this` is updated:     
   ![fido debug update state](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/debugger_this_fido2.png)
   
5. Step again to complete the `getTreat()` method and return to the `main` method.
   Notice the `giveTreat` method has been removed from the call stack and the
   debugger has stopped at the next line of code `snoopy.giveTreat();`.
   ![fido givetreat method returned](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/fido_givetreat_complete.png)

6. Press "Step Into" to execute `snoopy.giveTreat()` and stop at the first
   line in the method.  The variable `this` points at the same object as `snoopy`:     
   ![debugger this snoopy](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/debugger_this_snoopy.png)     

   Step twice to execute the two lines of code. The object referenced by `this` is updated:     
   ![snoopy debug update state](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/debugger_this_snoopy2.png)

7. Press "Step Into" to return to the `main` method.
8. Continue to press "Step Into" to execute the remaining method calls in the `main` method.
   Observe how  `this` is assigned to the invocation object in each call to
   `giveBath` and `toString` (implicitly called in the print statement).  Notice
    the static method `bark` does not have a `this` reference when called.


The variable `this` is often referred to as an *implicit* parameter
because the instance method does not *explicitly* declare
it as a parameter.   An implicit parameter is not defined as part of the method signature,
but it is implied by the class declaring an instance method (i.e. a non-static method).

Since Java uses `this` as a keyword for the implicit parameter's name,
we can't define a variable named `this`.  We also can't assign
`this` a value.



### Comprehension Check

<details>
    <summary>Given the following <code>Liquid</code> class, what is printed by each
   of the three calls to the <code>isBoiling()</code> method contained in the print statements?</summary>

  <p>The code prints:<br>
  true<br>false<br>true
  </p>

  <p>The value <code>true</code> is returned from the first method call<br>
  <code>System.out.println( potOfWater.isBoiling() );  //#1</code>.
  <br>Notice IntelliJ  displays the value <code>true</code> next to the expression <code>currentTemp >= boilingPoint</code>
  to show the return value.
  </p>

  <img src="https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/this_liquid1.png">

  <p>The value <code>false</code> is returned from the second method call<br><code>System.out.println( potOfMilk.isBoiling() );   //#2</code>.</p>

  <img src="https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/this_liquid2.png">

  <p>After updating the <code>potOfMilk</code> temperature, the value <code>true</code>
  is returned from the third method call<br><code>System.out.println( potOfMilk.isBoiling() );   //#3</code>.</p>

  <img src="https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/this_liquid3.png">

</details>

```java
public class Liquid {
   private int currentTemp;
   private int boilingPoint;

   public boolean isBoiling() {
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
```



## Static Methods

Since a static method is not called using an object, it
can't access instance variables nor call instance methods
unless it has an explicit reference to a class instance. 

For example, the `main` method shown below assigns the variable `potOfMilk` to reference
an instance of the `Liquid` class, and then uses the reference to access an
instance variable and call an instance method. This shows an example of a static
method accessing an instance variable and method through an object reference:

```java
public static void main(String[] args) {
    Liquid potOfMilk = new Liquid();
    potOfMilk.boilingPoint = 202;
    ...
    System.out.println( potOfWater.isBoiling() );  //#1
}
```

However, a compile-time error results if the `main` method tries
to access the instance variable or method without using an object reference.
The `main` method is a static method and is called without an object
reference, therefore the compiler *can't* replace the code with `this.boilingPoint` or
`this.isBoiling()` like it can in an instance method.

```java
public static void main(String[] args) {
    boilingPoint = 202;   //ERROR!
    ...
    System.out.println( isBoiling() );  //ERROR!
}
```

## Conclusion

Because an instance method is invoked using an object reference,
Java provides an implicit parameter named `this`, which
can be used to reference the invocation object within the method body.

An instance method can use dot notation to preface an instance variable or instance
method call with `this`, for example `this.isWaggingTail`.  If we omit
the dot notation, the compiler will automatically add it.

Static methods are not invoked using a class instance and therefore do
not have the implicit parameter `this`.

## Resources

- [Java Tutorial - this](https://docs.oracle.com/javase/tutorial/java/javaOO/thiskey.html)
