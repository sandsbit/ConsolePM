package me.nikitaserba.consolepm;


public class MainCLI {
    public static void main(String[] args) {
        printWelcome();
        PasswordManager pm = authenticate();
        accountListInterface(pm);
    }

    private static void printWelcome() {
        System.out.println("Welcome to ConsolePM!");
    }

    private static PasswordManager authenticate() {

    }

    private static void accountListInterface(PasswordManager passwordManager) {

    }
}