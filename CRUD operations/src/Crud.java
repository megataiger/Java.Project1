import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * Class CRUD contains methods for working with
 * the database Student-Group-Teacher
 */

public class Crud {

    public Crud() {
    }

    public static void insertStudent(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nAdd new student");

        String query = "INSERT INTO `student` (`id`, `name`, `birthday`," +
                "`gender`, `group` ) VALUES (NULL, ?, ?, ?, ?);";
        PreparedStatement prstate = conn.prepareStatement(query);

        System.out.print("Enter student name ");
        String name = in.nextLine();
        prstate.setString(1, name);

        System.out.print("Enter birthday in format YYYY-MM-DD ");
        String date = in.next();
        prstate.setString(2, date);

        System.out.print("Enter gender M|W|T ");
        String gender = in.next();
        prstate.setString(3, gender);

        System.out.print("Enter student group ");
        int group = in.nextInt();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT `id` FROM `group` WHERE `number`="
                + group);
        result.next();
        group = result.getInt(1);
        prstate.setInt(4, group);

        prstate.executeUpdate();
        in.nextLine();
    }

    public static void updateStudent(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nChange information about student");

        String query = "UPDATE `student` SET `name`=?, `birthday`=?, `gender`=?," +
                " `group`=? WHERE `student`.`id`=?;";
        PreparedStatement prstate = conn.prepareStatement(query);

        System.out.print("Enter student ID ");
        int idStudent = in.nextInt();
        in.nextLine();

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM `student` WHERE `id`="
                + idStudent);
        result.next();
        prstate.setString(1, result.getString(2));
        prstate.setString(2, result.getString(3));
        prstate.setString(3, result.getString(4));
        prstate.setInt(4, result.getInt(5));
        prstate.setInt(5, idStudent);

        String zero = "zero";
        System.out.print("Enter student name or zero ");
        String name = in.nextLine();
        if (!name.equals(zero))
            prstate.setString(1, name);

        System.out.print("Enter birthday in format YYYY-MM-DD or zero ");
        String date = in.next();
        if (!date.equals(zero))
            prstate.setString(2, date);

        System.out.print("Enter gender M|W|T or zero ");
        String gender = in.next();
        if (!gender.equals(zero))
            prstate.setString(3, gender);

        System.out.print("Enter student group or 0 ");
        int group = in.nextInt();
        if (group > 0)
        {
            result = state.executeQuery("SELECT `id` FROM `group` WHERE `number`="
                    + group);
            result.next();
            group = result.getInt(1);
            prstate.setInt(4, group);
        }

        prstate.executeUpdate();
        in.nextLine();
    }

    public static void deleteStudent(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nDeduct student");

        String query = "DELETE FROM `student` WHERE `student`.`id` = ?;";
        PreparedStatement prstate = conn.prepareStatement(query);

        System.out.print("Enter student ID ");
        int idStudent = in.nextInt();

        prstate.setInt(1, idStudent);

        prstate.executeUpdate();
        in.nextLine();
    }

    public static void selectStudents(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nView students");

        Statement state = conn.createStatement();

        System.out.print("Enter group or 0 ");
        int group = in.nextInt();
        if (group > 0) {


            ResultSet result = state.executeQuery("SELECT `student`.`id`, `name`, `birthday`, " +
                    "`gender`, `number`" +
                    "FROM `student` JOIN `group`" +
                    "ON `student`.`group` = `group`.`id`" +
                    "WHERE `number`=" + group);

            while (result.next()) {

                for (int i = 1; i < 6; i++)
                    System.out.print(result.getString(i) + "\t");
                System.out.print("\n");
            }
        }
        else
        {
            ResultSet result = state.executeQuery("SELECT `student`.`id`, `name`, `birthday`, " +
                    "`gender`, `number`" +
                    "FROM `student` JOIN `group`" +
                    "ON `student`.`group` = `group`.`id`");

            while (result.next()) {

                for (int i = 1; i < 6; i++)
                    System.out.print(result.getString(i) + "\t");
                System.out.print("\n");
            }
        }
        in.nextLine();
    }

    public static void searchStudent(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nSearch student");

        System.out.print("Enter column for search " +
                        "(Example: id, name, birthday, gender, group) ");
        String column = in.nextLine();

        System.out.print("Enter value ");
        String value = in.nextLine();

        String query = "SELECT `student`.`id`, `name`, `birthday`, `gender`, `number`" +
                        "FROM `student` JOIN `group`" +
                        "ON `student`.`group` = `group`.`id` " +
                        "WHERE " + column + "= ?";
        PreparedStatement prstate = conn.prepareStatement(query);

        prstate.setString(1, value);

        ResultSet result = prstate.executeQuery();
        while (result.next())
        {
            for (int i = 1; i <= 5; i++)
                System.out.print(result.getString(i) + "\t");
            System.out.print("\n");
        }
    }

    public static void insertGroup(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nAdd new group");

        String query = "INSERT INTO `group` (`id`, `number`) VALUES (NULL, ?)";
        PreparedStatement prstate = conn.prepareStatement(query);

        System.out.print("Enter group number ");
        int num = in.nextInt();
        prstate.setInt(1, num);

        prstate.executeUpdate();
        in.nextLine();
    }

    public static void updateGroup(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nChange group number");

        System.out.print("Enter group number to update ");
        int num = in.nextInt();

        String query = "UPDATE `group` SET `number`=? WHERE `group`.`number`=" + num;
        PreparedStatement prstate = conn.prepareStatement(query);

        System.out.print("Enter new group number ");
        num = in.nextInt();
        prstate.setInt(1, num);

        prstate.executeUpdate();
        in.nextLine();
    }

    public static void deleteGroup(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nDisband group");

        System.out.print("Enter group number to delete ");
        int num = in.nextInt();

        String query = "SELECT `id` FROM `group` WHERE `number`=" + num;
        Statement state = conn.createStatement();
        ResultSet result =  state.executeQuery(query);
        result.next();
        num = result.getInt(1);

        query = "DELETE FROM `student` WHERE `student`.`group`=" + num;
        state.executeUpdate(query);

        query = "DELETE FROM `group/teacher` WHERE `group/teacher`.`group_id`=" + num;
        state.executeUpdate(query);

        query = "DELETE FROM `group` WHERE `group`.`id`=" + num;
        state.executeUpdate(query);
        in.nextLine();
    }

    public static void selectGroups(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nView all groups");

        String query = "SELECT `number` FROM `group`";
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next())
            System.out.println(result.getInt(1));
        in.nextLine();
    }

    public static void selectGroupTeachers(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nView all teachers the group");

        System.out.print("Enter group number ");
        int num = in.nextInt();

        String query = "SELECT `id` FROM `group` WHERE `number`=" + num;
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);
        result.next();
        num = result.getInt(1);

        query = "SELECT `name` " +
                "FROM `group/teacher` RIGHT JOIN `teacher`" +
                "ON `group/teacher`.`teacher_id`=`teacher`.`id`" +
                "WHERE `group_id`=" + num;
        result = state.executeQuery(query);

        while (result.next())
            System.out.println(result.getString(1));
        in.nextLine();
    }

    public static void insertTeacher(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nAdd new teacher");

        String query = "INSERT INTO `teacher` (`id`, `name`, `birthday`," +
                "`gender`) VALUES (NULL, ?, ?, ?);";
        PreparedStatement prstate = conn.prepareStatement(query);

        System.out.print("Enter teacher name ");
        String name = in.nextLine();
        prstate.setString(1, name);

        System.out.print("Enter birthday in format YYYY-MM-DD ");
        String date = in.next();
        prstate.setString(2, date);

        System.out.print("Enter gender M|W|T ");
        String gender = in.next();
        prstate.setString(3, gender);

        prstate.executeUpdate();
        in.nextLine();
    }

    public static void updateTeacher(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nChange information about teacher");

        String query = "UPDATE `teacher` SET `name`=?, `birthday`=?, `gender`=?" +
                "WHERE `teacher`.`id`=?;";
        PreparedStatement prstate = conn.prepareStatement(query);

        System.out.print("Enter teacher ID ");
        int idTeacher = in.nextInt();
        in.nextLine();

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM `teacher` WHERE `id`="
                + idTeacher);
        result.next();
        prstate.setString(1, result.getString(2));
        prstate.setString(2, result.getString(3));
        prstate.setString(3, result.getString(4));
        prstate.setInt(4, idTeacher);

        String zero = "zero";
        System.out.print("Enter teacher name or zero ");
        String name = in.nextLine();
        if (!name.equals(zero))
            prstate.setString(1, name);

        System.out.print("Enter birthday in format YYYY-MM-DD or zero ");
        String date = in.next();
        if (!date.equals(zero))
            prstate.setString(2, date);

        System.out.print("Enter gender M|W|T or zero ");
        String gender = in.next();
        if (!gender.equals(zero))
            prstate.setString(3, gender);

        prstate.executeUpdate();
        in.nextLine();
    }

    public static void deleteTeacher(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nDismiss teacher");

        Statement state = conn.createStatement();

        System.out.print("Enter teacher ID ");
        int idTeacher = in.nextInt();

        String query = "DELETE FROM `group/teacher` WHERE `group/teacher`.`teacher_id`=" + idTeacher;
        state.executeUpdate(query);

        query = "DELETE FROM `teacher` WHERE `teacher`.`id`=" + idTeacher;
        state.executeUpdate(query);
        in.nextLine();
    }

    public static void selectTeachers(Connection conn) throws SQLException, IOException {

        System.out.println("\nView all teachers");

        Statement state = conn.createStatement();

        ResultSet result = state.executeQuery("SELECT * FROM `teacher`");
        while (result.next()) {

            for (int i = 1; i < 6; i++)
                System.out.print(result.getString(i) + "\t");
            System.out.print("\n");
            }
    }

    public static void selectTeacherGroups(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nView all groups the teacher");

        System.out.print("Enter teacher ID ");
        int idTeacher = in.nextInt();

        String query = "SELECT `number`" +
                "FROM `group` RIGHT JOIN `group/teacher`" +
                "ON `group`.`id`=`group/teacher`.`group_id`" +
                "WHERE `teacher_id`=" + idTeacher;
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next())
            System.out.println(result.getString(1));
        in.nextLine();
    }

    public static void searchTeacher(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nSearch teacher");

        System.out.print("Enter column for search " +
                        "(Example: id, name, birthday, gender) ");
        String column = in.nextLine();

        System.out.print("Enter value ");
        String value = in.nextLine();
        String query = "SELECT * FROM `teacher` WHERE " + column + "= ?";
        PreparedStatement prstate = conn.prepareStatement(query);
        prstate.setString(1, value);

        ResultSet result = prstate.executeQuery();

        while (result.next())
        {
            for (int i = 1; i <= 4; i++)
                System.out.print(result.getString(i) + "\t");
            System.out.print("\n");
        }
        in.nextLine();
    }

    public static void addGroupTeacher(Scanner in, Connection conn) throws SQLException, IOException {

        System.out.println("\nAdd group the teacher");

        System.out.print("Enter teacher ID ");
        int idTeacher = in.nextInt();
        in.nextLine();
        System.out.print("Enter group number ");
        int numGroup = in.nextInt();

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT `id` FROM `group` WHERE `number`=" + numGroup);
        result.next();
        numGroup = result.getInt(1);

        String query = "INSERT INTO `group/teacher` (`group_id`, `teacher_id`) VALUES (?, ?)";
        PreparedStatement prstate = conn.prepareStatement(query);

        prstate.setInt(1, numGroup);
        prstate.setInt(2, idTeacher);

        prstate.executeUpdate();
        in.nextLine();
    }
}