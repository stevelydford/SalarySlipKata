package SalarySlipKata.infrastructure;

import static java.lang.String.*;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDate;

import SalarySlipKata.domain.EmployeeId;
import SalarySlipKata.domain.GBP;
import SalarySlipKata.donain_service.SalaryService;

public class StandardSalarySlipPrinter {
  private static final int FIXED_LENGTH_FOR_AMOUNT = 8;

  private static final String CURRENT_DATE_FORMAT = "dd MMM yyyy";
  private static final String SALARY_PERIOD_FORMAT = "MMM yyyy";

  private static final String STANDARD_PAY_SLIP_FORMAT =
      "Date: %s            Salary for period: %s%n" +
      "                                                        %n" +
      "Employee ID: %s                                      %n" +
      "                                                        %n" +
      "SALARY                    DEDUCTION                     %n" +
      "Basic           %s  National Insurance    %s%n" +
      "Bonus           %s  Tax                   %s%n" +
      "Overtime        %s                                %n" +
      "                                                        %n" +
      "Gross salary    %s  Net payable           %s";

  private final Console console;
  private final Clock clock;
  private final SalaryService salaryService;

  public StandardSalarySlipPrinter(Console console, Clock clock, SalaryService salaryService) {
    this.console = console;
    this.clock = clock;
    this.salaryService = salaryService;
  }

  public void printSalaryFor(EmployeeId employeeId) {
    String currentDate =
        getFormattedDate(CURRENT_DATE_FORMAT, clock.getCurrentDate());
    String salaryPeriod =
        getFormattedDate(SALARY_PERIOD_FORMAT, clock.getCurrentDate());

    console.print(
        format(STANDARD_PAY_SLIP_FORMAT,
            currentDate,
            salaryPeriod,
            employeeId,
            leftPadWithSpaces(salaryService.getBasicSalaryFor(employeeId), FIXED_LENGTH_FOR_AMOUNT),
            leftPadWithSpaces(salaryService.getNationalInsuranceFor(employeeId), FIXED_LENGTH_FOR_AMOUNT),
            leftPadWithSpaces(salaryService.getBonus(employeeId), FIXED_LENGTH_FOR_AMOUNT),
            leftPadWithSpaces(salaryService.getTax(employeeId), FIXED_LENGTH_FOR_AMOUNT),
            leftPadWithSpaces(salaryService.getOvertime(employeeId), FIXED_LENGTH_FOR_AMOUNT),
            leftPadWithSpaces(salaryService.getGrossSalary(employeeId), FIXED_LENGTH_FOR_AMOUNT),
            leftPadWithSpaces(salaryService.getNetPayable(employeeId), FIXED_LENGTH_FOR_AMOUNT)
        )
    );
  }

  private String leftPadWithSpaces(GBP amount, int totalLength) {
    return format("%1$"+totalLength+ "s", amount.toString());
  }

  private String getFormattedDate(String pattern, LocalDate date) {
    return date.format(ofPattern(pattern));
  }
}
