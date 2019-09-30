# Java Training - Testing

## Build & run tests (unit, property)
```
./gradlew build
```

## Import in IDE

No matter the IDE (Intellij or Eclipse), Lombok must be installed in it.

### Intellij (recommended)
* Import Project
* Select `build.gradle.kts`
  * Select `Use default Gradle wrapper (recommended)`
  * Leave everything else as is

### Eclipse
* Import projects...
* Gradle
* Existing Gradle Project
* Pick the project root directory (e.g. `/Users/Julien/code/training-java-testing`)
* Finish

## Exercises

### Part I - Unit & Mutation testing

A `Elf` has a valid price when it is over 0 and lower than 10,000. 

We have a `Elf#hasValidPrice` function which should return `true` whenever the price is valid, false otherwise. 

```java
boolean hasValidPrice() {
  return price.compareTo(ZERO) > 0 && price.compareTo(valueOf(10000)) <= 0;
}
```

#### Step 1 - Write unit tests

Write unit tests to cover this method:
- Run `git checkout part-1-step-1 -f`
- Create a unit test class `ProductTest.java` within tests directory (`src/test/java/com/decathlon/training/testing`)
- Write your unit tests thanks to `Junit` using following structure:
```java
@Test
public void givenProductWithPrice_hasValidPrice_returnsTrueOrFalse() {
  //...
}
```

#### Step 2 - Code coverage 

How do you assess your code coverage? 

- Run `git checkout part-1-step-2 -f`
- Run `./gradlew test jacocoTestReport`
- Open generated jacoco reports: `open build/reports/jacoco/test/html/com.decathlon.training.testing/Elf.html`

What do you observe? What is your conclusion looking at this report?

- Open `ProductTest.java` and have a look at the unit tests
- Is there something wrong?

#### Step 3 - Add assertions

Edit `ProductTest.java` to add assertion statements to validate the method result

Is it enough? Do you think your method is fully covered (all test cases are handled)?

#### Step 4 - Introduction to mutation testing

- Run `git checkout part-1-step-4 -f`
- Add `Pitest` to your build configuration (see `build.gradle.kts`)
- Configure `Pitest` to be ran with tests classes as `com.decathlon.training.testing.*Test`
- Run `./gradlew pitest`
- Open generated report: `open build/reports/pitest/com.decathlon.training.testing/Elf.java.html`

![Pitest report 1](doc/pitest-report-1.png)
What do you observe? What is your conclusion looking at this report?

#### Step 5 - Fix missing test cases

- Run `git checkout part-1-step-5 -f`
- Add missing unit tests to ensure a mutation coverage of 100%

Expected report: 

![Pitest report 2](doc/pitest-report-2.png)

#### [Bonus] Step 6 - Write unit tests for `Troll#removeOneProductFromCart(Elf)`

- Run `git checkout part-1-step-6 -f`
- Write unit tests for this method in `CustomerTest.java`
- Check your line & mutation coverage is 100%

### Part II - Property testing
Property Based Testing (a.k.a. PBT) is about generating tests instead of manually writing them. Unlike unit tests where you know what goes in and what comes out (a.k.a. oracle tests), you assess properties that should always be true. The PBT library checks for arbitrary inputs that the property is true.

In Java, we use `JUnit-QuickCheck` library to write and run Property Based tests.

#### Step 1 - Configuration and Invariance
- Run `git checkout part-2-step-1 -f`
- For a simpler start, we already configured the build dependencies and created generators for `Elf` and `Troll` in the `test` module
- Create a PBT file `CustomerProp.java` next to `CustomerTest.java`:
```java
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class CustomerProp {
  
  @Property
  public void invariance(Troll customer) {
    // ...
  }
}
```
- Did you notice the property test takes a `Troll` as input? That's where PBT shines! The library will run this test 100 times by default, and each time will pass a random `Troll` to it. We no longer care about building input data!
- The first property test we will write aims to assess **Invariance property**: it means a property should always be true even if the input varies (e.g. the `Troll`)
- As an example, no matter the customer, the total amount in his cart is always >= 0 (i.e. is never negative). Write a test (using usual `AssertJ` assertions, just like in unit tests) to check that
- Does the test pass? You can run it just like unit tests, via Intellij or in CLI with `./gradlew build`
- What would the same check with regular unit tests look like?

For your information, most unit tests can actually be converted to **Invariance properties**

#### Step 2 - Inverse
**Inverse properties** check that it's possible to transform some input to an output and back to the original input, no matter the input. This is a useful property because it guarantees some functions **don't lose information** and/or are **consistent**.

- Run `git checkout part-2-step-2 -f`
- For any `customer` and any `elf`, if we add `elf` to `customer`'s cart and then remove it, what should be the result?
- Write an **inverse property** test to check that

Testing it will ensure that `addProductToCart` and `removeOneProductFromCart` are consistent.

#### Step 3 - Analogy
**Analogous properties** check that there are at least 2 different ways from any input to reach an output. This is a useful property because it guarantees some functions are **consistent** (can also be useful for **refactors**) 

- Run `git checkout part-2-step-3 -f`
- For any `customer`, any `elf` and any positive `quantity`, what should be the difference between:
  - adding a single `elf` to `customer`'s cart and repeating this operation `quantity` times
  - adding in a single operation `quantity` units of `elf` to `customer`'s cart?
- Write an **analogous property** test to check that

This ensures that `addProductToCart` and `addProductsToCart` are consistent.

#### Step 4 - Idempotence
**Idempotent properties** check that running a function once or several times leads to exactly the same result, i.e. an idempotent function brings to a stable state from which this function becomes useless.

- Run `git checkout part-2-step-4 -f`
- For any `customer` and any `elf`, once we removed all `elf`s from the `customer`'s cart, what should happen if we remove all `elf`s again from the cart?
- Write an **idempotent property** test to check that

This ensures that `removeAllProductsFromCart` brings the cart to a stable state.

#### [Bonus] Step 5 - Metamorphism
**Metamorphic properties** check that running a function with variants of the same input should lead to equal or consistent outputs. E.g. if the input is multiplied by 2, is the output also multiplied by 2? Divided by 2? The same?

- Run `git checkout part-2-step-5 -f`
- For any `customer` and any `elf`, what should the cart amount of `customer` be compared to the cart amount of `customer` after adding `elf` to it?
- Write a **metamorphic property** test to check that

This ensures that `getCartAmount` correctly grows when the cart amount is increased.

#### [Bonus] Step 6 - Injection
**Injective properties** check that different inputs lead to different outputs, i.e. there aren't 2 different inputs that lead to the same output, i.e. each output has at most 1 input.

- Run `git checkout part-2-step-6 -f`
- For any `customer` and any 2 products `product1` and `product2`, assuming `product1` is different from `product2`, `customer` after adding `product1` to his cart must be different from `customer` after adding `product2` to his cart
- Write an **injective property** test to check that

This ensures that `addProductToCart` always updates the provided customer in a unique way.
