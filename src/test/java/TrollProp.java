import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import io.vavr.collection.List;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

/**
 * Welcome to property tests! I'll be your guide!
 */
@RunWith(JUnitQuickcheck.class)
public class TrollProp {

  /**
   * This is an invariance property: we check that something is always true, no matter the input.
   *
   * When migrating unit tests to property based tests, this is usually the easier type of property to get started. Look for any unit test you have where some values are "pretty much random" and replace them with invariance tests where these "random values" are actually injected by the library. The benefit is twofold: you make it explicit to the reader that some values are irrelevant, and the PBT library will actually check many values to ensure the value is irrelevant ;-)
   */
  @DisplayName("A troll hunting score can never be negative")
  @Property
  public void invariance(Troll troll) {
    assertThat(troll.score()).isGreaterThan(0);
  }

  /**
   * This is an inverse property: we check that doing something and then undoing it brings back to the original state
   *
   * This type of property is particularly useful when you have code that converts from a model to another (e.g. from your REST API to your domain model, or from your domain model to your DAO model) because you need to check that converting back and forth should bring back to the original value, i.e. your code does not "lose" information in the process!
   */
  @DisplayName("A troll killing an elf and then realizing the elf actually survived remains unchanged")
  @Property
  public void inverse(Troll troll, Elf elf) {
    Troll trollWithAdditionalElfKilled = troll.iGotOne(elf);
    Troll trollRealizingHeMissedTheElf = trollWithAdditionalElfKilled.oopsHeSurvived(elf);

    assertThat(trollRealizingHeMissedTheElf).isEqualTo(troll);
  }

  /**
   * This is an analogous property: we check that there are several ways from a starting value to reach and end value.
   *
   * This is useful for consistency between functions! E.g. `x + x` should always be equal to `x * 2`, so we can say that these 2 functions are analogous.
   *
   * Another use case for analogous properties are refactoring: instead of modifying an existing function `foo`, copy its implementation to `foo2`, then modify `foo2`, and eventually check that no matter the input `x`, `foo x == foo2 x`, which ensures we did not bring any regression during refactoring! Then you can safely remove `foo` and rename `foo2` to `foo`
   */
  @DisplayName("Killing N elves one by one is the same as killing N elves in a single strike")
  @Property
  public void analogy(Troll troll, Elf elf, @InRange(minInt = 0) int nbElves) {
    List<Elf> oneByOne = List.fill(nbElves, elf);
    Troll trollKillingOneByOne = oneByOne.foldLeft(troll, Troll::iGotOne);
    Troll trollKillingInSingleStrike = troll.iGot(nbElves, elf);

    assertThat(trollKillingOneByOne).isEqualTo(trollKillingInSingleStrike);
  }

  /**
   * This is an idempotence property: we check that applying a function once or many times to the successive results should lead to the same value; e.g. `f` is idempotent if `f x == f (f x) == f (f (f x)) == ...`
   *
   * This is particularly useful for functions that make the input converge to a stable output, e.g. functions that cleanup form inputs (date format, put name in upper case, etc.)
   */
  @DisplayName("Resurrected elves cannot be resurrected again")
  @Property
  public void idempotent(Troll troll, Elf elf) {
    Troll resurrectedOnce = troll.allElvesOfAKindResurrected(elf);
    Troll resurrectedTwice = resurrectedOnce.allElvesOfAKindResurrected(elf);

    assertThat(resurrectedOnce).isEqualTo(resurrectedTwice);
  }

  /**
   * This is a metamorphic property: we run a function over an input and a modified version of the same input, and we check some property holds on the modified result (metamorphed sounds much cooler!).
   *
   * This is pretty neat when you can tell something about your function result depending on how the input varies.
   */
  @DisplayName("When a troll kills an elf, his score should increase")
  @Property
  public void metamorphic(Troll troll, Elf elf) {
    int scoreBefore = troll.score();
    Troll trollAfterKill = troll.iGotOne(elf);
    int scoreAfter = trollAfterKill.score();

    assertThat(scoreAfter).isEqualTo(scoreBefore + elf.value());
  }

  /**
   * This is an injective property: we check that different inputs must yield different results.
   *
   * This is useful whenever you need to ensure an output can only be reached by a single input; e.g. a hash function, or a function that takes a person and returns its Social Security Number (imagine if 2 persons had the same SSN!)
   */
  @DisplayName("When killing different elves, a troll should get different scores")
  @Property
  public void injective(Troll troll, Elf elf1, Elf elf2) {
    assumeThat(elf1).isNotEqualTo(elf2);

    Troll trollKilledElf1 = troll.iGotOne(elf1);
    Troll trollKilledElf2 = troll.iGotOne(elf2);

    assertThat(trollKilledElf1.score()).isNotEqualTo(trollKilledElf2.score());
  }
}
