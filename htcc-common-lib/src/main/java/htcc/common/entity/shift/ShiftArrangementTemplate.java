package htcc.common.entity.shift;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.reflect.TypeToken;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @JsonIgnore
    public String data = "";

    @Column
    public String actor = "";

    @Transient
    public Map<Integer, List<MiniShiftTime>> shiftTimeMap = new HashMap<>();

    public void setData(Map<Integer, List<MiniShiftTime>> shiftTimeMap) {
        this.data = StringUtil.toJsonString(shiftTimeMap);
    }

    public Map<Integer, List<MiniShiftTime>> getData() {
        return StringUtil.json2Collection(this.data,
                new TypeToken<Map<Integer, List<MiniShiftTime>>>(){}.getType());
    }

    @Override
    public String isValid() {
        return StringUtil.EMPTY;
    }
}
