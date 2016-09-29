package SalarySlipKata.domain.bands;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain_service.PersonalAllowanceCalculator;

public class HigherTaxWithPersonalAllowanceReductionRuleBand implements Band {
  private TaxBand taxBand;
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      TaxBand taxBand, PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.taxBand = taxBand;
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  @Override
  public Money calculateFrom(Money annualSalary) {
    Money personalAllowanceAdjustmentForOver100K = zero();
    if (personalAllowanceCalculator.reductionRuleAppliesBetween(lowerLimit(), upperLimit())) {
      personalAllowanceAdjustmentForOver100K =
          personalAllowanceCalculator.calculateAdjustmentForExcessOver100K(annualSalary);
    }

    if (annualSalary.isBetweenAndInclusiveOf(lowerLimit(), upperLimit())) {
      final Money excessIncome = calculateExcessFrom(annualSalary, lowerLimit());
      return excessIncome.plus(personalAllowanceAdjustmentForOver100K).multiplyBy(rate());
    }

    if (annualSalary.isGreaterThan(upperLimit())) {
      final Money excessIncome = calculateExcessFrom(upperLimit(), lowerLimit());
      return excessIncome.plus(personalAllowanceAdjustmentForOver100K).multiplyBy(rate());
    }

    return zero();
  }

  @Override
  public Money calculateExcessFrom(Money upperLimit, Money lowerLimit) {
    return taxBand.calculateExcessFrom(upperLimit, lowerLimit);
  }

  @Override
  public Money lowerLimit() {
    return taxBand.lowerLimit();
  }

  @Override
  public Money upperLimit() {
    return taxBand.upperLimit();
  }

  @Override
  public double rate() {
    return taxBand.rate();
  }
}
