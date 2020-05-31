package nju.pa.experiment.util;

import nju.pa.experiment.data.coverage.FileCoverage;
import nju.pa.experiment.data.coverage.LineInfo;
import nju.pa.experiment.data.coverage.PackageCoverage;
import nju.pa.experiment.data.coverage.ProjectCoverage;
import nju.pa.experiment.data.diff.LineLocation;
import nju.pa.experiment.data.diff.LocationDiff;
import nju.pa.experiment.util.exception.CannotDiffException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This Util provide methods of compare the difference between
 * two Coverage
 *
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-31
 */
public class CoverageDiffUtil {

    private CoverageDiffUtil() {}

    public static List<LocationDiff> diff(List<LineLocation> locationList1, List<LineLocation> locationList2) {
        List<LocationDiff> locationDiffs = new ArrayList<>();
        for (LineLocation location1 : locationList1) {
            locationDiffs.addAll(diff(location1, locationList2));
        }
        return locationDiffs;
    }

    /**
     * The media of diff two list.
     * 1.Only the LineLocations has same location(index) should be diffed.
     * 2.Only the LineLocations hasDifferences should be recorded.
     *
     * @param location Diff source location
     * @param locationList Diff target locations.
     * @return A list of LocationDiffs
     */
    public static List<LocationDiff> diff(LineLocation location, List<LineLocation> locationList) {
        List<LocationDiff> locationDiffs = new ArrayList<>();

        for (LineLocation location2 : locationList) {
            // TODO - maybe need break. since index uniquely mark a LineLocation
            if(location.sameTo(location2)) {
                LocationDiff locationDiff = diff(location, location2);
                if(locationDiff.getIsChanged())
                    locationDiffs.add(locationDiff);
            }

        }
        return locationDiffs;
    }

    /**
     * Compare two instances of LineLocations and output their differences.
     * These two instances should deserve comparision.
     *
     * @param location1 LineLocation instance1
     * @param location2 LineLocation instance2
     * @return An instance of LocationDiff, represents comparison result.
     *
     * @throws CannotDiffException when instances should not be compared.
     */
    public static LocationDiff diff(LineLocation location1, LineLocation location2) {

        if(!location1.sameTo(location2)) {
            String msg = String.format(
                    "Two locations have different indices! Index1=[%s], Index2=[%s]",
                    location1.getIndex(),
                    location2.getIndex()
            );
            throw new CannotDiffException(msg);
        }

        String index = location1.getIndex();
        String diffCovered = String.valueOf(location1.getIsCovered());
        String diffCount = String.valueOf(location1.getCount());
        boolean isChanged = false;

        if(!location1.getIsCovered().equals(location2.getIsCovered()))
            diffCovered = formulateDiff(location1.getIsCovered(), location2.getIsCovered());
        if(!location1.getCount().equals(location2.getCount()))
            diffCount = formulateDiff(location1.getCount(), location2.getCount());
        isChanged = computeIsChanged(location1, location2);

        return new LocationDiff(index, diffCovered, diffCount, isChanged);
    }

    private static boolean computeIsChanged(LineLocation location1, LineLocation location2) {
        return !((location1.getIsCovered().equals(location2.getIsCovered()))
                &&
                (location1.getCount().equals(location2.getCount())));
    }

    private static String formulateDiff(Object one, Object two) {
        return String.format("[%s]-[%s]", one.toString(), two.toString());
    }

    /**
     * Parse all LineLocations from a ProjectCoverage. Prepare for diff.
     *
     * @param coverage An instance of ProjectCoverage
     * @return A list of LineLocations
     *
     * @see ProjectCoverage
     */
    public static List<LineLocation> coverageToLineLocations(ProjectCoverage coverage) {
        List<LineLocation> lineLocations = new ArrayList<>();

        String projectName = coverage.getProjectName();

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

        return lineLocations;
    }
}
