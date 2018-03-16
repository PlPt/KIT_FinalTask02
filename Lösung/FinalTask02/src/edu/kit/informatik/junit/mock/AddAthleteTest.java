package edu.kit.informatik.junit.mock;

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
 * AddAthleteTest
 *
 * @author Valentin Wagner
 *         25.02.18
 * @author Lucas Alber
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Terminal.class)
public class AddAthleteTest {

    private TerminalMock terminalMock;

    public AddAthleteTest() {
    }

    @Before
    public void init() {
        this.terminalMock = new TerminalMock();
    }


    @Test
    public void testItWorks() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-ioc-code 118;ger;Deutschland;1992")
                .willReturn("add-olympic-sport eishockey;eishockey")
                .willReturn("add-athlete 0001;Max;Mustermann;Deutschland;eishockey;eishockey")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();

        for(int i = 0; i < terminalMock.getResult().getResults().size(); i++){
            assertThat(terminalMock.getResult().getResults().get(i)).isEqualTo("OK");
        }

    }

    @Test
    public void testSpecialCharsAndNumbersAllowed() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-ioc-code 118;ger;Deutschland;1992")
                .willReturn("add-olympic-sport eishockey;eishockey")
                .willReturn("add-athlete 0001;Max58%&$#;Mustermann58%&$#;Deutschland;eishockey;eishockey")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();

        for(int i = 0; i < terminalMock.getResult().getResults().size(); i++){
            assertThat(terminalMock.getResult().getResults().get(i)).isEqualTo("OK");
        }

    }

    @Test
    public void testTwoDisciplinesWork() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-ioc-code 118;ger;Deutschland;1992")
                .willReturn("add-olympic-sport eishockey;eishockey")
                .willReturn("add-olympic-sport eishockey;eishockey2")
                .willReturn("add-athlete 0001;Max;Mustermann;Deutschland;eishockey;eishockey")
                .willReturn("add-athlete 0001;Max;Mustermann;Deutschland;eishockey;eishockey2")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isFalse();

        for(int i = 0; i < terminalMock.getResult().getResults().size(); i++){
            assertThat(terminalMock.getResult().getResults().get(i)).isEqualTo("OK");
        }

    }

    @Test
    public void testOnlyWorksWhenLoggedIn() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-ioc-code 118;ger;Deutschland;1992")
                .willReturn("add-olympic-sport eishockey;eishockey")
                .willReturn("logout-admin")
                .willReturn("add-athlete 0001;Max;Mustermann;Deutschland;eishockey;eishockey")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isTrue();

        for(int i = 0; i < 3; i++){
            assertThat(terminalMock.getResult().getResults().get(i)).isEqualTo("OK");
        }

    }

    @Test
    public void test0000IDDoesntWork() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-ioc-code 118;ger;Deutschland;1992")
                .willReturn("add-olympic-sport eishockey;eishockey")
                .willReturn("add-athlete 0000;Max;Mustermann;Deutschland;eishockey;eishockey")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isTrue();

        for(int i = 0; i < 2; i++){
            assertThat(terminalMock.getResult().getResults().get(i)).isEqualTo("OK");
        }
    }

    @Test
    public void testCountryDoesntExist() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-ioc-code 118;ger;Deutschland;1992")
                .willReturn("add-olympic-sport eishockey;eishockey")
                .willReturn("add-athlete 0001;Max;Mustermann;USA;eishockey;eishockey")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isTrue();

        for(int i = 0; i < 2; i++){
            assertThat(terminalMock.getResult().getResults().get(i)).isEqualTo("OK");
        }
    }


    @Test
    public void testSportDoesntExist() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-ioc-code 118;ger;Deutschland;1992")
                .willReturn("add-olympic-sport eishockey;eishockey")
                .willReturn("add-athlete 0001;Max;Mustermann;Deutschland;Curling;eishockey")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isTrue();

        for(int i = 0; i < 2; i++){
            assertThat(terminalMock.getResult().getResults().get(i)).isEqualTo("OK");
        }
    }

    @Test
    public void testSportDisciplineDoesntExist() throws Exception {
        terminalMock.mock(true)
                .willReturn("add-ioc-code 118;ger;Deutschland;1992")
                .willReturn("add-olympic-sport eishockey;eishockey")
                .willReturn("add-athlete 0001;Max;Mustermann;Deutschland;eishockey;disziplin")
                .willReturn("quit");

        Wrapper.main();

        assertThat(terminalMock.isError()).isTrue();

        for(int i = 0; i < 2; i++){
            assertThat(terminalMock.getResult().getResults().get(i)).isEqualTo("OK");
        }
    }
}