/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/
 *** <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Admin API
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework License:
 * https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.admin.dto;


/**
 * This class stores the information of an Action entry in the Action table.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */
public class Metric extends DataObject {
    
    private int metricId;
    private long timestamp;

    public Metric() {
    }

    public Metric(int metricId, long timestamp) {
        this.metricId = metricId;
        this.timestamp = timestamp;
    }
    
    public boolean invalidInputs() {
        return false;
    }

    public int getMetricId() {
        return metricId;
    }

    public void setMetricId(int metricId) {
        this.metricId = metricId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    
    
}
