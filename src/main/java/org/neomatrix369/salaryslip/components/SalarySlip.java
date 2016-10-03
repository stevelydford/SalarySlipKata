package org.neomatrix369.salaryslip.components;

import static java.lang.String.*;

import java.util.Objects;

import org.neomatrix369.salaryslip.tax.TaxDetails;

public class SalarySlip {
  private final Employee employee;
  private final Money grossSalary;
  private final TaxDetails taxDetails;
  private final Money niContributions;
  private final Money netPayable;

  public SalarySlip(
      Employee employee, Money grossSalary, TaxDetails taxDetails, Money niContributions) {
    this.employee = employee;
    this.grossSalary = grossSalary;
    this.taxDetails = taxDetails;
    this.niContributions = niContributions;
    this.netPayable = calculateNetPayableWith(grossSalary, taxDetails.taxPayable(), niContributions);
  }

  private Money calculateNetPayableWith(
      Money grossSalary, Money taxPayable, Money niContributions) {
    return grossSalary
              .minus(taxPayable)
              .minus(niContributions);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SalarySlip that = (SalarySlip) o;
    return Objects.equals(employee, that.employee) &&
        Objects.equals(grossSalary, that.grossSalary) &&
        Objects.equals(taxDetails, that.taxDetails) &&
        Objects.equals(niContributions, that.niContributions) &&
        Objects.equals(netPayable, that.netPayable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employee, grossSalary, taxDetails, niContributions, netPayable);
  }

  @Override
  public String toString() {
    return format("employee=%s\n grossSalary=%s\n taxDetails=%s\n niContributions=%s\n netPayable=%s",
        employee, grossSalary, taxDetails, niContributions, netPayable);
  }
}
