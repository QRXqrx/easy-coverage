package nju.pa.experiment.util;

import java.io.*;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-25
 */
public class IOUtil {

    private IOUtil() {}

    public static final String JAVA_SUFFIX = ".java";
    public static final String CLASS_SUFFIX = ".class";
    public static final String TXT_SUFFIX = ".txt";
    public static final String NEW_LINE = System.lineSeparator();

    /**
     * Read all content from a readable file.
     *
     * @param file is a readable file.
     * @param charset charset of the file.
     * @return content of the file.
     * @throws IOException if read wrongly.
     *
     * @date 2020-03-18
     */
    public static String readAllcontent(File file, String charset) throws IOException {
        if(!file.isFile()) {
            throw new IllegalArgumentException("Invalid file. Please input a path of file.");
        }
        if(!file.canRead()) {
            throw new IllegalArgumentException(file.getAbsolutePath() + ": cannot be read");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
        StringBuilder contentBuilder = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            contentBuilder.append(line).append(NEW_LINE);
        }
        br.close();
        return contentBuilder.toString();
    }

    /**
     * Read all content from a readable file. Use default charset: UTF-8.
     *
     * @param file is a readable file.
     * @return content of the file.
     * @throws IOException if read wrongly.
     *
     * @date 2020-03-18
     */
    public static String readAllcontent(File file) throws IOException {
        return readAllcontent(file, "UTF-8");
    }

    public static String readAllcontent(String path) throws IOException {
        if(path == null) {
            throw new IllegalArgumentException("Path should not be null.");
        }
        return readAllcontent(new File(path));
    }


    /**
     * Read content from a txt file, one line for one item.
     *
     * @param file A readable file.
     * @return A List of parsing result.
     *
     * @date 2020-03-18
     */
    public static List<String> readContentsLineByLine(File file) throws IOException {
        if(!file.isFile()) {
            throw new IllegalArgumentException("Invalid file. Please input a path of file.");
        }
        if(!file.canRead()) {
            throw new IllegalArgumentException(file.getAbsolutePath() + ": cannot be read");
        }

        List<String> contents = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while((line = br.readLine()) != null) {
            contents.add(line);
        }

        br.close();
        return contents;
    }
    /**
     * Read content from a txt file, one line for one item.
     *
     * @param path A path of a property file, written in a txt file.
     * @return A List of parsing result.
     *
     * @date 2020-03-18
     */
    public static List<String> readContentsLineByLine(String path) throws IOException {
        if(path == null) {
            throw new IllegalArgumentException("Path should not be null.");
        }
        File file = new File(path);
        return readContentsLineByLine(file);
    }

    public static String suffixOf(String filePath) {
        return suffixOf(new File(filePath));
    }

    public static String suffixOf(File file) {
        if(file.isDirectory()) {
            return "";
        }
        String fileName = file.getName();
        int loc = fileName.lastIndexOf('.');
        return fileName.substring(loc);
    }

    public static String simpleName(String filePath) {
        return simpleName(new File(filePath));
    }

    public static String simpleName(File file) {
        if(file.isDirectory()) {
            return file.getName();
        }
        return file.getName().replace(suffixOf(file), "");
    }

    public static List<File> listFilesOrEmpty(String dirPath) throws NotDirectoryException {
        return listFilesOrEmpty(new File(dirPath));
    }

    public static List<File> listFilesOrEmpty(File dir) throws NotDirectoryException {
        if(!dir.isDirectory())
            throw new NotDirectoryException(dir.getAbsolutePath());

        File[] files = dir.listFiles();
        if(files == null)
            return new ArrayList<>();
        return Arrays.asList(files);
    }

}
