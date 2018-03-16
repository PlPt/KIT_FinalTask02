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
 * ResetTest
 *
 * @author Valentin Wagner
 *         01.03.18
 * @author Lucas Alber
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Terminal.class)
public class ResetTest {

    private TerminalMock terminalMock;

    public ResetTest() {
    }

    @Before
    public void init() {
        this.terminalMock = new TerminalMock();
    }


    @Test
    public void testItWorks() throws Exception {
        terminalMock.mock(true)
                .willReturn("reset")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();
        assertThat(terminalMock.getResult().getLast()).isEqualTo("OK");
    }

    @Test
    public void testOnlyWorksWhenLoggedIn() throws Exception {
        terminalMock.mock()
                .willReturn("reset")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isTrue();
    }

    @Test
    public void testErrorWithExtraSpace() throws Exception {
        terminalMock.mock(true)
                .willReturn("reset ")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isTrue();
    }

    @Test
    public void testAdminAccountStillExists() throws Exception {
        terminalMock.mock(true)
                .willReturn("reset")
                .willReturn("logout-admin")
                .willReturn("login-admin hans;password123")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();
        assertThat(terminalMock.getResult().getResults())
                .containsExactlyElementsIn(new String[]{
                    "OK", "OK", "OK"
                });
    }

    @Test
    public void testNoCountriesExistAnymore() throws Exception {
        terminalMock.mock(true)
                .willReturn("reset")
                .willReturn("list-ioc-codes")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();
        assertThat(terminalMock.getResult().getResults())
                .containsExactlyElementsIn(new String[]{
                        "OK"
                });
    }

    @Test
    public void testNoSportsExistAnymore() throws Exception {
        terminalMock.mock(true)
                .willReturn("reset")
                .willReturn("list-olympic-sports")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();
        assertThat(terminalMock.getResult().getResults())
                .containsExactlyElementsIn(new String[]{
                        "OK"
                });
    }

    @Test
    public void testOlympicMedalTableEmpty() throws Exception {
        terminalMock.mock(true)
                .willReturn("reset")
                .willReturn("olympic-medal-table")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();
        assertThat(terminalMock.getResult().getResults())
                .containsExactlyElementsIn(new String[]{
                        "OK"
                });
    }

}