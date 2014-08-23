package sensorData;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sensorData.Sensors;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-23T18:40:21")
@StaticMetamodel(Locations.class)
public class Locations_ { 

    public static volatile SingularAttribute<Locations, Integer> idlocations;
    public static volatile CollectionAttribute<Locations, Sensors> sensorsCollection;
    public static volatile SingularAttribute<Locations, String> roomName;

}