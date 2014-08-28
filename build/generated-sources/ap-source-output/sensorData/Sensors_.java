package sensorData;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sensorData.Locations;
import sensorData.SensorData;
import sensorData.Sensortypes;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-25T11:07:12")
@StaticMetamodel(Sensors.class)
public class Sensors_ { 

    public static volatile CollectionAttribute<Sensors, SensorData> sensorDataCollection;
    public static volatile SingularAttribute<Sensors, String> networkId;
    public static volatile SingularAttribute<Sensors, Locations> locationsIdlocations;
    public static volatile SingularAttribute<Sensors, Boolean> active;
    public static volatile SingularAttribute<Sensors, Sensortypes> sensorTypesidsensorTypes;
    public static volatile SingularAttribute<Sensors, Integer> idsensors;

}