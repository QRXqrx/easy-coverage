package nju.pa.experiment.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Clover generation result, one instance of ProjectCoverage represents one clover.xml.
 *
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectCoverage implements Coverage{

    /**
     * Record projectName, gotten from "name" attribute of <project> tag, like follows:
     *
     * <project name="RedBlackTree 0.0.1-SNAPSHOT" timestamp="1590739525374">
     *
     */
    @JSONField
    private String projectName;


    /**
     * Record path of the original clover result.
     */
    @JSONField(ordinal = 1)
    private String cloverPath;

    /**
     * "BCR" for "Branch Coverage Rate & Ratio"
     * {
     *     "Rate" : "x.x%"
     *     "Ratio" : "x/x"
     * }
     */
    @JSONField(ordinal = 2)
    private Map<String, String> BCR;

    /**
     * "LCR" for "Line Coverage Rate & Ratio"
     */
    @JSONField(ordinal = 3)
    private Map<String, String> LCR;

    /**
     * "MCR" for "Method Coverage Rate & Ratio".
     */
    @JSONField(ordinal = 4)
    private Map<String, String> MCR;


    /**
     * One project may contains several packages. Record coverage info for every packages.
     */
    @JSONField(ordinal = 5)
    private List<PackageCoverage> packageCoverages;

}
