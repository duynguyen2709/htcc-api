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
import java.util.List;

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
                entity.setBaseSalary(formula);
            }
            else if (formulaEnum == SalaryFormulaEnum.EXTRA) {
                formula.setValue(6_000_000L);
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
        BaseResponse response = logService.insertSalaryLog(formula);
        return (response != null &&
                response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue() &&
                Long.parseLong(StringUtil.valueOf(response.getData())) != -1);
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
