package me.nikitaserba.consolepm;


import me.nikitaserba.consolepm.utils.Account;
import me.nikitaserba.consolepm.utils.EncryptionException;
import me.nikitaserba.consolepm.utils.NoSuchUserException;

import java.io.IOException;
import java.util.*;

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

    private static PasswordManager login() throws EncryptionException, IOException {
        while (true) {
            String username = askForReply("Enter your username: ");
            String password = askForReplySecure("Enter your password: ");
            try {
                var pm = PasswordManager.authenticate(username, password);
                if (pm == null)
                    System.out.println("Invalid password!");
                else
                    return pm;
            } catch (NoSuchUserException _) {
                System.out.println("Invalid username!");
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
        System.out.println("Name: " + account.getName());
        System.out.println("Username: " + account.getUsername());
        System.out.println("Email: " + account.getEmail());
        System.out.println("Date created: " + account.getCreated().toString());
        System.out.println("Password: " + passwordManager.decryptPassword(account.getPasswordEncrypted()));
    }

    private static int choose(String messageAtTheEnd, String... options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println(String.valueOf(i+1) + " - " + options[i]);
        }

        Scanner scanner = new Scanner(System.in);
        int answer;
        do {
            System.out.print(messageAtTheEnd);
            answer = scanner.nextInt();
        } while (answer <= 0 || answer > options.length);
        return answer;
    }

    private static boolean yesNoQuestion(String message) {
        System.out.print(message + " (y/n)");
        Scanner scanner = new Scanner(System.in);
        return scanner.next().toLowerCase().equals("y");
    }

    private static String askForReply(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    private static String askForReplySecure(String message) {
        System.out.print(message);
        return Arrays.toString(System.console().readPassword());
    }
}