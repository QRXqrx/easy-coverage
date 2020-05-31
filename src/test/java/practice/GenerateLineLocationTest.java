package practice;

import com.alibaba.fastjson.JSON;
import nju.pa.experiment.data.coverage.FileCoverage;
import nju.pa.experiment.data.coverage.LineInfo;
import nju.pa.experiment.data.coverage.PackageCoverage;
import nju.pa.experiment.data.coverage.ProjectCoverage;
import nju.pa.experiment.data.diff.LineLocation;
import nju.pa.experiment.util.IOUtil;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-31
 */
public class GenerateLineLocationTest {

    @Test
    public void testGenerateLocations() {
        String jsonPath = "material/json/easy-coverage.json";
        try {
            String jsonStr = IOUtil.readAllcontent(jsonPath);
            ProjectCoverage coverage = JSON.parseObject(jsonStr, ProjectCoverage.class);

            List<LineLocation> lineLocations = new ArrayList<>();

            String projectName = coverage.getProjectName();
            System.out.println("projectName: " + projectName);
            List<PackageCoverage> packageCoverages = coverage.getPackageCoverages();

            for (PackageCoverage packageCoverage : packageCoverages) {
                String packageName = packageCoverage.getPackageName();
                List<FileCoverage> fileCoverages = packageCoverage.getFileCoverages();

                for (FileCoverage fileCoverage : fileCoverages) {
                    String fileName = fileCoverage.getFileName();
                    List<LineInfo> lineInfos = fileCoverage.getLineInfos();

                    for (LineInfo lineInfo : lineInfos)
                        lineLocations.add(new LineLocation(projectName, packageName, fileName, lineInfo));
                }
            }

            System.out.println(lineLocations);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
