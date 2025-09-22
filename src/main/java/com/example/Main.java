package com.example;

import java.sql.*;
import java.util.Scanner;
import org.h2.tools.Server;

public class Main {

    private static final String URL = "jdbc:h2:~/testdb"; // File-based DB (persists on disk)
    private static final String USER = "sa";
    private static final String PASS = "";

    public static void main(String[] args) {
        try {
            // ✅ Start H2 web console on localhost:8082
            Server webServer = Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
            System.out.println("H2 Console started at: http://localhost:8082 (JDBC URL: jdbc:h2:~/testdb)");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                 Scanner scanner = new Scanner(System.in)) {

                Statement st = conn.createStatement();
                st.execute("CREATE TABLE IF NOT EXISTS car (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), price INT)");

                while (true) {
                    System.out.println("\n=== Car CRUD Menu ===");
                    System.out.println("1. Add Car");
                    System.out.println("2. View Cars");
                    System.out.println("3. Update Car");
                    System.out.println("4. Delete Car");
                    System.out.println("5. Exit");
                    System.out.print("Enter choice: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    switch (choice) {
                        case 1 -> addCar(conn, scanner);
                        case 2 -> viewCars(conn);
                        case 3 -> updateCar(conn, scanner);
                        case 4 -> deleteCar(conn, scanner);
                        case 5 -> {
                            System.out.println("Exiting...");
                            webServer.stop(); // ✅ stop console when exiting
                            return;
                        }
                        default -> System.out.println("Invalid choice!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addCar(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter car name: ");
        String name = scanner.nextLine();
        System.out.print("Enter car price: ");
        int price = scanner.nextInt();

        String sql = "INSERT INTO car(name, price) VALUES(?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, price);
            ps.executeUpdate();
            System.out.println("✅ Car added successfully!");
        }
    }

 private static void viewCars(Connection conn) throws SQLException {
    String sql = "SELECT * FROM car";
    try (Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        System.out.println("\n--- Cars in DB ---");
        System.out.printf("%-5s %-20s %-10s%n", "ID", "Name", "Price");
        System.out.println("------------------------------------------------");

        while (rs.next()) {
            System.out.printf("%-5d %-20s %-10d%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("price"));
        }
    }
}


    private static void updateCar(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter car ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new price: ");
        int newPrice = scanner.nextInt();

        String sql = "UPDATE car SET name=?, price=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, newPrice);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Car updated successfully!");
            } else {
                System.out.println("⚠ Car not found!");
            }
        }
    }

    private static void deleteCar(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter car ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM car WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Car deleted successfully!");
            } else {
                System.out.println("⚠ Car not found!");
            }
        }
    }
}
