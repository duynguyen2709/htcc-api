package htcc.common.comparator;

import htcc.common.entity.leavingrequest.LeavingRequestResponse;
import htcc.common.util.DateTimeUtil;

import java.util.Comparator;

public class LeavingRequestResponseComparator implements Comparator<LeavingRequestResponse> {

    @Override
    public int compare(LeavingRequestResponse o1, LeavingRequestResponse o2) {
        String yyyyMMdd1 = DateTimeUtil.convertToOtherFormat(o1.getDateFrom(), "yyyy-MM-dd", "yyyyMMdd");
        String yyyyMMdd2 = DateTimeUtil.convertToOtherFormat(o2.getDateFrom(), "yyyy-MM-dd", "yyyyMMdd");

        long dateFrom1 = Long.parseLong(yyyyMMdd1);
        long dateFrom2 = Long.parseLong(yyyyMMdd2);

        return Long.compare(dateFrom1, dateFrom2);
    }
}
