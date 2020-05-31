package practice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import nju.pa.experiment.data.coverage.ProjectCoverage;
import nju.pa.experiment.util.CloverParseUtil;
import nju.pa.experiment.util.IOUtil;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-30
 */
public class JsonTest {

    private String cloverPath = "material/clover-example/RBT/clover.xml";

    @Test
    public void testCompare() {
        String jsonPath1 = "material/json/easy-coverage.json";
        String jsonPath2 = "material/json/easy-coverage-changed.json";
        try {
            String jsonStr1 = IOUtil.readAllcontent(jsonPath1);
            String jsonStr2 = IOUtil.readAllcontent(jsonPath2);
            ProjectCoverage coverage1 = JSON.parseObject(jsonStr1, ProjectCoverage.class);
            ProjectCoverage coverage2 = JSON.parseObject(jsonStr2, ProjectCoverage.class);

            ProjectCoverage coverageDiff = new ProjectCoverage();

            Class<ProjectCoverage> projectCoverageClass = ProjectCoverage.class;

            List<Method> getters = Stream.of(projectCoverageClass.getMethods())
                                         .filter((method) -> method.getName().startsWith("get"))
                                         .collect(Collectors.toList());

            for (Method getter : getters) {
                try {
                    Object value = getter.invoke(coverage1);
                    System.out.println(value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testStringToJson() {
        String jsonPath1 = "material/json/easy-coverage.json";
        try {
            String jsonStr1 = IOUtil.readAllcontent(jsonPath1);
//            System.out.println(jsonStr1);
            ProjectCoverage coverage1 = JSON.parseObject(jsonStr1, ProjectCoverage.class);
            System.out.println(coverage1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        ProjectCoverage coverage = CloverParseUtil.parseClover(cloverPath);
        String s = JSON.toJSONString(coverage, SerializerFeature.PrettyFormat);
        System.out.println(s);
    }
}
