package htcc.common.entity.payslip;

import htcc.common.entity.base.BaseLogEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryLogEntity extends BaseLogEntity {

    private static final String TABLE_NAME = "SalaryLog";

    public String paySlipId = "";

    public String companyId = "";
    public String username = "";
    public int cycleType = 1; // 1 = monthly, 2 = weekly
    public String lastPaymentDate = "";
    public String nextPaymentDate = "";
    public String baseSalary = "";
    public String extraSalary = "";
    public String mealMoney = "";
    public String overtimeMoney = "";
    public String additionalIncome = "";
    public String latePenalty = "";
    public String nonPermissionOff = "";
    public String taxMoney = "";
    public String insuranceMoney = "";
    public String additionalPenalty = "";
    public String preTaxDependencyReduction = "";

    // for manager
    public String lockDate = "";
    public int status = 0;
    public String actor = "";

    // for employee payslip
    public long totalIncome = 0L;
    public long totalDeduction = 0L;
    public long totalNetPay = 0L;

    public SalaryLogEntity(SalaryFormula formula) {
        this.companyId = formula.getCompanyId();
        this.username = formula.getUsername();
        this.cycleType = formula.getCycleType();
        this.lastPaymentDate = formula.getLastPaymentDate();
        this.nextPaymentDate = formula.getNextPaymentDate();
        this.baseSalary = StringUtil.toJsonString(formula.getBaseSalary());
        this.extraSalary = StringUtil.toJsonString(formula.getExtraSalary());
        this.mealMoney = StringUtil.toJsonString(formula.getMealMoney());
        this.overtimeMoney = StringUtil.toJsonString(formula.getOvertimeMoney());
        this.additionalIncome = StringUtil.toJsonString(formula.getAdditionalIncome());
        this.latePenalty = StringUtil.toJsonString(formula.getLatePenalty());
        this.nonPermissionOff = StringUtil.toJsonString(formula.getNonPermissionOff());
        this.taxMoney = StringUtil.toJsonString(formula.getTaxMoney());
        this.insuranceMoney = StringUtil.toJsonString(formula.getInsuranceMoney());
        this.additionalPenalty = StringUtil.toJsonString(formula.getAdditionalPenalty());
        this.preTaxDependencyReduction = StringUtil.toJsonString(formula.getPreTaxDependencyReduction());

        //
        this.lockDate = StringUtil.EMPTY;
        this.status = 0;
        this.actor = StringUtil.EMPTY;

        //
        this.totalIncome = this.totalDeduction = this.totalNetPay = 0L;
    }

    @Override
    public Map<String, Object> getParamsMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("requestId", this.requestId);
        map.put("paySlipId", this.paySlipId);
        map.put("companyId", this.companyId);
        map.put("username", this.username);
        map.put("cycleType", this.cycleType);
        map.put("lastPaymentDate", this.lastPaymentDate);
        map.put("nextPaymentDate", this.nextPaymentDate);
        map.put("baseSalary", this.baseSalary);
        map.put("extraSalary", this.extraSalary);
        map.put("mealMoney", this.mealMoney);
        map.put("overtimeMoney", this.overtimeMoney);
        map.put("additionalIncome", this.additionalIncome);
        map.put("latePenalty", this.latePenalty);
        map.put("nonPermissionOff", this.nonPermissionOff);
        map.put("taxMoney", this.taxMoney);
        map.put("insuranceMoney", this.insuranceMoney);
        map.put("additionalPenalty", this.additionalPenalty);
        map.put("preTaxDependencyReduction", this.preTaxDependencyReduction);
        //
        map.put("lockDate", this.lockDate);
        map.put("status", this.status);
        map.put("actor", this.actor);
        //
        map.put("totalIncome", this.totalIncome);
        map.put("totalDeduction", this.totalDeduction);
        map.put("totalNetPay", this.totalNetPay);
        return map;
    }

    @Override
    public long getCreateTime() {
        Date dt = DateTimeUtil.parseStringToDate(lastPaymentDate, "yyyyMMdd");
        return dt != null ? dt.getTime() : System.currentTimeMillis();
    }

    @Override
    public String retrieveTableName() {
        return TABLE_NAME;
    }
}
