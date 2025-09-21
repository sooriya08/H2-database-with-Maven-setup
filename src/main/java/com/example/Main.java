/*
 * @Author: sooriyaprakash sooriya.prakash@bluescopetech.com
 * @Date: 2025-09-20 11:48:00
 * @LastEditors: sooriyaprakash sooriya.prakash@bluescopetech.com
 * @LastEditTime: 2025-09-21 22:22:34
 * @FilePath: \demo\src\main\java\com\example\Main.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.example;

import java.sql.*;
import org.h2.tools.Server;

public class Main {
  public static void main(String[] args) throws Exception {

    // Optional: start H2 web console at http://localhost:8082

     Server web = Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
        System.out.println("H2 web console started at: http://localhost:8082 (user=sa, no password)");

            // Connection URL examples:
        // In-memory DB: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
        // File DB (persist to user's home): jdbc:h2:~/testdb
        String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        String user = "sa";
        String pass = "";

        try( Connection conn = DriverManager.getConnection(url, user, pass)) {
            Statement st = conn.createStatement();
             st.execute("CREATE TABLE IF NOT EXISTS car (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), price INT)");
            st.executeUpdate("INSERT INTO car(name, price) VALUES('Honda City', 1200000), ('Hyundai Creta', 1100000),('Thar', 3200000)");

            ResultSet rs = st.executeQuery("SELECT id, name, price FROM car");
            System.out.println("Cars in DB:");

            while (rs.next()) {
                System.out.printf("  %d: %s -> %d%n", rs.getInt("id"), rs.getString("name"), rs.getInt("price"));
                
            } 
        } finally {
            // keep console open until user stops app; you can comment this stop call if you want long-running app
         //   web.stop();
        }
    
  }
}