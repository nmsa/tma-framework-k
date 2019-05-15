package flume.mycustomsource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.flume.ChannelSelector;
import org.apache.flume.Event;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.event.EventBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class CustomChannelProcessorProxy extends ChannelProcessor {

    public ChannelProcessor m_downstreamChannelProcessor = null;

    public CustomChannelProcessorProxy(ChannelSelector selector) {
        super(selector);
    }

    public CustomChannelProcessorProxy(ChannelProcessor processor) {
        super(null);
        m_downstreamChannelProcessor = processor;
    }

    public Map<String, String> generateHeaders(int probeId, int resourceId, int descriptionId, int time, double value) {
        Map<String, String> headers = new HashMap<String, String>();
        String probestring = Integer.toString(probeId);
        headers.put("probe", probestring);
        String resourcestring = Integer.toString(resourceId);
        headers.put("resource", resourcestring);
        String descriptionstring = Integer.toString(descriptionId);
        headers.put("description", descriptionstring);
        String timestring = Integer.toString(time);
        headers.put("time", timestring);
        String valuestring = Double.toString(value);
        headers.put("value", valuestring);
        return headers;
    }

    public List<Event> parseEvents(List<Event> evts) {
        List<Event> listEvents = new ArrayList<Event>();

        Connection c = null;

        PreparedStatement ps = c.prepareStatement("INSERT INTO Data(probeId, descriptionId, resourceId, valueTime, value) "
                + " VALUES (? , ? , ?, ?, ?)");

        for (Event event : evts) {
            byte[] eventBody = event.getBody();
            JSONObject input = new JSONObject(new String(eventBody));
            JSONArray data = input.getJSONArray("data");
            int probeId = input.getInt("probeId");
            int resourceId = input.getInt("resourceId");
            for (int i = 0; i < data.length(); i++) {
                JSONArray observations = data.getJSONObject(i).getJSONArray("observations");
                for (int j = 0; j < observations.length(); j++) {
                    int descriptionId = data.getJSONObject(i).getInt("descriptionId");
                    int time = observations.getJSONObject(j).getInt("time");
                    Double value = observations.getJSONObject(j).getDouble("value");

                    ps.setInt(1, probeId);
                    ps.setInt(2, resourceId);
                    ps.setInt(3, descriptionId);
                    ps.setInt(4, time);
                    ps.setDouble(5, value);

                    int result = ps.executeUpdate();
                    if (result <= 0) {
                        System.out.println("ERROR = " + ps);
                    }
                }
            }
        }
        return listEvents;
    }

    @Override
    public void processEventBatch(List<Event> events) {
        List<Event> generatedEvents = parseEvents(events);
        m_downstreamChannelProcessor.processEventBatch(generatedEvents);
    }
}
