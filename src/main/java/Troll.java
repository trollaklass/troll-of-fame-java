import io.vavr.collection.Map;
import io.vavr.control.Option;
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

  public int score() {
    int start = 0;
    return killList.foldLeft(start,
        (previousScore, elfAndNbKilled) -> (elfAndNbKilled._1.value() * elfAndNbKilled._2));
  }

  public Troll iGotOne(Elf elf) {
    return modifyScore(nbKilled -> nbKilled + 1, elf);
  }

  public Troll iGot(int nbElves, Elf elf) {
    return modifyScore(nbKilled -> nbKilled + nbElves, elf);
  }

  public Troll oopsHeSurvived(Elf elf) {
    return modifyScore(nbKilled -> nbKilled - 1, elf);
  }

  public Troll allElvesOfAKindResurrected(Elf elf) {
    Map<Elf, Integer> killListWithoutResurrectedElves = killList.remove(elf);
    return withKillList(killListWithoutResurrectedElves);
  }

  private Troll modifyScore(Function<Integer, Integer> modifier, Elf elf) {
    Option<Integer> currentNbKilled = killList.get(elf);
    int nextNbKilled = modifier.apply(currentNbKilled.getOrElse(0));
    Map<Elf, Integer> newKillList = killList.put(elf, nextNbKilled);
    return withKillList(newKillList);
  }
}
