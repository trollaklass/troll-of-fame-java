import io.vavr.collection.Map;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@Wither
public class Troll {

  private String name;
  private Map<Elf, Integer> killList;

  //TODO
  public int score() {
    return 0;
  }

  //TODO
  public Troll iGotOne(Elf elf) {
    return null;
  }

  //TODO
  public Troll iGot(int nbElves, Elf elf) {
    return null;
  }

  //TODO
  public Troll oopsHeSurvived(Elf elf) {
    return null;
  }

  //TODO
  public Troll allElvesOfAKindResurrected(Elf elf) {
    return null;
  }

  //TODO
  private Troll modifyScore(Function<Integer, Integer> modifier, Elf elf) {
    return null;
  }
}
