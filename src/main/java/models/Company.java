package models;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class Company {
    @XmlAttribute
    public String name;

    @XmlElement
    public List<Staff> staff;

    public Company() {
        super();
    }
}
