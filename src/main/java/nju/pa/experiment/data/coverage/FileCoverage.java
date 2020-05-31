package nju.pa.experiment.data.coverage;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Record parsing result for <file> tags
 *
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileCoverage implements Coverage{

    /**
     * Java file name.
     */
    @JSONField
    private String fileName;

    /**
     * File path
     */
    @JSONField(ordinal = 1)
    private String filePath;

    /**
     * "BCR" for "Branch Coverage Rate & Ratio"
     */
    @JSONField(ordinal = 2)
    private Map<String, String> BCR;

    /**
     * "LCR" for "Line Coverage Rate & Ratio""
     */
    @JSONField(ordinal = 3)
    private Map<String, String> LCR;

    /**
     * "MCR" for "Method Coverage Rate & Ratio""
     */
    @JSONField(ordinal = 4)
    private Map<String, String> MCR;

    /**
     * One <file> contains several <line>
     */
    @JSONField(ordinal = 5)
    private List<LineInfo> lineInfos;

}
