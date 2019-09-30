import com.google.auto.service.AutoService;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import elf.Race;
import elf.Role;
import java.math.BigDecimal;
import java.util.UUID;

@AutoService(Generator.class)
public class ElfGen extends Generator<Elf> {

  public ElfGen() {
    super(Elf.class);
  }

  @Override
  public Elf generate(SourceOfRandomness random, GenerationStatus status) {
    return new Elf(
        gen().type(Race.class).generate(random, status),
        gen().type(Role.class).generate(random, status)
    );
  }
}
