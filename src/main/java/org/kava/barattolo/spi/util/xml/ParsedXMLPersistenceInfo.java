package org.kava.barattolo.spi.util.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "persistence", namespace = PersistenceXmlNamespace.VALUE)
@XmlAccessorType(XmlAccessType.FIELD)
public class ParsedXMLPersistenceInfo {
    @XmlElement(name = "persistence-unit", namespace = PersistenceXmlNamespace.VALUE)
    private List<ParsedXMLPersistenceUnitInfo> persistenceUnits;

    public ParsedXMLPersistenceInfo(List<ParsedXMLPersistenceUnitInfo> persistenceUnits) {
        this.persistenceUnits = persistenceUnits;
    }

    public ParsedXMLPersistenceInfo() {
    }

    public List<ParsedXMLPersistenceUnitInfo> getPersistenceUnits() {
        return persistenceUnits;
    }

    public void setPersistenceUnits(List<ParsedXMLPersistenceUnitInfo> persistenceUnits) {
        this.persistenceUnits = persistenceUnits;
    }
}
