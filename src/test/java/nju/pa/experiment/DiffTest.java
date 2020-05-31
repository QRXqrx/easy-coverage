package nju.pa.experiment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import nju.pa.experiment.data.coverage.FileCoverage;
import nju.pa.experiment.data.coverage.LineInfo;
import nju.pa.experiment.data.coverage.PackageCoverage;
import nju.pa.experiment.data.coverage.ProjectCoverage;
import nju.pa.experiment.data.diff.LineLocation;
import nju.pa.experiment.data.diff.LocationDiff;
import nju.pa.experiment.util.CoverageDiffUtil;
import nju.pa.experiment.util.IOUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-31
 */
public class DiffTest {

    @Test
    public void testDiffList() {
        String jsonPath1 = "material/json/easy-coverage.json";
        String jsonPath2 = "material/json/easy-coverage-changed.json";
        try {
            String jsonStr1 = IOUtil.readAllcontent(jsonPath1);
            String jsonStr2 = IOUtil.readAllcontent(jsonPath2);
            ProjectCoverage coverage1 = JSON.parseObject(jsonStr1, ProjectCoverage.class);
            ProjectCoverage coverage2 = JSON.parseObject(jsonStr2, ProjectCoverage.class);

            List<LineLocation> lineLocations1 = CoverageDiffUtil.coverageToLineLocations(coverage1);
            List<LineLocation> lineLocations2 = CoverageDiffUtil.coverageToLineLocations(coverage2);

            List<LocationDiff> diffResults = CoverageDiffUtil.diff(lineLocations1, lineLocations2);

            String diff = JSON.toJSONString(diffResults, SerializerFeature.PrettyFormat);
            System.out.println(diff);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDiff() {
        String jsonPath1 = "material/json/easy-coverage.json";
        String jsonPath2 = "material/json/easy-coverage-changed.json";
        try {
            String jsonStr1 = IOUtil.readAllcontent(jsonPath1);
            String jsonStr2 = IOUtil.readAllcontent(jsonPath2);
            ProjectCoverage coverage1 = JSON.parseObject(jsonStr1, ProjectCoverage.class);
            ProjectCoverage coverage2 = JSON.parseObject(jsonStr2, ProjectCoverage.class);

            List<LineLocation> lineLocations1 = CoverageDiffUtil.coverageToLineLocations(coverage1);
            List<LineLocation> lineLocations2 = CoverageDiffUtil.coverageToLineLocations(coverage2);


            LineLocation loc36_1 = lineLocations1.get(1);
            LineLocation loc36_2 = lineLocations2.get(1);

            System.out.println(CoverageDiffUtil.diff(loc36_1, loc36_2));

            LineLocation loc38_1 = lineLocations1.get(7);
            LineLocation loc38_2 = lineLocations2.get(7);

            System.out.println(CoverageDiffUtil.diff(loc38_1, loc38_2));

            LineLocation loc53_1 = lineLocations1.get(11);
            LineLocation loc53_2 = lineLocations2.get(11);

            System.out.println(CoverageDiffUtil.diff(loc53_1, loc53_2));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGenerateLocations() {
        String jsonPath = "material/json/easy-coverage.json";
        try {
            String jsonStr = IOUtil.readAllcontent(jsonPath);
            ProjectCoverage coverage = JSON.parseObject(jsonStr, ProjectCoverage.class);

            System.out.println(CoverageDiffUtil.coverageToLineLocations(coverage));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
