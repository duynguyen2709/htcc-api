package htcc.employee.service.service.salary;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.*;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.payslip.ManagerLockSalaryRequest;
import htcc.common.entity.payslip.SalaryModel;
import htcc.common.entity.payslip.SalaryFormula;
import htcc.common.entity.payslip.SalaryLogEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.LogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class SalaryCalculationService {

    @Autowired
    private SalaryFormulaService salaryFormulaService;

    @Autowired
    private LogService logService;

    public SalaryFormula createDefaultSalaryFormula(String companyId, String username) {
        SalaryFormula entity = new SalaryFormula();
        entity.setCompanyId(companyId);
        entity.setUsername(username);
        entity.setCycleType(PaymentCycleTypeEnum.MONTHLY.getValue());
        entity.setLastPaymentDate(DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse(entity.getLastPaymentDate(), formatter);
        LocalDate endDate = startDate.plusDays(startDate.lengthOfMonth());
        entity.setNextPaymentDate(endDate.format(formatter));
        entity.setAdditionalIncome(new ArrayList<>());
        entity.setAdditionalPenalty(new ArrayList<>());

        for (SalaryFormulaEnum formulaEnum : SalaryFormulaEnum.values()) {
            if (formulaEnum == SalaryFormulaEnum.TOTAL_BASE_SALARY) {
                continue;
            }

            SalaryFormula.DetailFormula formula = new SalaryFormula.DetailFormula();
            formula.setFormulaId(formulaEnum.getId());
            formula.setDescription(formulaEnum.getDescription());
            formula.setType(SalaryFormulaTypeEnum.DIRECT.getValue());
            formula.setValue(0);
            formula.setTotalAmount(0);
            formula.setIdBasedOn(StringUtil.EMPTY);
            formula.setCalcTax(false);

            if (formulaEnum == SalaryFormulaEnum.BASE) {
                formula.setValue(14_000_000L);
                formula.setCalcTax(true);
                entity.setBaseSalary(formula);
            }
            else if (formulaEnum == SalaryFormulaEnum.EXTRA) {
                formula.setValue(6_000_000L);
                formula.setCalcTax(true);
                entity.setExtraSalary(formula);
            }
            else if (formulaEnum == SalaryFormulaEnum.MEAL) {
                formula.setValue(990_000L);
                formula.setCalcTax(true);
                entity.setMealMoney(formula);
            }
            else if (formulaEnum == SalaryFormulaEnum.OVERTIME) {
                formula.setCalcTax(true);
                formula.setType(SalaryFormulaTypeEnum.TIMES.getValue());
                entity.setOvertimeMoney(formula);
            }
            else if (formulaEnum == SalaryFormulaEnum.LATE) {
                formula.setType(SalaryFormulaTypeEnum.TIMES.getValue());
                entity.setLatePenalty(formula);
            }
            else if (formulaEnum == SalaryFormulaEnum.NON_PERMISSION_OFF) {
                formula.setType(SalaryFormulaTypeEnum.TIMES.getValue());
                entity.setNonPermissionOff(formula);
            }
            else if (formulaEnum == SalaryFormulaEnum.TAX) {
                formula.setValue(10.0f);
                formula.setType(SalaryFormulaTypeEnum.PERCENTAGE.getValue());
                entity.setTaxMoney(formula);
            }
            else if (formulaEnum == SalaryFormulaEnum.INSURANCE) {
                formula.setValue(10.5f);
                formula.setType(SalaryFormulaTypeEnum.PERCENTAGE.getValue());
                formula.setIdBasedOn(SalaryFormulaEnum.BASE.getId());
                entity.setInsuranceMoney(formula);
            }
            else if (formulaEnum == SalaryFormulaEnum.PRE_TAX_REDUCTION) {
                formula.setValue(Constant.PRE_TAX_DEPENDENCY_REDUCTION);
                entity.setPreTaxDependencyReduction(formula);
            }
        }

        entity = salaryFormulaService.create(entity);
        return entity;
    }

    public boolean calculateSalary(SalaryFormula formula) {
        long totalIncomeWithTax = 0;
        long totalIncomeWithoutTax = 0;
        long totalDeductionBeforeTax = 0;
        long totalDeductionAfterTax = 0;

        Map<String, SalaryFormula.DetailFormula> formulaMap = new HashMap<>();
        formulaMap.put(SalaryFormulaEnum.BASE.getId(), formula.getBaseSalary());
        formulaMap.put(SalaryFormulaEnum.EXTRA.getId(), formula.getExtraSalary());
        formulaMap.put(SalaryFormulaEnum.MEAL.getId(), formula.getMealMoney());
        formulaMap.put(SalaryFormulaEnum.OVERTIME.getId(), formula.getOvertimeMoney());
        formulaMap.put(SalaryFormulaEnum.LATE.getId(), formula.getLatePenalty());
        formulaMap.put(SalaryFormulaEnum.NON_PERMISSION_OFF.getId(), formula.getNonPermissionOff());
        formulaMap.put(SalaryFormulaEnum.TAX.getId(), formula.getTaxMoney());
        formulaMap.put(SalaryFormulaEnum.INSURANCE.getId(), formula.getInsuranceMoney());
        formulaMap.put(SalaryFormulaEnum.PRE_TAX_REDUCTION.getId(), formula.getPreTaxDependencyReduction());
        for (SalaryFormula.DetailFormula detail : formula.getAdditionalIncome()) {
            formulaMap.put(detail.getFormulaId(), detail);
        }
        for (SalaryFormula.DetailFormula detail : formula.getAdditionalPenalty()) {
            formulaMap.put(detail.getFormulaId(), detail);
        }

        // Base Salary
        SalaryFormula.DetailFormula baseSalary = formula.getBaseSalary();
        long baseSalaryValue = 0;
        if (baseSalary.getType() == 1) {
           baseSalaryValue = (long)baseSalary.getValue();
        }
        else {
            float workingHours = countWorkingHours(formula);
            baseSalaryValue = (long)(workingHours * baseSalary.getValue());
        }
        baseSalary.setTotalAmount(baseSalaryValue);
        formula.setBaseSalary(baseSalary);
        totalIncomeWithTax += baseSalaryValue;

        // Extra Salary
        SalaryFormula.DetailFormula extraSalary = formula.getExtraSalary();
        long extraSalaryValue = (long)extraSalary.getValue();
        extraSalary.setTotalAmount(extraSalaryValue);
        formula.setExtraSalary(extraSalary);
        totalIncomeWithTax += extraSalaryValue;

        // Meal Money
        SalaryFormula.DetailFormula mealMoney = formula.getMealMoney();
        mealMoney.setTotalAmount((long)mealMoney.getValue());
        formula.setMealMoney(mealMoney);
        totalIncomeWithTax += (long)mealMoney.getValue();

        // Overtime Money
        SalaryFormula.DetailFormula overtimeMoney = formula.getOvertimeMoney();
        float overtimeHours = countOvertimeHours(formula);
        long overtimeValue = (long)(overtimeHours * formula.getOvertimeMoney().getValue());
        overtimeMoney.setTotalAmount(overtimeValue);
        formula.setOvertimeMoney(overtimeMoney);
        totalIncomeWithTax += overtimeValue;

        List<SalaryFormula.DetailFormula> additionIncomeList = new ArrayList<>();
        for (int i = 0; i< formula.getAdditionalIncome().size(); i++) {
            SalaryFormula.DetailFormula detailIncome = formula.getAdditionalIncome().get(i);
            long totalValue = 0;
            if (detailIncome.getType() == 1) {
                totalValue = (long)detailIncome.getValue();
            }
            else if (detailIncome.getType() == 2) {
                long valueBasedOn = 0;

                if (detailIncome.getIdBasedOn().equals(SalaryFormulaEnum.TOTAL_BASE_SALARY.getId())) {
                    valueBasedOn = formula.getBaseSalary().getTotalAmount() + formula.getExtraSalary().getTotalAmount();
                }
                else {
                    SalaryFormula.DetailFormula basedOn = getBasedOnFormula(formula, detailIncome.getIdBasedOn());
                    if (basedOn != null) {
                        valueBasedOn = basedOn.getTotalAmount();
                    }
                }
                totalValue = (long)(valueBasedOn * detailIncome.getValue() / 100);
            }
            detailIncome.setTotalAmount(totalValue);
            additionIncomeList.add(detailIncome);

            if (detailIncome.isCalcTax()) {
                totalIncomeWithTax += totalValue;
            }
            else {
                totalIncomeWithoutTax += totalValue;
            }
        }
        formula.setAdditionalIncome(additionIncomeList);

        // Late Penalty
        SalaryFormula.DetailFormula latePenalty = formula.getLatePenalty();
        float lateHours = countLateHours(formula);
        long latePenaltyValue = (long)(lateHours * latePenalty.getValue());
        latePenalty.setTotalAmount(latePenaltyValue);
        formula.setLatePenalty(latePenalty);
        totalDeductionBeforeTax += latePenaltyValue;

        // Non Permission Off
        SalaryFormula.DetailFormula nonPermissionOff = formula.getNonPermissionOff();
        float nonPermissionOffDays = countNonPermissionOffDays(formula);
        long nonPermissionOffValue = (long)(nonPermissionOffDays * nonPermissionOff.getValue());
        nonPermissionOff.setTotalAmount(nonPermissionOffValue);
        formula.setNonPermissionOff(nonPermissionOff);
        totalDeductionBeforeTax += nonPermissionOffValue;

        // Insurance
        SalaryFormula.DetailFormula insurance = formula.getInsuranceMoney();
        long insuranceValue = (long)(insurance.getValue() * formula.getBaseSalary().getTotalAmount() / 100);
        insurance.setTotalAmount(insuranceValue);
        formula.setInsuranceMoney(insurance);

        // Pre Tax Dependency Reduction
        SalaryFormula.DetailFormula preTaxReduction = formula.getPreTaxDependencyReduction();
        long preTaxReductionValue = (long)preTaxReduction.getValue();
        preTaxReduction.setTotalAmount(preTaxReductionValue);
        formula.setPreTaxDependencyReduction(preTaxReduction);

        // Additional Deduction
        List<SalaryFormula.DetailFormula> additionPenaltyList = new ArrayList<>();
        for (int i = 0; i< formula.getAdditionalPenalty().size(); i++) {
            SalaryFormula.DetailFormula penalty = formula.getAdditionalPenalty().get(i);
            long totalValue = 0;
            if (penalty.getType() == 1) {
                totalValue = (long)penalty.getValue();
            }
            else if (penalty.getType() == 2) {
                long valueBasedOn = 0;

                if (penalty.getIdBasedOn().equals(SalaryFormulaEnum.TOTAL_BASE_SALARY.getId())) {
                    valueBasedOn = formula.getBaseSalary().getTotalAmount() + formula.getExtraSalary().getTotalAmount();
                }
                else {
                    SalaryFormula.DetailFormula basedOn = getBasedOnFormula(formula, penalty.getIdBasedOn());
                    if (basedOn != null) {
                        valueBasedOn = basedOn.getTotalAmount();
                    }
                }
                totalValue = (long)(valueBasedOn * penalty.getValue() / 100);
            }
            penalty.setTotalAmount(totalValue);
            additionPenaltyList.add(penalty);

            if (penalty.isCalcTax()) {
                totalDeductionBeforeTax += totalValue;
            }
            else {
                totalDeductionAfterTax += totalValue;
            }
        }
        formula.setAdditionalPenalty(additionPenaltyList);

        // Tax
        SalaryFormula.DetailFormula tax = formula.getTaxMoney();
        long beforeTax = (long)(totalIncomeWithTax - insuranceValue - totalDeductionBeforeTax - formula.getPreTaxDependencyReduction().getValue());
        long taxValue = (long)(beforeTax * tax.getValue() / 100);
        if (taxValue < 0) {
            taxValue = 0;
        }
        tax.setTotalAmount(taxValue);
        formula.setTaxMoney(tax);

        long totalIncome = totalIncomeWithTax + totalIncomeWithoutTax;
        long totalDeduction = totalDeductionBeforeTax + totalDeductionAfterTax + taxValue + insuranceValue;

        // call Log Service
        BaseResponse response = logService.insertSalaryLog(formula, totalIncome, totalDeduction);
        return (response != null &&
                response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue() &&
                Long.parseLong(StringUtil.valueOf(response.getData())) != -1);
    }

    private SalaryFormula.DetailFormula getBasedOnFormula(SalaryFormula formula, String idBasedOn) {
        if (idBasedOn.equals(SalaryFormulaEnum.BASE.getId())) {
            return formula.getBaseSalary();
        }
        if (idBasedOn.equals(SalaryFormulaEnum.EXTRA.getId())) {
            return formula.getExtraSalary();
        }
        if (idBasedOn.equals(SalaryFormulaEnum.MEAL.getId())) {
            return formula.getMealMoney();
        }
        if (idBasedOn.equals(SalaryFormulaEnum.OVERTIME.getId())) {
            return formula.getOvertimeMoney();
        }

        for (SalaryFormula.DetailFormula detail : formula.getAdditionalIncome()) {
            if (detail.getFormulaId().equals(idBasedOn)) {
                return detail;
            }
        }
        return null;
    }

    private float countNonPermissionOffDays(SalaryFormula formula) {
        return 0.0f;
    }

    private float countLateHours(SalaryFormula formula) {
        return 3.5f;
    }

    private float countOvertimeHours(SalaryFormula formula) {
        return 10.0f;
    }

    private float countWorkingHours(SalaryFormula formula) {
        return 8 * 22.0f;
    }

    public BaseResponse deleteSalaryLog(String yyyyMM, String paySlipId) {
        return logService.deleteSalaryLogForManager(yyyyMM, paySlipId);
    }

    public BaseResponse lockSalaryLog(ManagerLockSalaryRequest request) {
        return logService.lockSalaryLog(request);
    }

    public List<SalaryModel> getSalaryLog(String companyId, String yyyyMM) {
        try {
            BaseResponse response = logService.getSalaryLogForManager(companyId, yyyyMM);
            if (response == null || response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                throw new Exception("logService.getSalaryLogForManager return " + StringUtil.toJsonString(response));
            }

            String rawData = StringUtil.toJsonString(response.getData());
            List<SalaryLogEntity> salaryLogEntityList = StringUtil.json2Collection(rawData,
                    new TypeToken<List<SalaryLogEntity>>() {}.getType());

            List<SalaryModel> dataRes = new ArrayList<>();
            for (SalaryLogEntity log : salaryLogEntityList) {
                dataRes.add(new SalaryModel(log));
            }
            return dataRes;
        } catch (Exception e) {
            log.error("[getSalaryLog] [{}-{}] ex", companyId, yyyyMM, e);
            return null;
        }
    }

    public List<SalaryModel> getPayslip(String companyId, String username, String yyyyMM) {
        try {
            BaseResponse response = logService.getPayslipForEmployee(companyId, username, yyyyMM);
            if (response == null || response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                throw new Exception("logService.getPayslipForEmployee return " + StringUtil.toJsonString(response));
            }

            String rawData = StringUtil.toJsonString(response.getData());
            List<SalaryLogEntity> salaryLogEntityList = StringUtil.json2Collection(rawData,
                    new TypeToken<List<SalaryLogEntity>>() {}.getType());

            List<SalaryModel> dataRes = new ArrayList<>();
            for (SalaryLogEntity log : salaryLogEntityList) {
                dataRes.add(new SalaryModel(log));
            }
            return dataRes;
        } catch (Exception e) {
            log.error("[getPayslip] [{}-{}-{}] ex", companyId, yyyyMM, e);
            return new ArrayList<>();
        }
    }
}
