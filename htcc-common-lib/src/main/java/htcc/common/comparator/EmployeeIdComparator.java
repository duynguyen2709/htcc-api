package htcc.common.comparator;

import htcc.common.entity.jpa.EmployeeInfo;

import java.util.Comparator;

public class EmployeeIdComparator implements Comparator<EmployeeInfo> {

    @Override
    public int compare(EmployeeInfo o1, EmployeeInfo o2) {
        String id1 = o1.getEmployeeId().trim().substring(o1.getEmployeeId().trim().length() - 5);
        String id2 = o2.getEmployeeId().trim().substring(o2.getEmployeeId().trim().length() - 5);

        int idNum1 = Integer.parseInt(id1);
        int idNum2 = Integer.parseInt(id2);

        return Integer.compare(idNum1, idNum2);
    }
}
