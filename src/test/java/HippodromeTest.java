import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    public void nullHorses_ThrowIllegalArgException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
    }

    @Test
    public void nullHorses_ExceptionContainsMessage() {
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertTrue(illegalArgumentException.getMessage().contains("Horses cannot be null."));
    }

    @Test
    public void emptyHorses_ThrowIllegalArgException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        });
    }

    @Test
    public void emptyHorses_ExceptionContainsMessage() {
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        });
        assertTrue(illegalArgumentException.getMessage().contains("Horses cannot be empty."));
    }

    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        IntStream.rangeClosed(1, 30)
                .forEach((num) -> horses.add(new Horse("horseName" + num, num)));

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<>();
        IntStream.range(0, 50)
                .forEach((num) -> horses.add(Mockito.mock(Horse.class)));

        new Hippodrome(horses).move();

        horses.forEach(horse -> Mockito.verify(horse).move());

    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>();
        IntStream.rangeClosed(1, 30)
                .forEach((num) -> horses.add(new Horse("horseName" + num, num, num)));

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.getHorses().forEach(Horse::move);

        Horse actualWinner = hippodrome.getWinner();
        Horse expectedWinner = horses.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .get();

        assertSame(expectedWinner, actualWinner);
    }
}