package NHL_Class; 
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PowerPlay{
    @JsonProperty("VAN") 
    public VAN vAN;
    @JsonProperty("BUF") 
    public BUF bUF;
    @JsonProperty("SJS") 
    public SJS sJS;
    @JsonProperty("MTL") 
    public MTL mTL;
    @JsonProperty("FLA") 
    public FLA fLA;
    @JsonProperty("TBL") 
    public TBL tBL;
    @JsonProperty("SEA") 
    public SEA sEA;
    @JsonProperty("NJD") 
    public NJD nJD;
    @JsonProperty("DAL") 
    public DAL dAL;
    @JsonProperty("PIT") 
    public PIT pIT;
    @JsonProperty("COL") 
    public COL cOL;
    @JsonProperty("WSH") 
    public WSH wSH;
    @JsonProperty("CBJ") 
    public CBJ cBJ;
    @JsonProperty("DET") 
    public DET dET;
    @JsonProperty("NYI") 
    public NYI nYI;
    @JsonProperty("CHI") 
    public CHI cHI;
    @JsonProperty("LAK") 
    public LAK lAK;
    @JsonProperty("NSH") 
    public NSH nSH;
    @JsonProperty("WPG") 
    public WPG wPG;
    @JsonProperty("MIN") 
    public MIN mIN;
    @JsonProperty("ANA") 
    public ANA aNA;
    @JsonProperty("EDM") 
    public EDM eDM;
    @JsonProperty("BOS") 
    public BOS bOS;
    @JsonProperty("PHI") 
    public PHI pHI;
    @JsonProperty("STL") 
    public STL sTL;
    @JsonProperty("VGK") 
    public VGK vGK;
    @JsonProperty("CAR") 
    public CAR cAR;
    @JsonProperty("OTT") 
    public OTT oTT;
    @JsonProperty("CGY") 
    public CGY cGY;
    @JsonProperty("NYR") 
    public NYR nYR;
    @JsonProperty("ARI") 
    public ARI aRI;
    @JsonProperty("TOR") 
    public TOR tOR;

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = PowerPlay.class.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonProperty.class)) {
                continue;
            }
            try {
                JsonProperty a = field.getAnnotation(com.fasterxml.jackson.annotation.JsonProperty.class);
                map.put(a.value(), (Object) field.get(this));
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        return map;
    }
}
