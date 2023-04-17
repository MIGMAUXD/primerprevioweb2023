package co.previo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.previo.modelo.Estudiantes;
import co.previo.util.Conexion;

public class EstudiantesDAO {

private String jdbcURL = "jdbc:postgresql://database-1.ct3gev1bipds.us-east-2.rds.amazonaws.com:5432/testpweb";
	
    private String jdbcUsername = "student";
    
    private String jdbcPassword = "Student22";
    
    private Conexion conexion;
    
    private static final String INSERT_USERS_SQL = "INSERT INTO paciente (documento, nombre, apellido, email, genero, fechanacimiento, direccion, peso, estatura) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT id, documento, nombre, apellido, email, genero, fechanacimiento, direccion, peso, estatura FROM paciente WHERE id =?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM paciente";
    private static final String DELETE_USERS_SQL = "DELETE FROM paciente WHERE id = ?;";
    private static final String UPDATE_USERS_SQL = "UPDATE paciente SET documento = ?, nombre = ?, apellido = ?, email = ?, genero = ?, fechanacimiento = ?, direccion = ?, peso = ?, estatura = ?;";
    
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    
    public void insertUser(Estudiantes user) throws SQLException {
        System.out.println(INSERT_USERS_SQL);

        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getDocumento());
            preparedStatement.setString(2, user.getNombre());
            preparedStatement.setString(3, user.getApellido());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getGenero());
            preparedStatement.setDate(6, (Date) user.getFechanacimiento());
            preparedStatement.setString(7, user.getDireccion());
            preparedStatement.setFloat(8, user.getPeso());
            preparedStatement.setFloat(9, user.getEstatura());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //printSQLException(e);
        }
    }
    
    public Estudiantes selectUser(int id) {
    	Estudiantes user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String documento = rs.getString("documento");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String genero = rs.getString("genero");
                Date fechanacimiento = rs.getDate("fechanacimiento");
                String direccion = rs.getString("direccion");
                Float peso = rs.getFloat("peso");
                Float estatura = rs.getFloat("estatura");
                user = new Estudiantes(id, documento, nombre, apellido, email, genero, fechanacimiento, direccion, direccion, peso, estatura);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List < Estudiantes > selectAllUsers() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Estudiantes > users = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String documento = rs.getString("documento");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String genero = rs.getString("genero");
                Date fechanacimiento = rs.getDate("fechanacimiento");
                String direccion = rs.getString("direccion");
                Float peso = rs.getFloat("peso");
                Float estatura = rs.getFloat("estatura");
                users.add(new Estudiantes(id, documento, nombre, apellido, email, genero, fechanacimiento, direccion, direccion, peso, estatura));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(Estudiantes user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, user.getDocumento());
            statement.setString(2, user.getNombre());
            statement.setString(3, user.getApellido());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getGenero());
            statement.setDate(6, (Date) user.getFechanacimiento());
            statement.setString(7, user.getDireccion());
            statement.setFloat(8, user.getPeso());
            statement.setFloat(9, user.getEstatura());
            statement.setInt(4, user.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
