package htcc.employee.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ContactFilterEntity implements Serializable {

    private static final long serialVersionUID = 1926368356905150807L;

    public List<String> departmentList = new ArrayList<>();
    public List<String> officeIdList = new ArrayList<>();
}
