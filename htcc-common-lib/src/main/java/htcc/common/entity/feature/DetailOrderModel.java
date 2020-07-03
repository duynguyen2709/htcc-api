package htcc.common.entity.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailOrderModel implements Serializable {

    private static final long serialVersionUID = 130808L;

    private Map<String, Object> supportedFeatures = new HashMap<>();
}
