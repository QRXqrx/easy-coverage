package nju.pa.experiment.util;

import nju.pa.experiment.data.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * Provide static methods for parsing clover.xml.
 *
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-30
 */
public class CloverParseUtil {

    private CloverParseUtil() {}

    public static ProjectCoverage parseClover(File cloverFile) {
        ProjectCoverage projectCoverage = new ProjectCoverage();

        SAXReader saxReader = new SAXReader();
        try {
            Document doc = saxReader.read(cloverFile);
            Element projectElem = doc.getRootElement().element("project");

            // Construct a projectCoverage.
            projectCoverage.setProjectName(projectElem.attributeValue("name"));
            projectCoverage.setCloverPath(cloverFile.getAbsolutePath());
            setMetrics(projectElem.element("metrics"), projectCoverage);
            projectCoverage.setPackageCoverages(generatePackageCoverages(projectElem));

        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("Parse clover failed! In parseClover()");
        }

        return projectCoverage;
    }

    public static ProjectCoverage parseClover(String cloverPath) {
        return parseClover(new File(cloverPath));
    }

    public static List<PackageCoverage> generatePackageCoverages(Element projectElem) {
        List<PackageCoverage> packageCoverages = new ArrayList<>();

        Iterator iter = projectElem.elementIterator("package");
        while(iter.hasNext()) {
            Object next = iter.next();
            if(next instanceof Element) {
                Element packageElem = (Element) next;
                packageCoverages.add(parsePackage(packageElem));
            }
        }

        return packageCoverages;
    }


    /**
     * Parse info from a <package> tag, like follow:
     * <package name="net.mooctest">
     *
     * One package contains several files, which should be concerned, too.
     *
     * @param packageElem An element represents <package> tag.
     * @return An instance of PackageCoverage.
     *
     * @see PackageCoverage
     */
    public static PackageCoverage parsePackage(Element packageElem) {
        PackageCoverage packageCoverage = new PackageCoverage();

        packageCoverage.setPackageName(packageElem.attributeValue("name"));
        setMetrics(packageElem.element("metrics"), packageCoverage);
        packageCoverage.setFileCoverages(generateFileCoverages(packageElem));

        return packageCoverage;
    }


    private static List<FileCoverage> generateFileCoverages(Element packageElem) {
        List<FileCoverage> fileCoverages = new ArrayList<>();

        Iterator iter = packageElem.elementIterator("file");
        while(iter.hasNext()) {
            Object next = iter.next();
            if(next instanceof Element) {
                Element fileElem = (Element) next;
                fileCoverages.add(parseFile(fileElem));
            }
        }

        return fileCoverages;
    }


    /**
     * Parse to get info from <file> tag, like follows:
     *
     * <file path="C:\Users\QRX\Desktop\MyWorkplace\Postgraduate\Tasks\task4_atom_test_generation\TrySlicer\AtomTestExperiment\ats_AStarEX\result1528516697964-origin\src\main\java\net\mooctest\AbstractBinaryTree.java" name="AbstractBinaryTree.java">
     *
     * One file contains several lines, which should be concerned, too.
     *
     * @param fileElem An element represents a <file> tag
     * @return An instance of FileCoverage.
     *
     * @see FileCoverage
     */
    public static FileCoverage parseFile(Element fileElem) {
        FileCoverage fileCoverage = new FileCoverage();

        fileCoverage.setFileName(fileElem.attributeValue("name"));
        fileCoverage.setFilePath(fileElem.attributeValue("path"));
        setMetrics(fileElem.element("metrics"), fileCoverage);
        fileCoverage.setLineInfos(generateLineInfos(fileElem));

        return fileCoverage;
    }

    private static List<LineInfo> generateLineInfos(Element fileElem) {
        List<LineInfo> lineInfos = new ArrayList<>();

        Iterator iter = fileElem.elementIterator("line");
        while(iter.hasNext()) {
            Object next = iter.next();
            if(next instanceof Element) {
                Element lineElem = (Element) next;
                lineInfos.addAll(parseLine(lineElem));
            }
        }

        return lineInfos;
    }


    /**
     *
     * Parse to get info from <line> tags. This method should handle three kinds of <line>s, like follows:
     * 1. Branches - <line falsecount="4011" truecount="136623" num="37" type="cond"/>
     * 2. Statement - <line num="38" count="136623" type="stmt"/>
     * 3. Method - <line complexity="5" visibility="public" signature="search(int) : Node" num="35" count="4011" type="method"/>
     *
     * @param lineElem An element represents <line> tag
     * @return List of LineInfo instances,
     *         1.Contains two lineInfos if <line>'s type is "cond", each instance represents a branch.
     *         2.Contains one lineInfo if others.
     * @see LineInfo
     */
    public static List<LineInfo> parseLine(Element lineElem) {
        List<LineInfo> lineInfos = new ArrayList<>();

        String type = lineElem.attributeValue("type");

        if("cond".equals(type)) {
            Integer lineNumber = Integer.parseInt(lineElem.attributeValue("num"));
            Integer falseCnt = Integer.parseInt(lineElem.attributeValue("falsecount"));
            Integer trueCnt = Integer.parseInt(lineElem.attributeValue("truecount"));

            LineInfo falseBranch = new LineInfo(
                    lineNumber, (falseCnt != 0), "branch-false", falseCnt
            );
            LineInfo trueBranch  = new LineInfo(
                    lineNumber, (trueCnt != 0), "branch-true", trueCnt);

            lineInfos.add(falseBranch);
            lineInfos.add(trueBranch);
        } else {
            int lineNumber = Integer.parseInt(lineElem.attributeValue("num"));
            int count = Integer.parseInt(lineElem.attributeValue("count"));
            LineInfo lineInfo = new LineInfo(
                    lineNumber, (count != 0), type, count
            );
            lineInfos.add(lineInfo);
        }

        return lineInfos;
    }


    /**
     * Parse element tag, for example:
     *
     * <metrics coveredelements="498" complexity="131" loc="765" methods="42" classes="5" statements="312" packages="1" coveredconditionals="148" coveredmethods="42" elements="514" ncloc="517" files="3" conditionals="160" coveredstatements="308"/>
     *
     * @param metricsElem An element represents <metrics> tag.
     * @param coverage An instance of coverage.
     *
     * @see Coverage
     */
    public static void setMetrics(Element metricsElem, Coverage coverage) {
        Map<String, String> BCR = buildRMap("conditionals", metricsElem);
        Map<String, String> LCR = buildRMap("statements", metricsElem);
        Map<String, String> MCR = buildRMap("methods", metricsElem);

        coverage.setBCR(BCR);
        coverage.setLCR(LCR);
        coverage.setMCR(MCR);
    }

    /**
     * Compute R map for method setMetrics.
     *
     * @param attr Which attr you want to extract, in "conditionals", "statements" and "methods"
     * @param metricsElem An element represents <metrics> tag.
     * @return A R map (BCR, LCR or MCR)
     */
    private static Map<String, String> buildRMap(String attr, Element metricsElem) {
        // Get data from xml element
        String coveredAttr = "covered" + attr;
        int total = Integer.parseInt(metricsElem.attributeValue(attr));
        int covered = Integer.parseInt(metricsElem.attributeValue(coveredAttr));

        // Build map.
        Map<String, String> map = new HashMap<>();

        //Format ratio and rate
        String ratioStr = String.format("%d/%d", covered, total);
        double rate = ((double) covered / total);
        String rateStr = String.format("%.2f%%", rate*100);

        // Update map
        map.put("ratio", ratioStr);
        map.put("rate", rateStr);

        return map;
    }


}
