import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static int rawDataCount = 0;
    public static void main(String[] args){

        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        System.out.println("Preparing DB");

        dataBaseConnection.DropDB();
        dataBaseConnection.CreateDB();

        System.out.println("ETL begin");

        System.out.println("Current records: "+dataBaseConnection.countDB());


        //List of all files inside EU folder
        String[] pathnames;
        File f = new File("data/EU");
        pathnames = f.list();

        //Europe
        for (String pathname : pathnames) {
            System.out.println("Current File: "+pathname);
            dataBaseConnection.addManyDB(europeETL1H("data/EU/"+pathname));
        }

        //US48
        try {
            System.out.println("Current Region: "+"US48");
            dataBaseConnection.addManyDB(us48ETL("US48"));
            System.out.println("Current Region: "+"California");
            dataBaseConnection.addManyDB(us48ETL("California"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("ETL End");
        System.out.println("Current records: "+dataBaseConnection.countDB());
        System.out.println("Raw records: "+rawDataCount);


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

        //Raw count
        rawDataCount+= records.size();

        String time;
        String country;
        List<Energy> energyList = new ArrayList<>();

        for (int i = 1 ; i < records.size();i++) {
            Energy el = new Energy();
            if (records.get(i).size() == 23){
                //read time
                time = records.get(i).get(1);
                time = time.split("-")[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm ");
                LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
                el.setDateTime(Timestamp.valueOf(dateTime));

                //country name
                country = records.get(i).get(0);
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

    public static List<Energy> us48ETL(String location) throws IOException {
        //lists for every
        ArrayList<String> coal = new ArrayList<>();
        ArrayList<String> hydro = new ArrayList<>();
        ArrayList<String> gas = new ArrayList<>();
        ArrayList<String> nuclear = new ArrayList<>();
        ArrayList<String> other = new ArrayList<>();
        ArrayList<String> oil = new ArrayList<>();
        ArrayList<String> solar = new ArrayList<>();
        ArrayList<String> wind = new ArrayList<>();

        String line;

        BufferedReader br = new BufferedReader(new FileReader("data/"+location+"/coal.csv"));
        while ((line = br.readLine()) != null) {
            coal.add(line);
        }
        br = new BufferedReader(new FileReader("data/"+location+"/gas.csv"));
        while ((line = br.readLine()) != null) {
            gas.add(line);
        }
        br = new BufferedReader(new FileReader("data/"+location+"/hydro.csv"));
        while ((line = br.readLine()) != null) {
            hydro.add(line);
        }
        br = new BufferedReader(new FileReader("data/"+location+"/nuclear.csv"));
        while ((line = br.readLine()) != null) {
            nuclear.add(line);
        }
        br = new BufferedReader(new FileReader("data/"+location+"/oil.csv"));
        while ((line = br.readLine()) != null) {
            oil.add(line);
        }
        br = new BufferedReader(new FileReader("data/"+location+"/other.csv"));
        while ((line = br.readLine()) != null) {
            other.add(line);
        }
        br = new BufferedReader(new FileReader("data/"+location+"/solar.csv"));
        while ((line = br.readLine()) != null) {
            solar.add(line);
        }
        br = new BufferedReader(new FileReader("data/"+location+"/wind.csv"));
        while ((line = br.readLine()) != null) {
            wind.add(line);
        }
//        every file has the same size and there is 8 files in total
        rawDataCount+= coal.size()*8;

        List<Energy> energyList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy HH");
        String time;

        // prepare data in correct order
        for (int i = 0 ; i < wind.size()-5 ; i++){
            Energy el = new Energy();

            //hour and date
            time = coal.get(coal.size()-i-1).split(",")[0];
            time = time.replace("H","");
            el.setDateTime(Timestamp.valueOf(LocalDateTime.parse(time,formatter)));

            //coal, gas, oil, solar, hydro, wind, nuclear, other.
            el.setCoal(Long.parseLong(coal.get(coal.size()-i-1).split(",")[1]));
            el.setGas(Long.parseLong(gas.get(coal.size()-i-1).split(",")[1]));
            el.setOil(Long.parseLong(oil.get(coal.size()-i-1).split(",")[1]));
            el.setSolar(Long.parseLong(solar.get(coal.size()-i-1).split(",")[1]));
            el.setHydro(Long.parseLong(hydro.get(coal.size()-i-1).split(",")[1]));
            el.setWind(Long.parseLong(wind.get(coal.size()-i-1).split(",")[1]));
            el.setNuclear(Long.parseLong(nuclear.get(coal.size()-i-1).split(",")[1]));
            el.setOther(Long.parseLong(other.get(coal.size()-i-1).split(",")[1]));

            el.setCountry(location);
            energyList.add(el);
        }
        return energyList;
    }
}

