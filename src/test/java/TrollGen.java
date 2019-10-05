import static io.vavr.API.Tuple;

import com.google.auto.service.AutoService;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import io.vavr.collection.HashMap;

@AutoService(Generator.class)
public class TrollGen extends Generator<Troll> {

  public TrollGen() {
    super(Troll.class);
  }

  @Override
  public Troll generate(SourceOfRandomness random, GenerationStatus status) {
    return new Troll(
        gen().type(String.class).generate(random, status),
        HashMap.fill(random.nextInt(10), () -> Tuple(
            gen().type(Elf.class).generate(random, status),
            random.nextInt(1, 100)
        ))
    );
  }
}
