package me.nikitaserba.consolepm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainCLITest {

    @Test
    void choose() {
        InputStream in = new ByteArrayInputStream("1".getBytes(StandardCharsets.UTF_8));
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        MainCLI.in = in;
        MainCLI.out = out;

        var options = new String[] {"var1", "var2"};
        int choosen = MainCLI.choose("MSG: ", options);

        assertEquals(1, choosen);
        assertEquals("1 - var1\n2 - var2\nMSG: ", outputStream.toString());
    }

    @Test
    void yesNoQuestionTrue() {
        InputStream in = new ByteArrayInputStream("y".getBytes(StandardCharsets.UTF_8));
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        MainCLI.in = in;
        MainCLI.out = out;

        boolean choosen = MainCLI.yesNoQuestion("MSG");

        assertTrue(choosen);
        assertEquals("MSG (y/n)", outputStream.toString());
    }

    @Test
    void yesNoQuestionFalse() {
        InputStream in = new ByteArrayInputStream("s".getBytes(StandardCharsets.UTF_8));
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        MainCLI.in = in;
        MainCLI.out = out;

        boolean choosen = MainCLI.yesNoQuestion("MSG");

        assertFalse(choosen);
        assertEquals("MSG (y/n)", outputStream.toString());
    }

    @Test
    void askForReply() {
        InputStream in = new ByteArrayInputStream("TEST".getBytes(StandardCharsets.UTF_8));
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        MainCLI.in = in;
        MainCLI.out = out;

        String reply = MainCLI.askForReply("MSG: ");

        assertEquals("TEST", reply);
        assertEquals("MSG: ", outputStream.toString());
    }
}