import java.sql.*;
import java.util.List;

public class DataBaseConnection {
    private Connection con;
    private Statement stmt;
    private static final String driverURL= "jdbc:postgresql://localhost:5432/Energy?user=admin&password=admin";
    public void CreateDB() {

        try {
            con = DriverManager.getConnection(driverURL);

            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS public.energy\n" +
                    "(\n" +
                    "    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),\n" +
                    "    coal bigint NOT NULL,\n" +
                    "    country character varying(255) COLLATE pg_catalog.\"default\",\n" +
                    "    date_time timestamp without time zone,\n" +
                    "    gas bigint NOT NULL,\n" +
                    "    hydro bigint NOT NULL,\n" +
                    "    oil bigint NOT NULL,\n" +
                    "    other bigint NOT NULL,\n" +
                    "    solar bigint NOT NULL,\n" +
                    "    wind bigint NOT NULL,\n" +
                    "    CONSTRAINT energy_pkey PRIMARY KEY (id)\n" +
                    ")\n" +
                    "\n" +
                    "TABLESPACE pg_default;\n" +
                    "\n" +
                    "ALTER TABLE public.energy\n" +
                    "    OWNER to admin;";
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void DropDB() {

        try {
            con = DriverManager.getConnection(driverURL);

            stmt = con.createStatement();

            String sql = "Drop table if exists public.energy";

            stmt.executeUpdate(sql);
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countDB() {

        int ret = 0;
        try {
            con = DriverManager.getConnection(driverURL);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM public.energy");
            while ( rs.next() ) {
                ret = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public void addManyDB(List<Energy> listOfEnergy){

        try {
            con = DriverManager.getConnection(driverURL);
            con.setAutoCommit(false);
            stmt = con.createStatement();

            String sql = "";

            for (Energy el : listOfEnergy){
                sql = "INSERT INTO public.energy (country, date_time,coal,gas,oil,hydro,solar,wind,other) "+
                        "VALUES ('"+el.getCountry()+"','"+el.getDateTime()+"',"+el.getCoal()+","+el.getGas()+
                        ","+el.getOil()+","+el.getHydro()+","+el.getSolar()+","+el.getWind()+","+el.getOther()+")";
                stmt.executeUpdate(sql);
            }
//            𓁹‿𓁹

            stmt.close();
            con.commit();
            con.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+" :: "+ e.getMessage() );
            System.exit(0);
        }
    }







}
