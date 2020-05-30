package nju.pa.experiment.data;

import java.util.Map;

/**
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-05-30
 */
public interface Coverage {

    public void setBCR(Map<String, String> map);

    public void setLCR(Map<String, String> map);

    public void setMCR(Map<String, String> map);
}
