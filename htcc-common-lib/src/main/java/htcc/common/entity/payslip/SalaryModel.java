package htcc.common.entity.payslip;

import com.google.gson.reflect.TypeToken;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryModel implements Serializable {

    private static final long serialVersionUID = 2712L;
    private String paySlipId = "";
    private String companyId = "";
    private String username = "";

    private int cycleType = 1; // 1 = monthly, 2 = weekly
    private String lastPaymentDate = "";
    private String nextPaymentDate = "";

    private SalaryFormula.DetailFormula baseSalary;
    private SalaryFormula.DetailFormula extraSalary;
    private SalaryFormula.DetailFormula mealMoney;
    private SalaryFormula.DetailFormula overtimeMoney;
    private List<SalaryFormula.DetailFormula> additionalIncome;
    private SalaryFormula.DetailFormula latePenalty;
    private SalaryFormula.DetailFormula nonPermissionOff;
    private SalaryFormula.DetailFormula taxMoney;
    private SalaryFormula.DetailFormula insuranceMoney;
    private List<SalaryFormula.DetailFormula> additionalPenalty;
    private SalaryFormula.DetailFormula preTaxDependencyReduction;

    private String lockDate = "";
    private int status = 0;
    private String actor = "";

    private long totalIncome = 0L;
    private long totalDeduction = 0L;
    private long totalNetPay = 0L;

    public SalaryModel(SalaryLogEntity entity) {
        this.paySlipId = entity.getPaySlipId();
        this.companyId = entity.getCompanyId();
        this.username = entity.getUsername();
        this.cycleType = entity.getCycleType();
        this.lastPaymentDate = entity.getLastPaymentDate();
        this.nextPaymentDate = entity.getNextPaymentDate();
        this.lockDate = entity.getLockDate();
        this.status = entity.getStatus();
        this.actor = entity.getActor();
        this.totalIncome = entity.getTotalIncome();
        this.totalDeduction = entity.getTotalDeduction();
        this.totalNetPay = entity.getTotalNetPay();
        this.baseSalary = StringUtil.fromJsonString(entity.getBaseSalary(),
                SalaryFormula.DetailFormula.class);
        this.extraSalary = StringUtil.fromJsonString(entity.getExtraSalary(),
                SalaryFormula.DetailFormula.class);
        this.mealMoney = StringUtil.fromJsonString(entity.getMealMoney(),
                SalaryFormula.DetailFormula.class);
        this.overtimeMoney = StringUtil.fromJsonString(entity.getOvertimeMoney(),
                SalaryFormula.DetailFormula.class);
        this.latePenalty = StringUtil.fromJsonString(entity.getLatePenalty(),
                SalaryFormula.DetailFormula.class);
        this.nonPermissionOff = StringUtil.fromJsonString(entity.getNonPermissionOff(),
                SalaryFormula.DetailFormula.class);
        this.taxMoney = StringUtil.fromJsonString(entity.getTaxMoney(),
                SalaryFormula.DetailFormula.class);
        this.insuranceMoney = StringUtil.fromJsonString(entity.getInsuranceMoney(),
                SalaryFormula.DetailFormula.class);
        this.preTaxDependencyReduction = StringUtil.fromJsonString(entity.getPreTaxDependencyReduction(),
                SalaryFormula.DetailFormula.class);
        this.additionalIncome = StringUtil.json2Collection(entity.getAdditionalIncome(),
                new TypeToken<List<SalaryFormula.DetailFormula>>() {}.getType());
        this.additionalPenalty = StringUtil.json2Collection(entity.getAdditionalPenalty(),
                new TypeToken<List<SalaryFormula.DetailFormula>>() {}.getType());

    }
}
