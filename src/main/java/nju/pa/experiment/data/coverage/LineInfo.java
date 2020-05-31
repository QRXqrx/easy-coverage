package nju.pa.experiment.data.coverage;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Record parsing result for each <line> tag.
 *
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LineInfo {

    /**
     * Line number of source code.
     */
    @JSONField
    private Integer lineNumber;

    /**
     * Whether this line is covered.
     */
    @JSONField(ordinal = 1)
    private Boolean isCovered;

    /**
     * "branch", "statement", "method"
     */
    @JSONField(ordinal = 2)
    private String type;

    /**
     * The number of times of reaching.
     */
    @JSONField(ordinal = 3)
    private Integer count;

}
