package org.kava.barattolo.spi.util.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ParsedXMLPersistenceUnitInfo {
    @XmlAttribute(name = "name")
    String persistenceUnitName;
    @XmlElement(name = "class", namespace = PersistenceXmlNamespace.VALUE)
    List<String> managedClassNames;
    @XmlElementWrapper(name = "properties", namespace = PersistenceXmlNamespace.VALUE)
    @XmlElement(name = "property", namespace = PersistenceXmlNamespace.VALUE)
    List<ParsedXMLPersistenceUnitProperty> properties;

    public ParsedXMLPersistenceUnitInfo(String persistenceUnitName, List<String> managedClassNames,
                                        List<ParsedXMLPersistenceUnitProperty> properties) {
        this.persistenceUnitName = persistenceUnitName;
        this.managedClassNames = managedClassNames;
        this.properties = properties;
    }

    public ParsedXMLPersistenceUnitInfo() {
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

    public List<ParsedXMLPersistenceUnitProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<ParsedXMLPersistenceUnitProperty> properties) {
        this.properties = properties;
    }
}
