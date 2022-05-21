import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        System.out.println("Preparing DB");

        dataBaseConnection.DropDB();
        dataBaseConnection.CreateDB();

        System.out.println("ETL begin");
        System.out.println("Current records: "+dataBaseConnection.countDB());

//        List<Energy> test = new ArrayList<>();
//        test.add(new Energy("Poland", Timestamp.valueOf(LocalDateTime.now()),0,0,0,0,0,0,0));
//        dataBaseConnection.addManyDB(test);
//
//
//        System.out.println(dataBaseConnection.countDB());

    }
    public void europeETL1H(){
        
    }


}

