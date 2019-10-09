import static org.assertj.core.api.Assertions.assertThat;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class TrollProp {

  @Property
  public void invariance(Troll troll) {
    assertThat(troll.score()).isGreaterThanOrEqualTo(0);
  }
}
