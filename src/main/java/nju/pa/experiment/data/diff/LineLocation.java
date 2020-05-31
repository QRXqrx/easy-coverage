package nju.pa.experiment.data.diff;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nju.pa.experiment.data.coverage.LineInfo;

/**
 * LineLocation focus LineInfo. Primary target of designing this is to
 * summarize information to mark a LineInfo uniquely.
 *
 * @see nju.pa.experiment.data.coverage.LineInfo
 *
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LineLocation {

    /**
     * Summarize from project coverage, its formulation is like follows:
     *
     * projectName_packageName_fileName_lineNumber
     *
     */
    private String index;

    private Boolean isCovered;

    private Integer count;

    public LineLocation(String projectName, String packageName, String fileName, LineInfo lineInfo) {
        this.index = generateIndex(
                projectName, packageName, fileName, String.valueOf(lineInfo.getLineNumber()), lineInfo.getType());
        this.isCovered = lineInfo.getIsCovered();
        this.count = lineInfo.getCount();
    }

    private String generateIndex(String...strings) {
        StringBuilder indexBuider = new StringBuilder(100);
        for(int i = 0 ; i < strings.length ; i++) {
            indexBuider.append(strings[i]);
            if(i < strings.length - 1)
                indexBuider.append("_");
        }
        return indexBuider.toString();
    }

    /**
     * Prepare for diff. Two locations who have the same index should be diffed.
     *
     * @param otherLocation Another instance of LineLocation
     * @return The comparison result of index.
     *
     * @see LineLocation
     */
    public boolean sameTo(LineLocation otherLocation) {
        return this.index.equals(otherLocation.index);
    }


}
