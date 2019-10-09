import static elf.Race.DARK;
import static elf.Race.HIGH;
import static elf.Role.ARCHER;
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
  void oopsHeSurvivedDarkWarlock() {
    Elf darkWarlock = new Elf(DARK, WARLOCK);
    Troll aklassBefore = new Troll("Aklass", Map(darkWarlock, 3));

    Troll aklassAfter = aklassBefore.oopsHeSurvived(darkWarlock);

    assertThat(aklassAfter).isEqualTo(new Troll("Aklass", Map(darkWarlock, 2)));
  }

  @Test
  void allElvesOfAKindResurrected() {
    Elf darkSwordsman = new Elf(DARK, SWORDSMAN);
    Elf highArcher = new Elf(HIGH, ARCHER);
    Map<Elf, Integer> killList = HashMap.ofEntries(
        Tuple(darkSwordsman, 3),
        Tuple(highArcher, 42));
    Troll aklassBefore = new Troll("Aklass", killList);

    Troll aklassAfter = aklassBefore.allElvesOfAKindResurrected(darkSwordsman);

    assertThat(aklassAfter.score()).isEqualTo(168);
  }
}
