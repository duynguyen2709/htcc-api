package htcc.common.comparator;

import htcc.common.entity.leavingrequest.LeavingRequest;
import htcc.common.util.DateTimeUtil;

import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<LeavingRequest.LeavingDayDetail> {

    @Override
    public int compare(LeavingRequest.LeavingDayDetail o1, LeavingRequest.LeavingDayDetail o2) {
        Date d1 = DateTimeUtil.parseStringToDate(o1.date, "yyyyMMdd");
        Date d2 = DateTimeUtil.parseStringToDate(o2.date, "yyyyMMdd");

        return d1.compareTo(d2);
    }
}
