import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    public void nullName_ThrowIllegalArgException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, .0);
        });
    }

    @Test
    public void nullName_ExceptionContainsMessage() {
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, .0);
        });
        assertTrue(illegalArgumentException.getMessage().contains("Name cannot be null."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\s", "\t", "\n"})
    public void blankName_ThrowIllegalArgException(String name) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, .0);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\s", "\t", "\n"})
    public void blankName_ExceptionContainsMessage(String name) {
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, .0);
        });
        assertTrue(illegalArgumentException.getMessage().contains("Name cannot be blank."));
    }

    @Test
    public void speedNegative_ThrowIllegalArgException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("test", -1.0);
        });
    }

    @Test
    public void speedNegative_ExceptionContainsMessage() {
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("test", -1.0);
        });
        assertTrue(illegalArgumentException.getMessage().contains("Speed cannot be negative."));
    }

    @Test
    public void distanceNegative_ThrowIllegalArgException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("test", .0, -1.0);
        });
    }

    @Test
    public void distanceNegative_ExceptionContainsMessage() {
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("test", .0, -1.0);
        });
        assertTrue(illegalArgumentException.getMessage().contains("Distance cannot be negative."));
    }


    @Test
    void getName() {
        String expectedName = "test";
        Horse test = new Horse(expectedName, .0);
        String actualName = test.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    void getSpeed() {
        double expectedSpeed = 5.0;
        Horse test = new Horse("test", expectedSpeed);
        double actualSpeed = test.getSpeed();
        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    void getDistance() {
        double expectedDistance = 20.0;
        Horse test = new Horse("test", 1.0, expectedDistance);
        double actualDistance = test.getDistance();
        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    void zeroDistanceByDefault() {
        Horse test = new Horse("test", 1.0);
        double distance = test.getDistance();
        assertEquals(0, distance);
    }

    @Test
    void moveUsesRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("test", 1.0).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 20.0, 30.0})
    void moveAssignsByFormula(double random) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("test", 31, 283);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            double expectedDistance = 283 + 31 * random;

            horse.move();

            double actualDistance = horse.getDistance();
            assertEquals(expectedDistance, actualDistance);
        }
    }
}