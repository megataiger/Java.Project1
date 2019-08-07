import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * This program describes all CRUD-operations
 * for working with the database Student-Group-Teacher
 * @version 1.0
 * @author Ilya Nemolyaev
 */


public class Main {

    public static void main(String[] args) {

        try {

            Scanner in = new Scanner(System.in);
            Connection conn = getConnection();

            try {

                Crud[] base = new Crud[8];

                //Add new group in db
                base[0].insertGroup(in, conn);

                //Add new student in db
                base[1].insertStudent(in, conn);

                //Add new teacher in db
                base[2].insertTeacher(in, conn);

                //Change column student
                base[3].updateStudent(in, conn);

                //Create new m-t-m for tables Group and Teacher
                base[4].addGroupTeacher(in, conn);

                //Shows the groups the teacher is teaching
                base[5].selectTeacherGroups(in, conn);

                //Find student you need
                base[6].searchStudent(in, conn);

                //Shows group teachers
                base[6].selectGroupTeachers(in, conn);
            }
            finally {
                in.close();
                conn.close();
            }
        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * This method use property file
     * database.property for
     * connect this database
     * @return Connection with database
     * @throws SQLException if server not found or
     * driver not detected
     * @throws IOException if property file contains
     * incorrect data or missing
     */

    public static Connection getConnection() throws SQLException, IOException {

        Properties props = new Properties();
        FileInputStream in = new FileInputStream("database.properties");
        props.load(in);
        in.close();

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url,username,password);
    }
}