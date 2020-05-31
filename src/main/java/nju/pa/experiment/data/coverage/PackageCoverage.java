package nju.pa.experiment.data.coverage;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Record parsing result for <package> tags
 *
 * <package name="net.mooctest">
 *
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageCoverage implements Coverage{

    /**
     * According to "name" attribute of <package> tag
     */
    @JSONField
    private String packageName;

    /**
     * "BCR" for "Branch Coverage Rate & Ratio"
     */
    @JSONField(ordinal = 1)
    private Map<String, String> BCR;

    /**
     * "LCR" for "Line Coverage Rate & Ratio""
     */
    @JSONField(ordinal = 2)
    private Map<String, String> LCR;

    /**
     * "MCR" for "Method Coverage Rate & Ratio""
     */
    @JSONField(ordinal = 3)
    private Map<String, String> MCR;


    /**
     * One package comprises several java files.
     */
    @JSONField(ordinal = 4)
    private List<FileCoverage> fileCoverages;


}
