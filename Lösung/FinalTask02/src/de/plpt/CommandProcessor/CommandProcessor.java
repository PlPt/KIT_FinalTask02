package de.plpt.CommandProcessor;

import de.plpt.ArgumentParser.ArgumentParser;
import de.plpt.ArgumentParser.ArgumentParserException;
import de.plpt.ArgumentParser.ArgumentParserExecutionException;
import de.plpt.ArgumentParser.CommandInfo;
import edu.kit.informatik.Terminal;

public class CommandProcessor {

    //region varDef
    private boolean quit = false;
  
    //endregion

    //region constructor
    public CommandProcessor(String[] cliArgs) {
        this.parser = new ArgumentParser(this);

      
    }
    //endregion

    //region processCommands
    public void processCommands() {

        while (!quit) {
            String line = Terminal.readLine();
            try {
                String result = parser.parse(line);
                if (result != null) Terminal.printLine(result);
            } catch (ArgumentParserExecutionException apeex) {
                Exception clientException = apeex.getTypedCause();
                Terminal.printError(String.format("[ArgumentParserExecutionException][%s] :: %s", clientException.getClass().getName(), clientException.getMessage()));
            } catch (ArgumentParserException apex) {

                Terminal.printError(String.format("[ArgumentParserException] :: %s", apex.getMessage()));
            } catch (Exception x) {
                Terminal.printError(String.format("[EX][%s] :: %s", x.getClass().getName(), x.getMessage()));
            }
        }
    }
    //endregion

    //region Methods
  

    //region quit
    @CommandInfo(command = "quit")
    public void quit() {
        this.quit = true;
    }
    //endregion


    //endregion
}
