package org.kava.barattolo.spi.util.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ParsedXMLPersistenceUnitInfo {
    @XmlAttribute(name = "name")
    String persistenceUnitName;
    @XmlElement(name = "class")
    List<String> managedClassNames;
    @XmlElement(name = "properties")
    ParsedXMLPersistenceUnitProperties properties;

    public ParsedXMLPersistenceUnitInfo(String persistenceUnitName, List<String> managedClassNames,
                                        ParsedXMLPersistenceUnitProperties properties) {
        this.persistenceUnitName = persistenceUnitName;
        this.managedClassNames = managedClassNames;
        this.properties = properties;
    }

    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    public void setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    public List<String> getManagedClassNames() {
        return managedClassNames;
    }

    public void setManagedClassNames(List<String> managedClassNames) {
        this.managedClassNames = managedClassNames;
    }

    public ParsedXMLPersistenceUnitProperties getProperties() {
        return properties;
    }

    public void setProperties(ParsedXMLPersistenceUnitProperties properties) {
        this.properties = properties;
    }
}
