package ru.mycompany;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.h2.tools.DeleteDbFiles;
import ru.mycompany.audio_metadata.Decode_audio;
import ru.mycompany.model.CollectionItem;
import ru.mycompany.model.Collection_tbl;
import ru.mycompany.model.Path_tbl;


import java.io.File;

public class Main {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    //static final String USER = "sa";
    static final String USER = "";
    static final String PASS = "";

    public static void createDB() {
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Method CreateDB");
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE   REGISTRATION " +
                    "(id INTEGER not NULL, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        System.out.println("Goodbye!");
    }

    public static void insertDB() {
        Connection conn = null;
        Statement stmt = null;
        try{
            System.out.println("Method InsertDB");
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected database successfully..." );

            // STEP 3: Execute a query
            stmt = conn.createStatement();
            String sql = "INSERT INTO Registration " + "VALUES (100, 'Zara', 'Ali', 18)";

            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            int index = -1;
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) index = rs.getInt(1);
            System.out.println("Индекс новой записи: " + index);
            sql = "INSERT INTO Registration " + "VALUES (101, 'Mahnaz', 'Fatma', 25)";

            stmt.executeUpdate(sql);
            sql = "INSERT INTO Registration " + "VALUES (102, 'Zaid', 'Khan', 30)";

            stmt.executeUpdate(sql);
            sql = "INSERT INTO Registration " + "VALUES(103, 'Sumit', 'Mittal', 28)";

            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");
    }

    public static void RecordDB () {
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Method RecordDB");

            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
            //String sql = "SELECT id, first, last, age FROM Registration";
            String sql = "SELECT * FROM path";
            ResultSet rs = stmt.executeQuery(sql);

            // STEP 4: Extract data from result set
            while(rs.next()) {
                // Retrieve by column name
                int id  = rs.getInt("id");
                String path = rs.getString("path");
                //int age = rs.getInt("age");
                //String first = rs.getString("first");
                //String last = rs.getString("last");

                // Display values
                System.out.print("ID: " + id);
                System.out.println(", path: " + path);
//                System.out.print(", Age: " + age);
  //              System.out.print(", First: " + first);
    //            System.out.println(", Last: " + last);
            }
            // STEP 5: Clean-up environment
            rs.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");
    }

    public static void updateDB () {
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Method UpdateDB");

            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to a database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
            String sql = "UPDATE Registration " + "SET age = 30 WHERE id in (100, 101)";
            stmt.executeUpdate(sql);

            // Now you can extract all the records
            // to see the updated records
            sql = "SELECT id, first, last, age FROM Registration";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                // Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                // Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            rs.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");
    }

    public static void deleteRecDB () {
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Method deleteRecDB");

            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();
            String sql = "DELETE FROM Registration " + "WHERE id = 101";
            stmt.executeUpdate(sql);

            // Now you can extract all the records
            // to see the remaining records
            sql = "SELECT id, first, last, age FROM Registration";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                // Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                // Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            rs.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");
    }

    public static void ClearTableDB () {
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Method ClearTableDB");

            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();
            String sql = "DELETE FROM Registration";
            stmt.executeUpdate(sql);

            // Now you can extract all the records
            // to see the remaining records
            sql = "SELECT id, first, last, age FROM Registration";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                // Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                // Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            rs.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");

    }

    public static void ShowTablesDB () {
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Method ShowTablesDB");

            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();
            String sql = "SHOW TABLES";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("---");
            while(rs.next()){
                System.out.println("List of Tables\n" +rs.getString("TABLE_NAME"));

            }
            System.out.println("---");
            rs.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");

    }
    CollectionItem intem;
    private static CollectionItem cb_decode_file(File fn){
        Decode_audio decode_audio = new Decode_audio();
        CollectionItem item;
        item = decode_audio.decode_info(fn.getAbsolutePath());
        //item.print();
        return item;
    }
    public static void find_all_files(String bDir, Path_tbl path_tbl, Collection_tbl collectiontbl) {
        File dir1 = new File(bDir);
        CollectionItem citem;
        // если объект представляет каталог
        if (dir1.isDirectory()) {
            long path_ID = path_tbl.Get_id(dir1.getAbsolutePath());
            if (path_ID!=0) {
                // Путь есть в базе
            }
            else {
                path_tbl.Add(dir1.getAbsolutePath());
                // получаем все вложенные объекты в каталоге
                for (File item : dir1.listFiles()) {

                    if (item.isDirectory()) {

                        System.out.println(item.getName() + "  \t folder");
                        //System.out.println(item.getAbsolutePath() + "  \t folder");
                        find_all_files(item.getAbsolutePath(), path_tbl, collectiontbl);

                    } else {
                        if (item.getName().endsWith(".mp3") || item.getName().endsWith(".flac")) {
                            System.out.println(item.getName() + "\t file");
                            String s = item.getAbsolutePath();
                            citem = cb_decode_file(item);
                            //System.out.println(citem.getDuration());
                            if (citem.getFile_name()!="") {
                                collectiontbl.Add(
                                        citem.getTitle(),
                                        citem.getArtists(),
                                        citem.getCompose(),
                                        citem.getGenre(),
                                        citem.getAlbum(),
                                        0/*Integer.getInteger(citem.getDuration())*/,
                                        (int) path_ID,
                                        citem.getFile_name());
                            }
                            //Platform.runLater(() -> {
                            //f_name_lbl.setText("S0");
                            /*
                            int _cnt1 = Collection_tbl.itemsProperty().getValue().size();
                            misic_collection.add(new mus_file(_cnt1, Title, Artists, Composer, Genre,
                                    Album, Duration, item.getParent(), item.getName()));

                             */
                            //});
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
	// write your code here
        System.out.println("HELLOW WORLDS!!");
        System.out.println("***************");
        File tempDirectory = FileUtils.getTempDirectory();
        System.out.println(tempDirectory.getAbsolutePath());

        // delete the H2 database named 'test' in the user home directory
        //DeleteDbFiles.execute("~", "test", true);

        Path_tbl path_tbl = new Path_tbl();
        path_tbl.create_tbl();
        /*
        path_tbl.print_tale();
        //path_tbl.del_all();
        System.out.println("ADD_ID: " + path_tbl.Add("/home/aleks"));
        System.out.println("ADD_ID: " + path_tbl.Add("/home/aleks/Музыка"));
        System.out.println("GET_ID: " + path_tbl.Get_id("/home/aleks"));
        System.out.println("GET_ID: " + path_tbl.Get_id("/home/aleks/Музыка"));
        System.out.println("GET_ID: " + path_tbl.Get_id("/home/aleks/Музыка1"));
        path_tbl.print_tale();
        //path_tbl.del_id(3);
        //path_tbl.del_path("/home/aleks");
        System.out.println("Table path deleted rec ID=3 ");
        System.out.println("Update ID: " + path_tbl.update("/home/aleks/Музыка", "/home/aleks"));
        //update_id(1, "/home/aleks/Музыка9");
        path_tbl.print_tale();
        */

        Collection_tbl collectiontbl = new Collection_tbl();
        collectiontbl.create_tbl();
        /*
        collectiontbl.print_tale();
        System.out.println("ADD_ID: " + collectiontbl.Add("Title_01", "Artist_01", "Composer_01", "Genre_01", "Album_01", 12345, 1,"music.mp3"));
        System.out.println("ADD_ID: " + collectiontbl.Add("Title_02", "Artist_02", "Composer_02", "Genre_02", "Album_02", 98765, 1,"albom.mp3"));
        System.out.println("ADD_ID: " + collectiontbl.Add("Title_03", "Artist_03", "Composer_03", "Genre_03", "Album_03", 55555, 2,"albom1.mp3"));
        collectiontbl.print_tale();
        System.out.println("GET_ID:" + collectiontbl.Get_id(1,"music.mp3"));
        System.out.println("GET_ID:" + collectiontbl.Get_id(1,"albom.mp3"));
        System.out.println("GET_FNAME:" + collectiontbl.Get_fname(2));

        //collectiontbl.del_fname(1,"albom.mp3");
        //collectiontbl.del_path_link(1);
        //collectiontbl.del_all();
        //collectiontbl.del_id(15);
        //collectiontbl.update_id("","NEW TITLE",1);
        //collectiontbl.Get_item(1,"music.mp3").print();

         */
        /*
        ObservableList<CollectionItem> items = collectiontbl.Get_items(3);
        System.out.println("Size collection:" + items.size());
        collectiontbl.print_tale();
        Decode_audio decode_audio = new Decode_audio();
        CollectionItem intem;
        intem = decode_audio.decode_info("/home/aleks/Музыка/Black Sabbath & Ozzy Osbourne - 2015/01 - N.I.B.mp3");
        intem.print();
        intem = decode_audio.decode_info("/home/aleks/Музыка/Mozart - Die Zauberflote [Heilmann, Ziesak, Sumi Jo - Georg Solti, 1990] (1991) (2 CD) - Decca/CD 1/01 - I. Ouverture.flac");
        intem.print();
*/
        find_all_files("/home/aleks/Музыка/", path_tbl, collectiontbl);
        path_tbl.print_tale();
        collectiontbl.print_tale();
/*
        createDB();
        ShowTablesDB();
        ClearTableDB();
        insertDB();
        System.out.println("***************");
        RecordDB();
        System.out.println("***************");
        updateDB();
        System.out.println("***************");
        deleteRecDB();
        */

    }
}
