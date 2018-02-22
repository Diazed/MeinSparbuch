package main.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Cost {

  @XmlElement
  private Integer id;
  @XmlElement
  private boolean paid;
  @XmlElement
  private String name;
  @XmlElement
  private String amount;
  @XmlElement
  private boolean costIsIncome;

  public Cost(){}

  public Cost(Integer id, boolean paid, String name, String amount) {
    this.id = id;
    this.paid = paid;
    this.name = name;
    this.amount = amount;
  }

  @XmlTransient
  public boolean isCostIsIncome() {
    return costIsIncome;
  }

  public void setCostIsIncome(boolean costIsIncome) {
    this.costIsIncome = costIsIncome;
  }

  @XmlTransient
  public Integer getId(){
    return this.id;
  }

  @XmlTransient
  public boolean isPaid() {
    return paid;
  }

  public void setPaid(boolean paid) {
    this.paid = paid;
  }

  @XmlTransient
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @XmlTransient
  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }
}
