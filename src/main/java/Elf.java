import static elf.Race.DARK;
import static elf.Race.HIGH;
import static elf.Role.ARCHER;
import static elf.Role.PRIEST;
import static elf.Role.SWORDSMAN;
import static elf.Role.WARLOCK;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import elf.Race;
import elf.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@Wither
public class Elf {

  private Race race;
  private Role role;

  public int value() {
    return multiplierPerRace() * valuePerRole();
  }

  /**
   * Because everybody hates those smug High Elves
   */
  private int multiplierPerRace() {
    return Match(race).of(
        Case($(HIGH), 2),
        Case($(DARK), 1)
    );
  }

  private int valuePerRole() {
    return Match(role).of(
        Case($(ARCHER), 2),
        Case($(SWORDSMAN), 1),
        Case($(PRIEST), 5),
        Case($(WARLOCK), 4)
    );
  }

}
