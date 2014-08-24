package sensorData;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sensorData.Sensors;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T15:47:59")
@StaticMetamodel(Sensortypes.class)
public class Sensortypes_ { 

    public static volatile SingularAttribute<Sensortypes, Integer> idsensorTypes;
    public static volatile CollectionAttribute<Sensortypes, Sensors> sensorsCollection;
    public static volatile SingularAttribute<Sensortypes, String> type;
    public static volatile SingularAttribute<Sensortypes, String> networkprefix;

}