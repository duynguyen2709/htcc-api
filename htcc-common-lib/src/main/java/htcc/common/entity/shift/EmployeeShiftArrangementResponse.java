package htcc.common.entity.shift;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
public class EmployeeShiftArrangementResponse implements Serializable {

    private static final long serialVersionUID = 59228111238519L;

    // Ngày
    public String date = "";
    // Danh sách ca
    public List<MiniShiftDetail> shiftList = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MiniShiftDetail implements Serializable {

        private static final long serialVersionUID = 5922818518L;

        // Văn phòng
        public String officeId;
        // Tên ca làm việc
        public String shiftId;
        // Thời gian làm việc
        public String shiftTime;

        public static Comparator<MiniShiftDetail> getComparator() {
            return new Comparator<MiniShiftDetail>() {
                @Override
                public int compare(MiniShiftDetail o1, MiniShiftDetail o2) {
                    return o1.getOfficeId().compareTo(o2.getOfficeId());
                }
            };
        }
    }

    public static Comparator<EmployeeShiftArrangementResponse> getComparator() {
        return new Comparator<EmployeeShiftArrangementResponse>() {
            @Override
            public int compare(EmployeeShiftArrangementResponse o1, EmployeeShiftArrangementResponse o2) {
                return Long.compare(Long.parseLong(o1.getDate()), Long.parseLong(o2.getDate()));
            }
        };
    }

}
