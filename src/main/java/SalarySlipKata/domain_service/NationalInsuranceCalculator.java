package SalarySlipKata.domain_service;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;

public class NationalInsuranceCalculator {

  private enum ContributionBands {
    HIGHER_CONTRIBUTION(new Money(43_000.00), 0.02),
    BASIC_CONTRIBUTION(new Money(8_060.00), 0.12);

    private final Money limit;
    private final double rate;

    ContributionBands(Money limit, double rate) {
      this.limit = limit;
      this.rate = rate;
    }
  }

  private static final int TWELVE_MONTHS = 12;

  public Money calculateMonthlyContributionsFor(Money annualSalary) {
    return calculateContributionsFor(annualSalary).divideBy(TWELVE_MONTHS);
  }

  private Money calculateContributionsFor(Money originalAnnualSalary) {
    Money annualSalary = new Money(originalAnnualSalary);
    Money contributions = zero();

    for(ContributionBands contribution: ContributionBands.values()) {
      Money excessIncomeForBand = calculateExcessIncomeForBandWith(annualSalary, contribution.limit);

      contributions = contributions.plus(
          calculateContribution(contribution.rate, excessIncomeForBand)
      );

      annualSalary = annualSalary.minus(excessIncomeForBand);
    }

    return contributions;
  }

  private Money calculateContribution(Double contributionRate, Money difference) {
    return difference.multiplyBy(contributionRate);
  }

  private Money calculateExcessIncomeForBandWith(Money annualSalary, Money contributionStartAmount) {
    return annualSalary.minus(contributionStartAmount);
  }
}
