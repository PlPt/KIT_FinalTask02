package edu.kit.informatik.junit.mock;

import static org.junit.Assert.*;
import static com.google.common.truth.Truth.assertThat;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.junit.TerminalMock;
import edu.kit.informatik.junit.Wrapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * AddOlympicSportTest
 *
 * @author Valentin Wagner
 *         24.02.18
 * @author Lucas Alber
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Terminal.class)
public class AddOlympicSportTest {

    private TerminalMock terminalMock;

    public AddOlympicSportTest() {
    }

    @Before
    public void init() {
        this.terminalMock = new TerminalMock();
    }


    @Test
    public void testItWorks() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-olympic-sport Eis;hockey")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();
        assertThat(terminalMock.getResult().getLast()).isEqualTo("OK");
    }

    @Test
    public void testSpecialCharsAndNumbersAllowed() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-olympic-sport Eis56%&$;hockey7%9$")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();
        assertThat(terminalMock.getResult().getLast()).isEqualTo("OK");
    }

    @Test
    public void testMultipleDisciplinesForSameSport() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-olympic-sport Eis;hockey")
                .willReturn("add-olympic-sport Eis;irwas")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();
        assertThat(terminalMock.getResult().getLast()).isEqualTo("OK");
    }

    @Test
    public void testSameDisciplineForDifferentSports() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-olympic-sport Eis;hockey")
                .willReturn("add-olympic-sport Irwas;hockey")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();
        assertThat(terminalMock.getResult().getLast()).isEqualTo("OK");
    }

    @Test
    public void testOnlyWorksWhenLoggedIn() throws Exception {
        terminalMock.mock()
                .willReturn("add-olympic-sport Eis;hockey")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isTrue();
    }

    @Test
    public void testCannotAddSameDisciplineTwice() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-olympic-sport Eis;hockey")
                .willReturn("add-olympic-sport Eis;hockey")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isTrue();
    }

}