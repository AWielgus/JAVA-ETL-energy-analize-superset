import java.sql.Timestamp;

public class Energy {

  private long coal;
  private String country;
  private java.sql.Timestamp dateTime;
  private long gas;
  private long hydro;
  private long oil;
  private long other;
  private long solar;
  private long wind;

  private long nuclear;

  public Energy(){
    this.coal = 0;
    this.gas = 0;
    this.oil = 0;
    this.other = 0;
    this.wind = 0;
    this.solar = 0;
    this.hydro = 0;
    this.nuclear =0;
  }

  public Energy( String country, Timestamp dateTime,long coal, long gas, long oil, long hydro, long solar, long wind, long other,long nuclear) {
    this.coal = coal;
    this.country = country;
    this.dateTime = dateTime;
    this.gas = gas;
    this.hydro = hydro;
    this.oil = oil;
    this.other = other;
    this.solar = solar;
    this.wind = wind;
    this.nuclear = nuclear;
  }

  public long getNuclear(){return nuclear;}

  public void setNuclear(long nuclear){this.nuclear=nuclear;}
  public long getCoal() {
    return coal;
  }

  public void setCoal(long coal) {
    this.coal = coal;
  }


  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }


  public java.sql.Timestamp getDateTime() {
    return dateTime;
  }

  public void setDateTime(java.sql.Timestamp dateTime) {
    this.dateTime = dateTime;
  }


  public long getGas() {
    return gas;
  }

  public void setGas(long gas) {
    this.gas = gas;
  }


  public long getHydro() {
    return hydro;
  }

  public void setHydro(long hydro) {
    this.hydro = hydro;
  }


  public long getOil() {
    return oil;
  }

  public void setOil(long oil) {
    this.oil = oil;
  }


  public long getOther() {
    return other;
  }

  public void setOther(long other) {
    this.other = other;
  }


  public long getSolar() {
    return solar;
  }

  public void setSolar(long solar) {
    this.solar = solar;
  }


  public long getWind() {
    return wind;
  }

  public void setWind(long wind) {
    this.wind = wind;
  }

}
