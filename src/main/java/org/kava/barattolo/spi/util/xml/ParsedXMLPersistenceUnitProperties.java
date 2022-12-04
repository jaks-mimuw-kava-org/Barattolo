package org.kava.barattolo.spi.util.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ParsedXMLPersistenceUnitProperties {
    @XmlElement(name = "property")
    private List<ParsedXMLPersistenceUnitProperty> properties;

    public ParsedXMLPersistenceUnitProperties(List<ParsedXMLPersistenceUnitProperty> properties) {
        this.properties = properties;
    }

    public List<ParsedXMLPersistenceUnitProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<ParsedXMLPersistenceUnitProperty> properties) {
        this.properties = properties;
    }
}
