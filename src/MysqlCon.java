import java.sql.*;
public class MysqlCon{

        private static String url = "jdbc:mysql://localhost:3306/NOMASDBN";
        private static String driverName = "com.mysql.cj.jdbc.Driver";
        private static String username = "root";
        private static String password = "password";
        private static Connection con;
        public static Connection getConnection(){
                try {
                        Class.forName(driverName);
                        try {
                                con = DriverManager.getConnection(url, username, password);
                        } catch (SQLException ex) {
                                System.out.println("Failed to create the database connection.");
                        }
                } catch (ClassNotFoundException ex) {
                        System.out.println("Driver not found.");
                }
                return con;
        }
        }
