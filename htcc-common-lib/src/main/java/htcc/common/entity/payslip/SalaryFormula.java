package htcc.common.entity.payslip;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.SalaryFormulaEnum;
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
import java.util.*;

@Entity
@IdClass(SalaryFormula.Key.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class SalaryFormula extends BaseJPAEntity {

    private static final long serialVersionUID = 2711L;

    @Id
    private String companyId = "";
    @Id
    private String username = "";
    @Column
    private int cycleType = 1; // 1 = monthly, 2 = weekly
    @Column
    private String lastPaymentDate = "";
    @Column
    private String nextPaymentDate = "";

    // Incomes
    @Column
    private String baseSalary = "";
    @Column
    private String extraSalary = "";
    @Column
    private String mealMoney = "";
    @Column
    private String overtimeMoney = "";
    @Column
    private String additionalIncome = "";

    // Deductions
    @Column
    private String latePenalty = "";
    @Column
    private String nonPermissionOff = "";
    @Column
    private String taxMoney = "";
    @Column
    private String insuranceMoney = "";
    @Column
    private String additionalPenalty = "";

    // Extra
    @Column
    private String preTaxDependencyReduction = "";

    // Base Salary
    public DetailFormula getBaseSalary() {
        return StringUtil.fromJsonString(this.baseSalary, DetailFormula.class);
    }
    public void setBaseSalary(DetailFormula formula) {
        this.baseSalary = StringUtil.toJsonString(formula);
    }

    // Extra Salary
    public DetailFormula getExtraSalary() {
        return StringUtil.fromJsonString(this.extraSalary, DetailFormula.class);
    }
    public void setExtraSalary(DetailFormula formula) {
        this.extraSalary = StringUtil.toJsonString(formula);
    }

    // Meal Money
    public DetailFormula getMealMoney() {
        return StringUtil.fromJsonString(this.mealMoney, DetailFormula.class);
    }
    public void setMealMoney(DetailFormula formula) {
        this.mealMoney = StringUtil.toJsonString(formula);
    }

    // Overtime Money
    public DetailFormula getOvertimeMoney() {
        return StringUtil.fromJsonString(this.overtimeMoney, DetailFormula.class);
    }
    public void setOvertimeMoney(DetailFormula formula) {
        this.overtimeMoney = StringUtil.toJsonString(formula);
    }


    // Additional Income
    public List<DetailFormula> getAdditionalIncome() {
        return StringUtil.json2Collection(this.additionalIncome, new TypeToken<List<DetailFormula>>() {}.getType());
    }
    public void setAdditionalIncome(List<DetailFormula> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.additionalIncome = StringUtil.toJsonString(list);
    }

    // Additional Penalty
    public List<DetailFormula> getAdditionalPenalty() {
        return StringUtil.json2Collection(this.additionalPenalty, new TypeToken<List<DetailFormula>>() {}.getType());
    }
    public void setAdditionalPenalty(List<DetailFormula> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.additionalPenalty = StringUtil.toJsonString(list);
    }

    // Tax Money
    public DetailFormula getTaxMoney() {
        return StringUtil.fromJsonString(this.taxMoney, DetailFormula.class);
    }
    public void setTaxMoney(DetailFormula formula) {
        this.taxMoney = StringUtil.toJsonString(formula);
    }

    // Insurance Money
    public DetailFormula getInsuranceMoney() {
        return StringUtil.fromJsonString(this.insuranceMoney, DetailFormula.class);
    }
    public void setInsuranceMoney(DetailFormula formula) {
        this.insuranceMoney = StringUtil.toJsonString(formula);
    }

    // Late Penalty
    public DetailFormula getLatePenalty() {
        return StringUtil.fromJsonString(this.latePenalty, DetailFormula.class);
    }
    public void setLatePenalty(DetailFormula formula) {
        this.latePenalty = StringUtil.toJsonString(formula);
    }

    // Non Permission Off
    public DetailFormula getNonPermissionOff() {
        return StringUtil.fromJsonString(this.nonPermissionOff, DetailFormula.class);
    }
    public void setNonPermissionOff(DetailFormula formula) {
        this.nonPermissionOff = StringUtil.toJsonString(formula);
    }

    // Pre Tax Dependency Reduction
    public DetailFormula getPreTaxDependencyReduction() {
        return StringUtil.fromJsonString(this.preTaxDependencyReduction, DetailFormula.class);
    }
    public void setPreTaxDependencyReduction(DetailFormula formula) {
        this.preTaxDependencyReduction = StringUtil.toJsonString(formula);
    }

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không hợp lệ";
        }
        if (StringUtil.isEmpty(username)) {
            return "Tên nhân viên không hợp lệ";
        }

        if (getBaseSalary().getValue() <= 0) {
            return "Lương cơ bản phải lớn hơn 0";
        }

        if (!StringUtil.isEmpty(lastPaymentDate) && !StringUtil.isEmpty(nextPaymentDate)) {
            if (!DateTimeUtil.isRightFormat(lastPaymentDate, "yyyyMMdd") ||
                    !DateTimeUtil.isRightFormat(nextPaymentDate, "yyyyMMdd")) {
                return "Định dạng ngày không hợp lệ";
            }

            if (Long.parseLong(lastPaymentDate) >= Long.parseLong(nextPaymentDate)) {
                return "Ngày trả lương tiếp theo phải sau ngày trả lương trước";
            }
        }
        Set<String> formulaIdSet = new HashSet<>();
        for (SalaryFormulaEnum formulaEnum : SalaryFormulaEnum.values()) {
            formulaIdSet.add(formulaEnum.getId());
        }

        for (DetailFormula detail : this.getAdditionalIncome()) {
            if (formulaIdSet.contains(detail.getFormulaId())) {
                return "Mã công thức " + detail.getFormulaId() + " đã tồn tại";
            }
            formulaIdSet.add(detail.getFormulaId());
        }

        for (DetailFormula detail : this.getAdditionalPenalty()) {
            if (formulaIdSet.contains(detail.getFormulaId())) {
                return "Mã công thức " + detail.getFormulaId() + " đã tồn tại";
            }
            formulaIdSet.add(detail.getFormulaId());
        }

        return StringUtil.EMPTY;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {

        private String companyId;
        private String username;

        public Key(String key){
            this.companyId = key.split("_")[0];
            this.username = key.split("_")[1];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SalaryFormula.Key that = (SalaryFormula.Key) o;
            return (companyId.equalsIgnoreCase(that.companyId) &&
                    username.equalsIgnoreCase(that.username));
        }

        @Override
        public int hashCode() {
            return Objects.hash(companyId, username);
        }
    }

    @Data
    @NoArgsConstructor
    public static class DetailFormula implements Serializable {
        private static final long serialVersionUID = 2710L;

        private String formulaId = "";
        private String description = "";
        private int type = 1;
        private float value = 0.0f;
        private String idBasedOn = "";
        private boolean calcTax = false;
        private long totalAmount = 0L;
    }
}
