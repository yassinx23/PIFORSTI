package com.esprit.Main.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDateBase {
    private static final String URL = "jdbc:mysql://localhost:3306/bdforsti";
    private static final String USER = "root";
    private static final String PWD = "";

    private Connection cnx;
    private static MyDateBase myDataBase;

    private MyDateBase() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connexion établie avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }

    public static MyDateBase getMyDataBase() {
        if (myDataBase == null) {
            myDataBase = new MyDateBase();
        }
        return myDataBase;
    }

    public Connection getCnx() {
        if (cnx == null) {
            System.err.println("La connexion à la base de données est NULL !");
        }
        return cnx;
    }
}
