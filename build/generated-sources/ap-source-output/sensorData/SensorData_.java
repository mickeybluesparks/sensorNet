package sensorData;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sensorData.Sensors;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T15:47:59")
@StaticMetamodel(SensorData.class)
public class SensorData_ { 

    public static volatile SingularAttribute<SensorData, Boolean> state1;
    public static volatile SingularAttribute<SensorData, Boolean> state2;
    public static volatile SingularAttribute<SensorData, Date> timeStamp;
    public static volatile SingularAttribute<SensorData, Sensors> sensorsIdsensors;
    public static volatile SingularAttribute<SensorData, Integer> idsensorData;
    public static volatile SingularAttribute<SensorData, Integer> value5;
    public static volatile SingularAttribute<SensorData, Float> value3;
    public static volatile SingularAttribute<SensorData, Integer> value4;
    public static volatile SingularAttribute<SensorData, Float> value1;
    public static volatile SingularAttribute<SensorData, Float> value2;

}