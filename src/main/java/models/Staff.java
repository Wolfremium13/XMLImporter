package models;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

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
