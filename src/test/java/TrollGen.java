import static io.vavr.API.Tuple;
import static java.lang.Math.abs;
import static java.lang.Math.floor;

import com.google.auto.service.AutoService;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import java.util.UUID;

@AutoService(Generator.class)
public class TrollGen extends Generator<Troll> {

  public TrollGen() {
    super(Troll.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Troll generate(SourceOfRandomness random, GenerationStatus status) {
    return new Troll(
        gen().type(String.class).generate(random, status),
        gen().type(Map.class, Elf.class, Integer.class).generate(random, status)
    );
  }
}
