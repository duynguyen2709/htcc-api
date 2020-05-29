package htcc.common.entity.shift;

import com.fasterxml.jackson.annotation.JsonIgnore;
import htcc.common.entity.jpa.EmployeeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ShiftArrangementResponse implements Serializable {
    private static final long serialVersionUID = 19263258350806L;
    public int week = 0;
    public int year = 0;

    public List<OfficeShiftInfo> shiftByDateList = new ArrayList<>();
    public List<OfficeShiftInfo> fixedShiftList = new ArrayList<>();
    public List<EmployeeInfo> canManageEmployees = new ArrayList<>();

    @JsonIgnore
    public transient Map<String, EmployeeInfo> canManageEmployeesMap = new HashMap<>();

    public OfficeShiftInfo findOfficeShiftInfo(String officeId, boolean isFixed) {
        if (isFixed) {
            for (OfficeShiftInfo office : this.fixedShiftList) {
                if (office.getOfficeId().equals(officeId)) {
                    return office;
                }
            }
        }
        else {
            for (OfficeShiftInfo office : this.shiftByDateList) {
                if (office.getOfficeId().equals(officeId)) {
                    return office;
                }
            }
        }
        return null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OfficeShiftInfo implements Serializable {
        private static final long serialVersionUID = 19263258350807L;
        public String officeId = "";
        public List<ShiftDetail> shiftDetailList = new ArrayList<>();

        public ShiftDetail findShiftDetail(String _shiftId) {
            for (ShiftDetail shiftDetail : this.shiftDetailList) {
                if (shiftDetail.getShiftId().equals(_shiftId)) {
                    return shiftDetail;
                }
            }
            return null;
        }

        public OfficeShiftInfo copy(){
            OfficeShiftInfo entity = new OfficeShiftInfo();
            entity.officeId = this.officeId;
            entity.shiftDetailList = new ArrayList<>();

            for (ShiftDetail shiftDetail : this.shiftDetailList) {
                ShiftDetail cloneShiftDetail = new ShiftDetail();
                cloneShiftDetail.shiftId = shiftDetail.shiftId;
                cloneShiftDetail.shiftName = shiftDetail.shiftName;
                cloneShiftDetail.shiftTime = shiftDetail.shiftTime;
                cloneShiftDetail.detailList = new ArrayList<>();

                for (ShiftByDayDetail shiftByDayDetail : shiftDetail.detailList){
                    ShiftByDayDetail cloneShiftByDayDetail = new ShiftByDayDetail();
                    cloneShiftByDayDetail.date = shiftByDayDetail.date;
                    cloneShiftByDayDetail.weekDay = shiftByDayDetail.weekDay;
                    cloneShiftByDayDetail.employeeList = new ArrayList<>();

                    for (EmployeeShiftInfo employee : shiftByDayDetail.employeeList) {
                        EmployeeShiftInfo cloneEmployee = new EmployeeShiftInfo(employee);
                        cloneShiftByDayDetail.employeeList.add(cloneEmployee);
                    }
                    cloneShiftDetail.detailList.add(cloneShiftByDayDetail);
                }
                entity.shiftDetailList.add(cloneShiftDetail);
            }
            return entity;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShiftDetail implements Serializable {
        private static final long serialVersionUID = 19263258350808L;
        public String shiftId = "";
        public String shiftName = "";
        public String shiftTime = "";
        public List<ShiftByDayDetail> detailList= new ArrayList<>();

        public ShiftByDayDetail findShiftByDateDetail(String _date, boolean isFixed) {
            for (ShiftByDayDetail detail : this.detailList) {
                if (isFixed) {
                    if (_date.equals(detail.getWeekDay() + "")) {
                        return detail;
                    }
                }
                else {
                    if (detail.getDate().equals(_date)) {
                        return detail;
                    }
                }
            }
            return null;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShiftByDayDetail implements Serializable {
        private static final long serialVersionUID = 19263258350809L;
        public String date = "";
        public int weekDay = 0;
        public List<EmployeeShiftInfo> employeeList = new ArrayList<>();
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeShiftInfo implements Serializable {
        private static final long serialVersionUID = 19263258350810L;
        public int type = 1;
        public String arrangeId   = "";
        public String username    = "";

        public EmployeeShiftInfo(String username, String arrangeId, int type) {
            this.type = type;
            this.arrangeId = arrangeId;
            this.username = username;
        }

        public EmployeeShiftInfo(EmployeeShiftInfo e) {
            this.type = e.type;
            this.arrangeId = e.arrangeId;
            this.username = e.username;
        }
    }
}
