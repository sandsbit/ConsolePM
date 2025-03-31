package me.nikitaserba.consolepm;


import me.nikitaserba.consolepm.utils.EncryptionException;
import me.nikitaserba.consolepm.utils.NoSuchUserException;

import java.io.IOException;

public class MainCLI {

    private static final UserManager userManager = UserManager.getInstance();

    public static void main(String[] args) throws Exception {
        printWelcome();
        PasswordManager pm = authenticate();
        accountListInterface(pm);
    }

    private static void printWelcome() {
        System.out.println("Welcome to ConsolePM!");
    }

    private static PasswordManager authenticate() throws IOException, EncryptionException, NoSuchUserException {
        if (yesNoQuestion("Do you want to login to an existing account?"))
            return login();
        else
            return register();
    }

    private static PasswordManager register() throws IOException, EncryptionException, NoSuchUserException {
        String username, password1, password2;
        while (true) {
            username = askForReply("Enter your new username: ");
            password1 = askForReplySecure("Enter your new password: ");
            password2 = askForReplySecure("Enter your new password once again: ");
            if (password1.equals(password2))
                break;
            else
                System.out.println("Passwords do not match! Try again.");
        }

        userManager.newUser(username, password1);
        return PasswordManager.authenticate(username, password1);
    }

    private static PasswordManager login() {

    }

    private static void accountListInterface(PasswordManager passwordManager) {

    }

    private static int choose(String messageAtTheEnd, String... options) {

    }

    private static boolean yesNoQuestion(String message) {

    }

    private static String askForReply(String message) {

    }

    private static String askForReplySecure(String message) {

    }
}