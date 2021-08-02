package xml.models;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement
public class Company{

    @XmlElement
    public List<Staff> staff;

    public Company() {
        super();
    }
}
