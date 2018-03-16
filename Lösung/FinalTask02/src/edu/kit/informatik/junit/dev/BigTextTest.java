package edu.kit.informatik.junit.dev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.junit.Wrapper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Terminal.class)
public class BigTextTest {
    private LucasSuperTerminalMocker terminalMock;
    
    private String path = "src/test/java/edu/kit/informatik/junit/dev/tests";
   
   /**
    * Executes before ALL tests
    */
   @Before
   public void init() {
       this.terminalMock = new LucasSuperTerminalMocker();
   }
   
   /**
    * This is the actual test. It is possible to add more test methods.
    */
   @Test
   public void textTest() {
        File dir = new File(path);
        if (dir.isDirectory()) {
            for (File in : dir.listFiles()) {
                if (in.isFile() && !in.getName().contains("out")) {
                    System.out.println("Testing: " + in.getPath());
                    File out = new File(in.getPath().substring(0, in.getPath().indexOf(".")) + "_out.txt");
                    if (out.isFile()) {
                        System.out.println("Found proper: " + out.getName());
                        try {
                            String[] inlines = readFile(in.getPath());
                            //System.out.println(inlines.length);
                            // Make sure the application terminates
                            if (!inlines[inlines.length - 1].equals("quit")) {
                                PrintWriter outWriter = new PrintWriter(new FileWriter(in.getPath(), true), true);
                                outWriter.println("quit");
                                outWriter.close();
                            }
                            // Filling Terminal
                            System.out.println("Set up Terminal...");
                            String[] commands = readFile(in.getPath());
                            //for (String s : readFile(in.getPath())) {
                                terminalMock.mock().willAnswer(new Answer() {
                                    int i = 0;
                                    @Override
                                    public Object answer(InvocationOnMock invocation) throws Throwable {
                                        return commands[i++];
                                    }
                                });
                            //}
                            
                            System.out.println("...finished!\nTesting...");
                            Wrapper.main();
                            System.out.println("OK!\nChecking what we got...");
                            
                            String[] got = LucasSuperTerminalMocker.OUT.toArray(new String[LucasSuperTerminalMocker.OUT.size()]);
                            String[] expected = readFile(out.getPath());
                            
                            //Comparing
                            System.out.println("Got " + got.length + " lines. Comparing...");
                            int min = Math.min(got.length, expected.length);
                            for (int i = 0; i < min; i++) {
                                //System.out.println("\"" + got[i] + "\"" + " got and expected: " + "\"" + expected[i] + "\"");
                                if (!got[i].equals(expected[i])) {
                                    System.err.println("We got wrong output!");
                                    System.err.println("We expected something that matches this:");
                                    System.err.println(expected[i] + " (In line " + i + ")");
                                    System.err.println("Instead we got this:");
                                    System.err.println(got[i]);
                                    Assert.fail();
                                }
                            }
                            if (got.length < expected.length) {
                                System.err.println("There is something missing: ");
                                for (int i = got.length; i < expected.length; i++) System.err.println(expected[i]);
                            }
                            if (got.length > expected.length) {
                                System.err.println("We got to much: ");
                                for (int i = expected.length; i < got.length ; i++) System.err.println(expected[i]);
                            }
                           
                            
                            //Assert.assertArrayEquals(expected, got);
                            System.out.println("OK!!\nCleaning up...");
                            //Clean Up
                            LucasSuperTerminalMocker.OUT.clear();
                            //Thread.sleep(1000);
                            
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Assert.fail();
                        }
                        
                        
                        
                    } else System.out.println(out.getPath() + " not found!");
                }
            }
        } else {
            System.err.println("This is not a Directory! Please set the proper Dir in the "
                    + "BigTextTest");
        }
    }
   
   public static String[] readFile(final String path) {
       try (final BufferedReader reader = new BufferedReader(new FileReader(path))) {
           return reader.lines().toArray(String[]::new);
       } catch (final IOException e) {
           throw new RuntimeException(e);
       }
   }
}

