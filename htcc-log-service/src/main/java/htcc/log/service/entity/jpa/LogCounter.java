package htcc.log.service.entity.jpa;

import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@IdClass(LogCounter.Key.class)
@RequiredArgsConstructor
@AllArgsConstructor
public class LogCounter extends BaseJPAEntity {

    @Id
    public String logType = "";

    @Id
    public String yyyyMM = "";

    @Id
    public String params = "";

    @Column
    public int count = 0;


    @Override
    public String isValid() {
        return StringUtil.EMPTY;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {

        public String logType;
        public String yyyyMM;
        public String params;

        public Key(String key){
            this.logType = key.split("_")[0];
            this.yyyyMM = key.split("_")[1];
            this.params = key.split("_")[2];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            LogCounter.Key that = (LogCounter.Key) o;
            return (logType.equalsIgnoreCase(that.logType) &&
                    yyyyMM.equalsIgnoreCase(that.yyyyMM) &&
                    params.equalsIgnoreCase(that.params));
        }

        @Override
        public int hashCode() {
            return Objects.hash(logType, yyyyMM, params);
        }
    }
}
