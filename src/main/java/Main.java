import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){

        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        System.out.println("Preparing DB");

        dataBaseConnection.DropDB();
        dataBaseConnection.CreateDB();

        System.out.println("ETL begin");

        System.out.println("Current records: "+dataBaseConnection.countDB());
        //Europe
        dataBaseConnection.addManyDB(europeETL1H("data/Czech_Republic_2017.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Czech_Republic_2018.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Czech_Republic_2019.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Czech_Republic_2020.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Czech_Republic_2021.csv"));

        dataBaseConnection.addManyDB(europeETL1H("data/Greece_2017.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Greece_2018.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Greece_2019.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Greece_2020.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Greece_2021.csv"));

        dataBaseConnection.addManyDB(europeETL1H("data/Italy_2017.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Italy_2018.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Italy_2019.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Italy_2020.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Italy_2021.csv"));

        dataBaseConnection.addManyDB(europeETL1H("data/France_2017.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/France_2018.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/France_2019.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/France_2020.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/France_2021.csv"));

        dataBaseConnection.addManyDB(europeETL1H("data/Poland_2017.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Poland_2018.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Poland_2019.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Poland_2020.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Poland_2021.csv"));

        dataBaseConnection.addManyDB(europeETL1H("data/Portugal_2017.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Portugal_2018.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Portugal_2019.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Portugal_2020.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Portugal_2021.csv"));

        dataBaseConnection.addManyDB(europeETL1H("data/Spain_2017.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Spain_2018.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Spain_2019.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Spain_2020.csv"));
        dataBaseConnection.addManyDB(europeETL1H("data/Spain_2021.csv"));





        System.out.println("Current records: "+dataBaseConnection.countDB());
    }
    public static List<Energy> europeETL1H(String location){
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(location))) {
            String line;
            while ((line = br.readLine()) != null) {
                line=line.replaceAll("\"\"","0");
                line=line.replaceAll("\"","");
                line=line.replaceAll("n/e","0");
                line=line.replaceAll("N/A","0");

                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String time;
        String country;
        List<Energy> energyList = new ArrayList<>();

        for (int i = 1 ; i < records.size();i++) {
            Energy el = new Energy();
            if (records.get(i).size() == 23){
                //read time
                time = records.get(i).get(1);
                //            time = time.split("\"")[1];
                time = time.split("-")[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm ");
                LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
                el.setDateTime(Timestamp.valueOf(dateTime));

                //country name
                country = records.get(i).get(0);
                //            country = country.split("\"")[1];
                country = country.split(" ")[0];
                el.setCountry(country);

                Long coal = 0L, gas = 0L, hydro = 0L, oil = 0L, other = 0L, solar = 0L, wind = 0L, nuclear = 0L;
                other += Long.parseLong(records.get(i).get(2));
                coal += Long.parseLong(records.get(i).get(3));
                gas += Long.parseLong(records.get(i).get(4));
                gas += Long.parseLong(records.get(i).get(5));
                coal += Long.parseLong(records.get(i).get(6));
                oil += Long.parseLong(records.get(i).get(7));
                oil += Long.parseLong(records.get(i).get(8));
                coal += Long.parseLong(records.get(i).get(9));
                other += Long.parseLong(records.get(i).get(10));
                hydro += Long.parseLong(records.get(i).get(13));
                hydro += Long.parseLong(records.get(i).get(14));
                hydro += Long.parseLong(records.get(i).get(15));
                nuclear += Long.parseLong(records.get(i).get(16));
                other += Long.parseLong(records.get(i).get(17));
                other += Long.parseLong(records.get(i).get(18));
                solar += Long.parseLong(records.get(i).get(19));
                other += Long.parseLong(records.get(i).get(20));
                wind += Long.parseLong(records.get(i).get(21));
                wind += Long.parseLong(records.get(i).get(22));

                el.setCoal(coal);
                el.setGas(gas);
                el.setHydro(hydro);
                el.setOil(oil);
                el.setSolar(solar);
                el.setNuclear(nuclear);
                el.setOther(other);
                el.setWind(wind);

                energyList.add(el);
            }
        }


        return energyList;
    }

    public static List<Energy> US(String location){
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(location))) {
            String line;
            while ((line = br.readLine()) != null) {
                line=line.replaceAll("\"\"","0");
                line=line.replaceAll("\"","");
                line=line.replaceAll("n/e","0");
                line=line.replaceAll("N/A","0");

                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String time;
        String country;
        List<Energy> energyList = new ArrayList<>();

        for (int i = 1 ; i < records.size();i++) {
            Energy el = new Energy();
            if (records.get(i).size() == 23){
                //read time
                time = records.get(i).get(1);
                //            time = time.split("\"")[1];
                time = time.split("-")[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm ");
                LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
                el.setDateTime(Timestamp.valueOf(dateTime));

                //country name
                country = records.get(i).get(0);
                //            country = country.split("\"")[1];
                country = country.split(" ")[0];
                el.setCountry(country);

                Long coal = 0L, gas = 0L, hydro = 0L, oil = 0L, other = 0L, solar = 0L, wind = 0L, nuclear = 0L;
                other += Long.parseLong(records.get(i).get(2));
                coal += Long.parseLong(records.get(i).get(3));
                gas += Long.parseLong(records.get(i).get(4));
                gas += Long.parseLong(records.get(i).get(5));
                coal += Long.parseLong(records.get(i).get(6));
                oil += Long.parseLong(records.get(i).get(7));
                oil += Long.parseLong(records.get(i).get(8));
                coal += Long.parseLong(records.get(i).get(9));
                other += Long.parseLong(records.get(i).get(10));
                hydro += Long.parseLong(records.get(i).get(13));
                hydro += Long.parseLong(records.get(i).get(14));
                hydro += Long.parseLong(records.get(i).get(15));
                nuclear += Long.parseLong(records.get(i).get(16));
                other += Long.parseLong(records.get(i).get(17));
                other += Long.parseLong(records.get(i).get(18));
                solar += Long.parseLong(records.get(i).get(19));
                other += Long.parseLong(records.get(i).get(20));
                wind += Long.parseLong(records.get(i).get(21));
                wind += Long.parseLong(records.get(i).get(22));

                el.setCoal(coal);
                el.setGas(gas);
                el.setHydro(hydro);
                el.setOil(oil);
                el.setSolar(solar);
                el.setNuclear(nuclear);
                el.setOther(other);
                el.setWind(wind);

                energyList.add(el);
            }
        }


        return energyList;
    }
}

