package de.plpt;

import de.plpt.CommandProcessor.CommandProcessor;
import edu.kit.informatik.Terminal;

public class Main {

    /**
     * Main etry point of application
     * @param args cli parameters
     */
    public static void main(String[] args) {
        try {
            CommandProcessor processor = new CommandProcessor();
            processor.processCommands();
        } catch (IllegalArgumentException x) {
            Terminal.printError(String.format("[%s] : %s", x.getClass().getName(), x.getMessage()));
        }
    }
}
