package com.test.calculator.ui;

import com.test.calculator.calcservice.ICalculationService;
import com.test.calculator.command.CalcCommand;
import com.test.calculator.command.ICommandHandler;

import java.io.PrintStream;
import java.util.Scanner;

import static com.test.calculator.command.CommandType.QUIT;

public class ApplicationLoop {

    private Scanner scan;
    private volatile boolean isRunning;
    private final PrintStream out;
    private final ICommandHandler handler;
    private final ICalculationService service;


    public ApplicationLoop(final ICommandHandler handler, final ICalculationService service, final PrintStream out) {
        this.handler = handler;
        this.service = service;
        this.out = out;
    }

    public void start() {
        if (isRunning)
            return;
        isRunning = true;
        run();
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            scan.close();
        }
    }

    /**
     * A run method that processes the user's input instructions and blocks the
     * current thread while waiting the user input.
     */
    private void run() {
        scan = new Scanner(System.in);
        while (isRunning) {
            out.println("Please enter command:");
            CalcCommand command = handler.processCommand(scan.nextLine());
            if (command.getType() == QUIT) {
                stop();
            } else {
                service.processCommand(command);
            }
        }
    }
}
