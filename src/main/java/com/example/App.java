package com.example;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        LOGGER.log(Level.INFO, () -> "Result: " + calc.calculate(10, 5, "add"));

       UserService userService = new UserService();

        userService.findUser("admin");
        userService.deleteUser("admin");
    }
}

