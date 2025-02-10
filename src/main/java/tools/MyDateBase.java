package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDateBase {
    public final String URL= "jdbc:mysql://localhost:3306/bdforsti";
    public final String USER="root";
    public final String PWD="";

    private Connection cnx;// 🟢 Déclare la variable cnx ici
    private static MyDateBase myDataBase;

    private MyDateBase() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connexion établie");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static MyDateBase getMyDataBase() {
        if(myDataBase==null)
            myDataBase = new MyDateBase();
        return myDataBase;


    }

    public Connection getCnx() {
        return cnx;
    }
}
