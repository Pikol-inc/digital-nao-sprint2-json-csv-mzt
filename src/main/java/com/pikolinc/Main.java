package com.pikolinc;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("🎓 SCIENTOMETRICS DEPARTMENT - AUTOMATION SYSTEM");
        System.out.println("================================================");
        System.out.println("Developer: Pikolinc");
        System.out.println("Purpose: Bi-monthly Scientific Production Report Generation\n");

        // Parse command line arguments for custom delimiter
        Config config = Config.fromArgs(args);
    }
}