import com.example.Feline;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class FelineTest {

    @Test
    public void eatMeatShouldReturnListForFeline() throws Exception {
        Feline feline = new Feline();
        Assert.assertEquals(List.of("Животные", "Птицы", "Рыба"), feline.eatMeat());
    }
    @Test
    public void getFamilyShouldReturnValidFamily() {
        Feline feline = new Feline();
        Assert.assertEquals("Кошачьи", feline.getFamily());
    }
    @Test
    public void getKittensShouldReturnOne() {
        Feline feline = new Feline();
        Assert.assertEquals(1, feline.getKittens());
    }
    @Test
    public void getKittensWithParameterShouldReturnParameter() {
        Feline feline = new Feline();
        Assert.assertEquals(2, feline.getKittens(2));
    }

}