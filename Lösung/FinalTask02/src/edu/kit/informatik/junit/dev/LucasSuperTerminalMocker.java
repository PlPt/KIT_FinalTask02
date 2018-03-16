package edu.kit.informatik.junit.dev;

import edu.kit.informatik.Terminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

/**
 * @author Lucas Alber
 */
public class LucasSuperTerminalMocker {

    public static final LinkedList<String> OUT = new LinkedList<>();

    public LucasSuperTerminalMocker(){
    }

    public BDDMockito.BDDMyOngoingStubbing<String> mock() throws Exception{
        PowerMockito.mockStatic(Terminal.class);

        //PowerMockito.doNothing().when(Terminal.class, "printLine", captor.capture());
        PowerMockito.when(Terminal.class, "printLine", Mockito.any())
                .then(invocation -> {
                    String message = invocation.getArgument(0).toString();
                    if (message.startsWith("Error, ")) {
                        OUT.add("Error");
                    } else {
                        String[] lines = message.split("\n");
                        for (int i = 0; i < lines.length; i++) {
                            if (!lines[i].isEmpty()) {
                                OUT.add(lines[i]);
                            }
                        }
                    }
                    return null;
                });
        PowerMockito.when(Terminal.class, "printError", Mockito.anyString())
                .then((invocation -> {
                    OUT.add("Error");
                    return null;
                }));

        BDDMockito.BDDMyOngoingStubbing<String> ongoingStubbing = BDDMockito.given(Terminal.readLine());
        return ongoingStubbing;
    }
}
