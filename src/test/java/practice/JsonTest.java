package practice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import nju.pa.experiment.data.ProjectCoverage;
import nju.pa.experiment.util.CloverParseUtil;
import org.junit.Test;

/**
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-30
 */
public class JsonTest {

    private String cloverPath = "material/clover-example/RBT/clover.xml";

    @Test
    public void test() {
        ProjectCoverage coverage = CloverParseUtil.parseClover(cloverPath);
        String s = JSON.toJSONString(coverage, SerializerFeature.PrettyFormat);
        System.out.println(s);
    }
}
