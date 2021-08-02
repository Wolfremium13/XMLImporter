package xml.models;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;

@XmlRootElement
public class Salary implements Serializable {
    @XmlAttribute
    public String currency;
    @XmlValue
    public int text;

    public Salary() {
        super();
    }
}
