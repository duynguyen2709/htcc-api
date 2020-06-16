package htcc.common.entity.shift;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Comparator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiniShiftTime implements Serializable {

    private static final long serialVersionUID = 5922818518L;

    public String officeId;
    public String shiftId;
    public String shiftName;
    public String startTime;
    public String endTime;
    public String shiftTime;

    public static Comparator<MiniShiftTime> getComparator() {
        return new Comparator<MiniShiftTime>() {
            @Override
            public int compare(MiniShiftTime o1, MiniShiftTime o2) {
                return o1.getOfficeId().compareTo(o2.getOfficeId());
            }
        };
    }
}
