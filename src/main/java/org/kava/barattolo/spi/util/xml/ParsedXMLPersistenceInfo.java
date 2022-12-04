package org.kava.barattolo.spi.util.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "persistence")
public class ParsedXMLPersistenceInfo {
    @XmlElement(name = "persistence-unit")
    private List<ParsedXMLPersistenceUnitInfo> persistenceUnits;

    public ParsedXMLPersistenceInfo(List<ParsedXMLPersistenceUnitInfo> persistenceUnits) {
        this.persistenceUnits = persistenceUnits;
    }

    public List<ParsedXMLPersistenceUnitInfo> getPersistenceUnits() {
        return persistenceUnits;
    }

    public void setPersistenceUnits(List<ParsedXMLPersistenceUnitInfo> persistenceUnits) {
        this.persistenceUnits = persistenceUnits;
    }
}
