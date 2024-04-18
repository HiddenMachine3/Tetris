package Game.IO;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    
    public static Object[] getNameAndScores(){
        String[] names = new String[0];
        int[] scores = new int[0];

        String url = "jdbc:mysql://localhost:3306/tetris";
        String username = "root";
        String password = "mysql_splash";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            String query = "SELECT name, score FROM scores";
            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<String> namesList = new ArrayList<>();
            ArrayList<Integer> scoresList = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int score = resultSet.getInt("score");
                namesList.add(name);
                scoresList.add(score);
            }

            names = namesList.toArray(new String[0]);
            scores = scoresList.stream().mapToInt(Integer::intValue).toArray();

            resultSet.close();
            statement.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Object[]{names, scores};
    }

    public static void addScore(String name, int score){
        String url = "jdbc:mysql://localhost:3306/tetris";
            String username = "root";
            String password = "mysql_splash";

            try {
                Connection connection = DriverManager.getConnection(url, username, password);

                String query = "INSERT INTO scores (name, score) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setString(1, name);
                statement.setInt(2, score);

                statement.executeUpdate();

                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}