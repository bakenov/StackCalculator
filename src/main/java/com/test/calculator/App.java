package com.test.calculator;

import com.test.calculator.calcservice.CalculationService;
import com.test.calculator.command.CommandBuilder;
import com.test.calculator.stack.RestorableStack;
import com.test.calculator.ui.ApplicationLoop;

public class App {

    private ApplicationLoop loop;

    public App() {
        CalculationService service = new CalculationService(new RestorableStack(20), System.out);
        loop = new ApplicationLoop(new CommandBuilder(System.out), service, System.out);
    }

    public static void main(String[] args) {
        App app = new App();
        app.loop.start();
    }

}
