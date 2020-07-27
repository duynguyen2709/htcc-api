package htcc.log.service.mapper;

import htcc.common.entity.order.OrderLogEntity;
import htcc.common.entity.payslip.SalaryLogEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryLogRowMapper implements RowMapper<SalaryLogEntity> {

    @Override
    public SalaryLogEntity mapRow(ResultSet rs, int i) throws SQLException {
        SalaryLogEntity entity = new SalaryLogEntity();
        entity.paySlipId = rs.getString("paySlipId");
        entity.companyId = rs.getString("companyId");
        entity.username = rs.getString("username");
        entity.cycleType = rs.getInt("cycleType");
        entity.lastPaymentDate = rs.getString("lastPaymentDate");
        entity.nextPaymentDate = rs.getString("nextPaymentDate");
        entity.baseSalary = rs.getString("baseSalary");
        entity.extraSalary = rs.getString("extraSalary");
        entity.mealMoney = rs.getString("mealMoney");
        entity.overtimeMoney = rs.getString("overtimeMoney");
        entity.additionalIncome = rs.getString("additionalIncome");
        entity.latePenalty = rs.getString("latePenalty");
        entity.nonPermissionOff = rs.getString("nonPermissionOff");
        entity.taxMoney = rs.getString("taxMoney");
        entity.insuranceMoney = rs.getString("insuranceMoney");
        entity.additionalPenalty = rs.getString("additionalPenalty");
        entity.preTaxDependencyReduction = rs.getString("preTaxDependencyReduction");

        entity.lockDate = rs.getString("lockDate");
        entity.status = rs.getInt("status");
        entity.actor = rs.getString("actor");

        entity.totalIncome = rs.getLong("totalIncome");
        entity.totalDeduction = rs.getLong("totalDeduction");
        entity.totalNetPay = rs.getLong("totalNetPay");

        return entity;
    }
}
