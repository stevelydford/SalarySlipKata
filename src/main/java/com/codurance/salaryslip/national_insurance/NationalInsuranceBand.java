package com.codurance.salaryslip.national_insurance;

import static com.codurance.salaryslip.components.Money.zero;

import com.codurance.salaryslip.components.Money;

public class NationalInsuranceBand {

  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  public NationalInsuranceBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  public Money calculateFrom(Money annualSalary) {
    if (annualSalary.isBetweenAndInclusiveOf(lowerLimit, upperLimit)) {
      return calculateExcessFrom(annualSalary, lowerLimit).multiplyBy(rate);
    }

    if (annualSalary.isGreaterThan(upperLimit)) {
      return calculateExcessFrom(upperLimit, lowerLimit).multiplyBy(rate);
    }

    return zero();
  }

  public Money calculateExcessFrom(Money upperLimit, Money lowerLimit) {
    return upperLimit.minus(lowerLimit);
  }

  @Override
  public String toString() {
    return String.format("lowerLimit=%s, upperLimit=%s, rate=%s", lowerLimit, upperLimit, rate);
  }
}
