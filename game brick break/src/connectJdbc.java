import java.sql.*;

public class connectJdbc {
   private Connection conn;
   private Statement stmt;
   
   public connectJdbc() {
    try {
        String DB_URL = "jdbc:mysql://localhost:3307/quanlydiem";
        String USER = "root";
        String PASSWORD = "12345678m";
        conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        stmt = conn.createStatement();
        System.out.println("thanh cong");
    } catch (SQLException e) {
      System.out.println("loi");
        System.err.println("Error" + e.getMessage());
    }
   }

   public void addScores(String name, int score, int level) {
      try {
         String sql = "INSERT INTO scores (player_name, score, levels) VALUES ('" + name + "', " + score + ", '" + level + "')";
         stmt.executeUpdate(sql);
         System.out.println("kết nối");
      } catch (SQLException e) {
         System.err.println("loi add : " + e.getMessage());
      }
   }
   public int getHighestScore() {
    int highestScore = 0;
    try {
       String sql = "SELECT MAX(score) FROM scores";
       ResultSet rs = stmt.executeQuery(sql);
       if (rs.next()) {
          highestScore = rs.getInt(1);
       }
    } catch (SQLException e) {
       System.err.println("Loi lay diem" + e.getMessage());
    }
    return highestScore;
 }

   public void close() {
      try {
         conn.close();
      } catch (Exception e) {
         System.err.println("loi dong: " + e.getMessage());
      }
   }
}
