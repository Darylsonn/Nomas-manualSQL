package NHL_Class;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Shots {
    @JsonProperty("VAN")
    public int vAN;
    @JsonProperty("BUF")
    public int bUF;
    @JsonProperty("SJS")
    public int sJS;
    @JsonProperty("MTL")
    public int mTL;
    @JsonProperty("FLA")
    public int fLA;
    @JsonProperty("TBL")
    public int tBL;
    @JsonProperty("SEA")
    public int sEA;
    @JsonProperty("NJD")
    public int nJD;
    @JsonProperty("DAL")
    public int dAL;
    @JsonProperty("PIT")
    public int pIT;
    @JsonProperty("COL")
    public int cOL;
    @JsonProperty("WSH")
    public int wSH;
    @JsonProperty("CBJ")
    public int cBJ;
    @JsonProperty("DET")
    public int dET;
    @JsonProperty("NYI")
    public int nYI;
    @JsonProperty("CHI")
    public int cHI;
    @JsonProperty("LAK")
    public int lAK;
    @JsonProperty("NSH")
    public int nSH;
    @JsonProperty("WPG")
    public int wPG;
    @JsonProperty("MIN")
    public int mIN;
    @JsonProperty("ANA")
    public int aNA;
    @JsonProperty("EDM")
    public int eDM;
    @JsonProperty("BOS")
    public int bOS;
    @JsonProperty("PHI")
    public int pHI;
    @JsonProperty("STL")
    public int sTL;
    @JsonProperty("VGK")
    public int vGK;
    @JsonProperty("CAR")
    public int cAR;
    @JsonProperty("OTT")
    public int oTT;
    @JsonProperty("CGY")
    public int cGY;
    @JsonProperty("NYR")
    public int nYR;
    @JsonProperty("ARI")
    public int aRI;
    @JsonProperty("TOR")
    public int tOR;

    public Map<String, Integer> getMap() {
        Map<String, Integer> map = new HashMap<>();
        Field[] fields = Shots.class.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonProperty.class)) {
                continue;
            }
            try {
                JsonProperty a = field.getAnnotation(com.fasterxml.jackson.annotation.JsonProperty.class);
                map.put(a.value(), (Integer) field.get(this));
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        return map;
    }
}
