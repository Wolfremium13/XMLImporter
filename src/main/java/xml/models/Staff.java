package xml.models;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;

@XmlRootElement
public class Staff implements Serializable {
    @XmlAttribute
    public String id;
    @XmlElement
    public String firstname;
    @XmlElement
    public String lastname;
    @XmlElement
    public String nickname;
    @XmlElement
    public Salary salary;

    public Staff() {
        super();
    }
}
