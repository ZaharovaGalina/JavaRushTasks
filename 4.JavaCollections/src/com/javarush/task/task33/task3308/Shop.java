package com.javarush.task.task33.task3308;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "shop")
@XmlRootElement
public class Shop {
    public Goods goods;
    @XmlElement(name = "count")
    public int count;
    @XmlElement(name = "profit")
    public double profit;
    @XmlElement(name="secretData")
    public String[] secretData;

    @XmlType(name = "goods")
    @XmlRootElement
    public static class Goods {
        @XmlElementWrapper
        @XmlElement(name="names")
        List<String> names;
    }
}
