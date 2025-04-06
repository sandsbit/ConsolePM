package me.nikitaserba.consolepm;


import me.nikitaserba.consolepm.utils.Account;
import me.nikitaserba.consolepm.utils.DataManager;
import me.nikitaserba.consolepm.utils.FileDataManager;
import me.nikitaserba.consolepm.utils.exceptions.EncryptionException;
import me.nikitaserba.consolepm.utils.exceptions.InvalidPasswordException;
import me.nikitaserba.consolepm.utils.exceptions.NoSuchUserException;
import me.nikitaserba.consolepm.utils.exceptions.UserAlreadyExistsException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class MainCLI {

    protected static InputStream in = System.in;
    protected static PrintStream out = System.out;

    private static final DataManager dataManager = new FileDataManager();
    private static final UserManager userManager;

    static {
        try {
            userManager = UserManager.getInstance(dataManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        printWelcome();
        PasswordManager pm = authenticate();
        accountListInterface(pm);
    }

    private static void printWelcome() {
        out.println("Welcome to ConsolePM!");
    }

    private static PasswordManager authenticate() throws IOException, EncryptionException, NoSuchUserException,
            InvalidPasswordException {
        if (yesNoQuestion("Do you want to login to an existing account?"))
            return login();
        else
            return register();
    }

    private static PasswordManager register() throws IOException, EncryptionException,
            NoSuchUserException, InvalidPasswordException {
        String username, password1, password2;
        while (true) {
            username = askForReply("Enter your new username: ");
            password1 = askForReplySecure("Enter your new password: ");
            password2 = askForReplySecure("Enter your new password once again: ");
            if (password1.equals(password2))
                break;
            else
                out.println("Passwords do not match! Try again.");

            try {
                userManager.newUser(username, password1);
            } catch (UserAlreadyExistsException e) {
                out.println("User already exists! Try again.");
            }
        }

        return PasswordManager.authenticate(new FileDataManager(), username, password1);
    }

    private static PasswordManager login() throws EncryptionException, IOException {
        while (true) {
            String username = askForReply("Enter your username: ");
            String password = askForReplySecure("Enter your password: ");
            try {
                return PasswordManager.authenticate(dataManager, username, password);
            } catch (NoSuchUserException | InvalidPasswordException _) {
                out.println("Invalid username or password!");
            }
        }
    }

    private static void accountListInterface(PasswordManager passwordManager) throws EncryptionException, IOException {
        while (true) {
            List<String> accounts = passwordManager.getAccountsNames();
            var options = new ArrayList<>(accounts);
            options.add("Add new");
            options.add("Quit");
            int answer = choose("Choose account data to view: ", options.toArray(new String[0]));

            if (answer == options.size()-1) {
                addNewAccount(passwordManager);
                continue;
            }

            if (answer == options.size())
                System.exit(0);

            printAccount(passwordManager.getAccountData(accounts.get(answer-1)), passwordManager);
        }
    }

    private static void addNewAccount(PasswordManager passwordManager) throws EncryptionException, IOException {
        String name = askForReply("Your new account name: ");
        String username = askForReply("Your new account username: ");
        String email = askForReply("Your new account email: ");
        String password = askForReplySecure("Your new account password: ");
        Date created = new Date();

        passwordManager.addAccount(new Account(name, username, email, null, created), password);
    }

    private static void printAccount(Account account, PasswordManager passwordManager) throws EncryptionException {
        out.println("Name: " + account.getName());
        out.println("Username: " + account.getUsername());
        out.println("Email: " + account.getEmail());
        out.println("Date created: " + account.getCreated().toString());
        out.println("Password: " + passwordManager.decryptPassword(account.getPasswordEncrypted()));
    }

    /**
     * Prints list and asks user to choose one option.
     */
    protected static int choose(String messageAtTheEnd, String... options) {
        for (int i = 0; i < options.length; i++) {
            out.println((i + 1) + " - " + options[i]);
        }

        Scanner scanner = new Scanner(in);
        int answer;
        do {
            out.print(messageAtTheEnd);
            answer = scanner.nextInt();
        } while (answer <= 0 || answer > options.length);
        return answer;
    }

    /**
     * Asks a yes/no question with message, adding "(y/n)" to it.
     * @return true if answer is y or Y
     */
    protected static boolean yesNoQuestion(String message) {
        out.print(message + " (y/n)");
        Scanner scanner = new Scanner(in);
        return scanner.next().equalsIgnoreCase("y");
    }

    /**
     * Just print message and ask for input.
     */
    protected static String askForReply(String message) {
        out.print(message);
        Scanner scanner = new Scanner(in);
        return scanner.next();
    }

    /**
     * Print message and ask for input that will be hidden.
     *
     * Doesn't work in Jetbrains' console.
     */
    protected static String askForReplySecure(String message) {
        out.print(message);
        return new String(System.console().readPassword());
    }
}