package htcc.common.comparator;

import htcc.common.entity.jpa.Office;
import htcc.common.entity.shift.ShiftArrangementResponse;

import java.util.Comparator;

public class OfficeShiftInfoComparator implements Comparator<ShiftArrangementResponse.OfficeShiftInfo> {
    @Override
    public int compare(ShiftArrangementResponse.OfficeShiftInfo o1, ShiftArrangementResponse.OfficeShiftInfo o2) {
        int score1 = 0;
        int score2 = 0;

        if (o1.getOffice().getIsHeadquarter()) {
            score1 += 2;
        }
        if (!o1.getShiftDetailList().isEmpty()) {
            score1++;
        }

        if (o2.getOffice().getIsHeadquarter()) {
            score2 += 2;
        }
        if (!o2.getShiftDetailList().isEmpty()) {
            score2++;
        }
        return Integer.compare(score2, score1);
    }
}
