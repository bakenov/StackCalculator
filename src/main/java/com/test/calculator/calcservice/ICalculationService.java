package com.test.calculator.calcservice;

import com.test.calculator.command.CalcCommand;

public interface ICalculationService {
    boolean processCommand(CalcCommand command);
}
