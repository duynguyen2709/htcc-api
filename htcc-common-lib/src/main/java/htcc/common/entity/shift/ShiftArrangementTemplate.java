package htcc.common.entity.shift;

import com.google.gson.reflect.TypeToken;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ShiftArrangementTemplate extends BaseJPAEntity {

    private static final long serialVersionUID = 59225708L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int templateId;

    @Column
    public String companyId = "";

    @Column
    public String templateName = "";

    @Column
    public String data = "";

    @Column
    public String actor = "";

    @Transient
    public List<MiniShiftTime> shiftTimeList = new ArrayList<>();

    public void setData(List<MiniShiftTime> shiftTimeList) {
        this.data = StringUtil.toJsonString(shiftTimeList);
    }

    public List<MiniShiftTime> getData() {
        return StringUtil.json2Collection(this.data, new TypeToken<List<MiniShiftTime>>(){}.getType());
    }

    @Override
    public String isValid() {

        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(templateName)) {
            return "Tên ca không được rỗng";
        }

        return StringUtil.EMPTY;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MiniShiftTime implements Serializable {

        private static final long serialVersionUID = 5922818518L;

        public int    weekDay;
        public String officeId;
        public String shiftId;
        public String shiftName;
        public String startTime;
        public String endTime;

        public static Comparator<MiniShiftTime> getComparator() {
            return new Comparator<MiniShiftTime>() {
                @Override
                public int compare(MiniShiftTime o1, MiniShiftTime o2) {
                    return Integer.compare(o1.getWeekDay(), o2.getWeekDay());
                }
            };
        }
    }
}
