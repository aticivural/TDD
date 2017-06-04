import com.vural.Connect4TDD;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


/**
 * Created by vural on 01-Jun-17.
 */
public class Connect4TDDSpec {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Connect4TDD tested;
    private OutputStream output;

    @Before
    public void beforeEachTest() {
        output = new ByteArrayOutputStream();
        tested = new Connect4TDD(new PrintStream(output));
    }

    @Test
    public void whenTheGameIsStartedTheBoardIsEmpty() {
        assertThat(tested.getNumberOfDiscs(), is(0));
    }

    @Test
    public void whenDiscOutsideBoardThenRuntimeException() {
        int column = -1;
        exception.expect(RuntimeException.class);
        exception.expectMessage("Invalid column " + column);
        tested.putDiscInColumn(column);
    }

    @Test
    public void whenFirstPlayerPlaysThenDisColorIsRed() {
        assertThat(tested.getCurrentPlayer(), is("R"));
    }

    @Test
    public void whenSecondPlayerPlaysThenDiscColorIsRed() {
        int column = 1;
        tested.putDiscInColumn(column);
        assertThat(tested.getCurrentPlayer(), is("G"));
    }

    @Test
    public void whenAskedForCurrentPlayerTheOutputNotice() {
        tested.getCurrentPlayer();
        assertThat(output.toString(), containsString("Player R turn"));
    }

    @Test
    public void whenADiscIntroducedTheBoardIsPrinted() {
        int column = 1;
        tested.putDiscInColumn(column);
        assertThat(output.toString(), containsString("| |R| | | | | |"));
    }

    @Test
    public void whenTheGameStartsItIsNotFinished() {
        assertFalse("The game must not be finished",
                tested.isFinished());
    }

    @Test
    public void whenNoDiscCanBeIntroducedTheGamesIsFinished() {
        for (int row = 0; row < 6; row++)
            for (int column = 0; column < 7; column++) {
                tested.putDiscInColumn(column);
            }
        assertTrue("The game must be finished", tested.isFinished());
    }

    @Test
    public void when4VerticalDiscsAreConnectedThenPlayerWins() {
        for (int row = 0; row < 3; row++) {
            tested.putDiscInColumn(1);//R
            tested.putDiscInColumn(2);//G
        }

        assertThat(tested.getWinner(), isEmptyString());
        tested.putDiscInColumn(1);//R
        assertThat(tested.getWinner(), is("R"));
    }

    @Test
    public void when4HorizontalDiscsAreConnectedThenPlayerWins() {
        int column;
        for (column = 0; column < 3; column++) {
            tested.putDiscInColumn(column); // R
            tested.putDiscInColumn(column); // G
        }
        assertThat(tested.getWinner(), isEmptyString());
        tested.putDiscInColumn(column); // R
        assertThat(tested.getWinner(), is("R"));
    }

    @Test
    public void when4DiagonalDiscsAreConnectedThenThatPlayerWins() {
        int[] gamePlay = new int[]{1, 2, 2, 3, 4, 3, 3, 4, 4, 5, 4};
        for (int column : gamePlay) {
            tested.putDiscInColumn(column);
        }
        assertThat(tested.getWinner(), is("R"));
    }

    @Test
    public void when4Diagonal2DiscsAreConnectedThenThatPlayerWins() {
        int[] gameplay = new int[]{3, 4, 2, 3, 2, 2, 1, 1, 1, 1};
        for (int column : gameplay) {
            tested.putDiscInColumn(column);
        }
        assertThat(tested.getWinner(), is("G"));
    }
}

























