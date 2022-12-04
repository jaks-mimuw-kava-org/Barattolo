package org.kava.barattolo.spi.util.xml;


import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.kava.barattolo.spi.KavaPersistenceUnitInfo;
import org.kava.lungo.Level;
import org.kava.lungo.Logger;
import org.kava.lungo.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class PersistenceUnitXMLParser {
    private static final Logger logger = LoggerFactory.getLogger(PersistenceUnitXMLParser.class, Level.DEBUG);

    public static PersistenceUnitInfo parse(String filePath, String persistenceUnitName) {
        ParsedXMLPersistenceInfo xmlPersistenceInfo = parseXml(filePath);
        return findPersistenceUnitInfo(xmlPersistenceInfo, persistenceUnitName);
    }

    private static ParsedXMLPersistenceInfo parseXml(String filePath) {
        logger.debug("Starting to parse %s persistence file.", filePath);
        try (FileReader fileReader = new FileReader(filePath)) {
            JAXBContext context = JAXBContext.newInstance(ParsedXMLPersistenceInfo.class);
            return (ParsedXMLPersistenceInfo) context.createUnmarshaller().unmarshal(fileReader);
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static PersistenceUnitInfo findPersistenceUnitInfo(ParsedXMLPersistenceInfo xmlPersistenceInfo,
                                                               String persistenceUnitName) {
        logger.debug("Looking for %s persistence unit in parsed xml.", persistenceUnitName);
        return xmlPersistenceInfo
                .getPersistenceUnits()
                .stream()
                .filter(it -> it.persistenceUnitName.equals(persistenceUnitName))
                .findFirst()
                .map(PersistenceUnitXMLParser::transform)
                .orElse(null);
    }

    private static PersistenceUnitInfo transform(ParsedXMLPersistenceUnitInfo xmlPersistenceUnitInfo) {

        Properties properties = new Properties();
        Map<String, Object> propertyMap = xmlPersistenceUnitInfo
                .getProperties()
                .getProperties()
                .stream()
                .collect(Collectors.toMap(
                        ParsedXMLPersistenceUnitProperty::getName,
                        ParsedXMLPersistenceUnitProperty::getValue
                ));
        properties.putAll(propertyMap);

        return new KavaPersistenceUnitInfo(
                xmlPersistenceUnitInfo.getPersistenceUnitName(),
                xmlPersistenceUnitInfo.getManagedClassNames(),
                properties
        );
    }
}
