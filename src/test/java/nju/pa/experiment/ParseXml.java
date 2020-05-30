package nju.pa.experiment;

import nju.pa.experiment.data.PackageCoverage;
import nju.pa.experiment.data.ProjectCoverage;
import nju.pa.experiment.util.CloverParseUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-30
 */
public class ParseXml {

    private String cloverPath = "material/clover-example/RBT/clover.xml";
    private File cloverFile = new File(cloverPath);

    @Test
    public void testParsePackage() {
        SAXReader saxReader = new SAXReader();

        try {
            Document doc = saxReader.read(cloverFile);
            Element rootElem = doc.getRootElement();
            Element projectElem = rootElem.element("project");

            Element packageElem = projectElem.element("package");

            System.out.println(CloverParseUtil.parsePackage(packageElem));

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParseFile() {
        SAXReader saxReader = new SAXReader();

        try {
            Document doc = saxReader.read(cloverFile);
            Element rootElem = doc.getRootElement();
            Element projectElem = rootElem.element("project");

            Element fileElem = projectElem.element("package").element("file");

            System.out.println(CloverParseUtil.parseFile(fileElem));

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParseLine() {
        SAXReader saxReader = new SAXReader();

        try {
            Document doc = saxReader.read(cloverFile);
            Element rootElem = doc.getRootElement();
            Element projectElem = rootElem.element("project");

            List elements = projectElem.element("package").element("file").elements("line");

            for(int i = 0 ; i < 4; i++) {
                Object o = elements.get(i);
                if(o instanceof Element) {
                    Element element = (Element) o;
                    System.out.println(CloverParseUtil.parseLine(element));
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testParseClover() {
        System.out.println(CloverParseUtil.parseClover(cloverPath));
    }

    @Test
    public void testFormat() {
        int a = 1;
        int b = 2;
        System.out.println(String.format("%d + %d", a, b));
    }

    @Test
    public void testParseProject() {
        SAXReader saxReader = new SAXReader();

        try {
            Document doc = saxReader.read(cloverFile);
            Element rootElem = doc.getRootElement();
            Element projectElem = rootElem.element("project");


        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
