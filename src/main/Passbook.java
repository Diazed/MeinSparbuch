package main;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Passbook {

  @XmlElement
  private Double monthlyIncome = 0d;
  @XmlElement
  private List<Cost> costs = new ArrayList<>();
  @XmlElement
  private String savePath = "";


  @XmlTransient
  public Double getMonthlyIncome() {
    return monthlyIncome;
  }

  public void setMonthlyIncome(Double monthlyIncome) {
    this.monthlyIncome = monthlyIncome;
  }

  @XmlTransient
  public List<Cost> getCosts() {
    return costs;
  }

  public void setCosts(List<Cost> costs) {
    this.costs = costs;
  }

  @XmlTransient
  public String getSavePath() {return savePath;}

  public void setSavePath(String savePath) {this.savePath = savePath;}
}
