/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ayush
 */
import java.sql.*;
public class DBQueryExecution {
    public int login(Connection a, String reg, String pass, char b) {
        String table = "STUDENTLOGIN";
        String field = "REGISTERNO";
        if(b == 'F') {
            table = "FACULTYLOGIN";
            field = "EMPLOYEEID";
        }
        try {
            Statement stmt  = a.createStatement();
            String query = "Select PASSWORD from " + table + " where " + field + "=\'" + reg + "\'";
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
                if(rs.getString("PASSWORD").compareTo(pass)==0)
                    return(1);
                else
                    return(2);
            }
            else
                return(0);
        } catch (SQLException ex) {
            System.out.println("Some Error Occured !!");
        }
        return(0);
    }
    public String getDetails(Connection a, String reg, char b) {
        String field = "EMPLOYEEID";
        String table = "FACULTYINFO";
        String name = "";
        if(b == 'S') {
            field = "REGISTERNO";
            table = "STUDENTINFO";
        }
        try {
            Statement stmt  = a.createStatement();
            String query = "Select NAME from " + table + " where " + field + " LIKE '" + Slogin.s + "'";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            name = rs.getString("NAME");
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return(name);
    }
    public ResultSet getDetailsForProfile(Connection a, String reg, char b) {
        String table = "FACULTYINFO";
        String field = "EMPLOYEEID";
        if(b == 'S') {
            field = "REGISTERNO";
            table = "STUDENTINFO";
        }
        try {
            Statement stmt  = a.createStatement();
            String query = "Select * from " + table + " where " + field + "=\'" + reg + "\'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return(rs);
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return(null);
    }
    
    public int insertIntoFaculty_RegisteredCourses(Connection a, String fid, String ccode) {
        String query = "Insert into FACULTY_REGISTEREDCOURSES (COURSE_CODE, FACULTYID, NO_OF_STUDENTS, TOTAL_CLASSES, ASSIGNMENT) values (\'" + ccode + "\', \'" + fid + "\', 0, 0, \'0\')";
        String query2 = "Select COURSE_NAME from COURSES where COURSE_CODE = \'" + ccode + "\'";
        try {
            Statement stmt = a.createStatement();
            int result = stmt.executeUpdate(query);
            if(result >= 0) {
                ResultSet rs = stmt.executeQuery(query2);
                rs.next();
                String query3 = "Insert into STUDENT_AVAILABLECOURSES (COURSE_CODE, COURSE_TITLE) values (\'" + ccode + "\', \'" + rs.getString("COURSE_NAME") + "\')";
                int result2 = stmt.executeUpdate(query3);
                if(result2 >= 0)
                    return(1);               
            }
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return(0);
    }
    
    public ResultSet facultyDetails_Student(Connection a, String ccode) {
        try {
            String query = "Select FI.NAME, FRC.FACULTYID from FACULTYINFO FI,FACULTY_REGISTEREDCOURSES FRC where COURSE_CODE = \'" + ccode + "\' and FI.EMPLOYEEID = FRC.FACULTYID";
            Statement stmt  = a.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
                return(rs);
            else
                return(null);
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return(null);
    }
    
    
    
    
    
    public ResultSet getDetailsAttendance_Faculty(Connection a, String fid) {
        String query = "Select FRC.COURSE_CODE, C.COURSE_NAME from FACULTY_REGISTEREDCOURSES FRC, COURSES C where FRC.FACULTYID = \'" + fid + "\' and FRC.NO_OF_STUDENTS != 0 and FRC.COURSE_CODE = C.COURSE_CODE";
        try {    
            Statement stmt = a.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
                return (rs);
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return(null);
    }
    
    public ResultSet getDetailsClasswise_Faculty(Connection a, String ccode, String fid) {
        String searchClass = ccode + "_" + fid;
        String query = "Select * from " + searchClass;
        try {
            Statement stmt = a.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return(rs);
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return(null);
    }
    
    public int updateAttendance(Connection a, String sc, String reg, boolean x) {
        String ccode = sc.substring(0,sc.indexOf('_'));
        String fid = sc.substring(sc.indexOf('_')+1);
        String query1 = "update " + sc + " set ATTENDED_CLASSES = ATTENDED_CLASSES + 1 where REGISTERNO = \'" + reg + "\'";
        String query2 = "update FACULTY_REGISTEREDCOURSES set TOTAL_CLASSES = TOTAL_CLASSES + 1 where COURSE_CODE = \'" + ccode + "\' and FACULTYID = \'" + fid + "\'";
        int updated1 = 1;
        try {
            Statement stmt = a.createStatement();
            if(!reg.equals(""))
                updated1 = stmt.executeUpdate(query1);
            if(x) {
                int updated2 = stmt.executeUpdate(query2);
            }
            if(updated1 >= 0)
                return(1);
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return 0;
    }
    
    public ResultSet viewCourses_Attendance(Connection a, String r) {
        String query = "Select SRC.COURSE_CODE, C.COURSE_NAME, SRC.FACULTYID from STUDENT_REGISTEREDCOURSES SRC, COURSES C where SRC.COURSE_CODE = C.COURSE_CODE and SRC.REGISTERNO = \'" + r + "\'";
        try {
            Statement stmt = a.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return(rs);
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return(null);
    }
    
    public int[] getDetails_Attendance(Connection a, String sc, String r) {
        int arr[] = new int[2];
        String ccode = sc.substring(0,sc.indexOf('_'));
        String fid = sc.substring(sc.indexOf('_')+1);
        String query1 = "Select ATTENDED_CLASSES from " + sc + " where REGISTERNO = \'" + r + "\'";
        String query2 = "Select TOTAL_CLASSES from FACULTY_REGISTEREDCOURSES where COURSE_CODE = \'" + ccode + "\' and FACULTYID = \'" + fid + "\'";
        try {
            Statement stmt1 = a.createStatement();
            ResultSet rs1 = stmt1.executeQuery(query1);
            rs1.next();
            Statement stmt2 = a.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query2);
            rs2.next();
            arr[0] = rs1.getInt("ATTENDED_CLASSES");
            arr[1] = rs2.getInt("TOTAL_CLASSES");
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return(arr);
    }
    
    public int passwordChange(Connection a, String id, String oldPass, String newPass) {
        String table = "STUDENTLOGIN";
        String field = "REGISTERNO";
        
        String query1 = "Select PASSWORD from " + table + " where " + field + " = \'" + id + "\'";
        String query2 = "Update " + table + " set PASSWORD = \'" + newPass + "\' where " + field + " = \'" + id + "\'";
        try {
            Statement stmt  = a.createStatement();
            ResultSet rs1 = stmt.executeQuery(query1);
            rs1.next();
            if(rs1.getString("PASSWORD").equals(oldPass)) {
                int up = stmt.executeUpdate(query2);
                if(up >= 0)
                    return 1;
            }
            else
                return 2;
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return 0;
    }
    
    
    
    public ResultSet viewMarks(Connection a, String sc, String reg) {
        String ccode = sc.substring(0,sc.indexOf('_'));
        String fid = sc.substring(sc.indexOf('_')+1);
        String query = "Select STATUS, MARKS from " + sc + " where REGISTERNO = \'" + reg + "\'";
        try {
            Statement stmt = a.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return(rs);
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return null;
    }
    
    public int addStudent(Connection a,String reg, String password) {
        String table1 = "STUDENTINFO", table2 = "STUDENTLOGIN", field = "REGISTERNO";
        
       
        String query2 = "insert into " + table2 + " (" + field + ", PASSWORD) values (\'" + reg + "\', \'" + password + "\')";
        try {
            Statement stmt = a.createStatement();
            int update1 = stmt.executeUpdate(query2);
            
                    return 1;
            
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return 0;
    }
    public int addFaculty(Connection a,String id, String pass) {
        
            String table1 = "FACULTYINFO";
            String table2 = "FACULTYLOGIN";
            String field =  "EMPLOYEEID";
        
       
        String query2 = "insert into " + table2 + " (" + field + ", PASSWORD) values (\'" + id + "\', \'" + pass + "\')";
        try {
            Statement stmt = a.createStatement();
            int update1 = stmt.executeUpdate(query2);
            
                    return 1;
            
        } catch (SQLException ex) {
            System.out.println("Some Error occured !!");
        }
        return 0;
    }
    
    

}
