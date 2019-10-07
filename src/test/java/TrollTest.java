import static elf.Race.DARK;
import static elf.Race.HIGH;
import static elf.Role.ARCHER;
import static elf.Role.PRIEST;
import static elf.Role.SWORDSMAN;
import static elf.Role.WARLOCK;
import static io.vavr.API.Map;
import static io.vavr.API.Tuple;
import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

class TrollTest {

  @Test
  void iGotOneHighArcher() {
    Troll aklassBefore = new Troll("Aklass", Map());
    Elf highArcher = new Elf(HIGH, ARCHER);

    Troll aklassAfter = aklassBefore.iGotOne(highArcher);

    assertThat(aklassAfter.score()).isEqualTo(4);
  }

  @Test
  void iGotOneHighPriest() {
    Troll aklassBefore = new Troll("Aklass", Map());
    Elf highPriest = new Elf(HIGH, PRIEST);

    Troll aklassAfter = aklassBefore.iGotOne(highPriest);

    assertThat(aklassAfter.score()).isEqualTo(10);
  }

  @Test
  void iGotOneDarkSwordsman() {
    Troll aklassBefore = new Troll("Aklass", Map());
    Elf darkSwordsman = new Elf(DARK, SWORDSMAN);

    Troll aklassAfter = aklassBefore.iGotOne(darkSwordsman);

    assertThat(aklassAfter.score()).isEqualTo(1);
  }

  @Test
  void iGotOneDarkWarlock() {
    Troll aklassBefore = new Troll("Aklass", Map());
    Elf darkWarlock = new Elf(DARK, WARLOCK);

    Troll aklassAfter = aklassBefore.iGotOne(darkWarlock);

    assertThat(aklassAfter.score()).isEqualTo(4);
  }

  @Test
  void oopsHeSurvivedDarkWarlock() {
    Elf darkWarlock = new Elf(DARK, WARLOCK);
    Troll aklassBefore = new Troll("Aklass", Map(darkWarlock, 3));

    Troll aklassAfter = aklassBefore.oopsHeSurvived(darkWarlock);

    assertThat(aklassAfter).isEqualTo(new Troll("Aklass", Map(darkWarlock, 2)));
  }

  @Test
  void allElvesOfAKindResurrected() {
    Elf darkSwordsman = new Elf(DARK, SWORDSMAN);
    Elf highPriest = new Elf(HIGH, PRIEST);
    Map<Elf, Integer> killList = HashMap.ofEntries(
        Tuple(darkSwordsman, 3),
        Tuple(highPriest, 42));
    Troll aklassBefore = new Troll("Aklass", killList);

    Troll aklassAfter = aklassBefore.allElvesOfAKindResurrected(darkSwordsman);

    assertThat(aklassAfter.score()).isEqualTo(420);
  }
}
