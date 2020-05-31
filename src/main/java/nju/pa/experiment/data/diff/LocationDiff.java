package nju.pa.experiment.data.diff;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Record the diff result of two instances of LineLocations
 *
 * @see LineLocation
 *
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDiff {

    /**
     * Preserve the index of LineLocations
     */
    @JSONField
    private String index;

    /**
     * "diff" fields used to record the diff result, of which formulation is like:
     *
     * [one]-[two]
     */
    @JSONField(ordinal = 1)
    private String diffCovered;

    @JSONField(ordinal = 2)
    private String diffCount;

    @JSONField(ordinal = 3)
    private Boolean isChanged;

}
