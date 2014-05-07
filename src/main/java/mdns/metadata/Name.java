package mdns.metadata;

import util.CommonUtil;

import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Metadata class of Name type object.
 * @author Shaofeng Chen
 * @since 3/27/14
 */
public class Name extends MetaData {
    private List<Label> labels;

    public Name() {
        labels = new ArrayList<Label>();
    }

    public static Name fromString(String nameString, int componentsNumber) {
        Name name = new Name();
        String[] components = CommonUtil.getComponents(nameString, componentsNumber);
        for (String component : components) {
            name.addLabel(Label.fromString(component));
        }
        return name;
    }

    public  static Name fromString(String nameString) {
        return fromString(nameString, 0);
    }
    @Override
    public String toString() {
        List<String> labelNames = new ArrayList<String>();
        for (Label label : labels) {
            labelNames.add(label.toString());
        }
        return StringUtils.join(labelNames, ".");
    }

    public void addLabel(Label label) {
        labels.add(label);
    }

    public List<Label> getLabels() {
        return labels;
    }

    public int getByteLength() {
        int byteLength = 0;
        for (Label label : labels) {
            byteLength += label.getByteLength();
        }
        return byteLength + 1;
    }

    public byte[] toByteArray() {
        int byteLength = getByteLength();
        ByteBuffer buffer = ByteBuffer.allocate(byteLength);
        for (Label label : labels) {
            buffer.put(label.toByteArray());
        }
        buffer.put((byte)0);
        return buffer.array();
    }
}
