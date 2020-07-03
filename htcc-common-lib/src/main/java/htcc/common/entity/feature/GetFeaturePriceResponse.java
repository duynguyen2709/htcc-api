package htcc.common.entity.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetFeaturePriceResponse implements Serializable {

    private static final long serialVersionUID = 130810L;

    private List<FeaturePrice> featureList = new ArrayList<>();
    private List<FeatureCombo> comboList = new ArrayList<>();
}
