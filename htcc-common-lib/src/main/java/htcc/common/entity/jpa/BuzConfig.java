//package htcc.common.entity.jpa;
//
//import htcc.common.util.StringUtil;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
//import javax.persistence.Entity;
//import javax.persistence.IdClass;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Entity
//@Data
//@IdClass(BuzConfig.Key.class)
//@RequiredArgsConstructor
//@AllArgsConstructor
//public class BuzConfig extends BaseJPAEntity {
//
//    private static final long serialVersionUID = 1926368583005199807L;
//
//    public String companyId = "";
//    public String section   = "";
//    public String key       = "";
//    public String value     = "";
//
//    @Override
//    public String isValid() {
//        return StringUtil.EMPTY;
//    }
//
//    @Data
//    @RequiredArgsConstructor
//    @AllArgsConstructor
//    public static class Key implements Serializable {
//
//        public String companyId;
//        public String section;
//        public String key;
//
//        public Key(String key) {
//            String[] val = key.split("_");
//
//            this.companyId = val[0];
//            this.section = val[1];
//            this.key = val[2];
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) {
//                return true;
//            }
//            if (o == null || getClass() != o.getClass()) {
//                return false;
//            }
//            BuzConfig.Key that = (BuzConfig.Key) o;
//            return (companyId.equalsIgnoreCase(that.companyId) && section.equalsIgnoreCase(that.section) &&
//                    key.equalsIgnoreCase(that.key));
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(companyId, section, key);
//        }
//    }
//}
