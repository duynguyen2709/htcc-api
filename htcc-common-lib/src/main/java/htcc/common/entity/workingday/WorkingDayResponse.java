package htcc.common.entity.workingday;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class WorkingDayResponse {

    private static final long serialVersionUID = 592281212515708L;

    // officeId, List (by Date)
    private Map<String, List<WorkingDayDetail>> detailMap = new HashMap<>();

    @Data
    @NoArgsConstructor
    public static class WorkingDayDetail {
        private String date = "";
        // Buổi nghỉ/làm việc (0 : cả ngày/ 1: buổi sáng / 2: buổi chiều)
        private int session = 0;
        // Ngày đó có làm việc hay không
        private Boolean isWorking = true;
        // Thông tin thêm về ngày nghỉ/ ngày đi làm đặc biệt (vd: nghỉ lễ 30/4)
        private String extraInfo = "";

        public WorkingDayDetail(WorkingDay day) {
            this.date = day.getDate();
            this.session = day.getSession();
            this.isWorking = day.getIsWorking();
            this.extraInfo = day.getExtraInfo();
        }

        public static Comparator<WorkingDayDetail> getComparator() {
            return new Comparator<WorkingDayResponse.WorkingDayDetail>() {
                @Override
                public int compare(WorkingDayResponse.WorkingDayDetail o1, WorkingDayResponse.WorkingDayDetail o2) {
                    return Long.compare(Long.parseLong(o1.getDate()), Long.parseLong(o2.getDate()));
                }
            };
        }
    }
}
