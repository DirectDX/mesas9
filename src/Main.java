import java.sql.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
      Connection connection = null;

      try {
          connection = getConnection();
          connection.setAutoCommit(false);
          Odontologo odontologo = null;
          odontologo = new Odontologo("Azulay","Jero","1");

          Statement statement = connection.createStatement();
          // creamos la tabla a partir de la constante

          statement.execute(STS_CREAR_TABLA);

          // Ejecutar la operación INSERT dentro de la transacción con unj PreperedStatement

          PreparedStatement pstmt = connection.prepareStatement(SQL_INSERT);
              pstmt.setString(1, odontologo.getApellido());
              pstmt.setString(2, odontologo.getNombre());
              pstmt.setString(3, odontologo.getMatricula());
              pstmt.executeUpdate();

          // cambiamos la matricula
          PreparedStatement pstmt2 = connection.prepareStatement(SQL_UPDATE_MATRICULA);
              pstmt2.setString(1, "Nueva Matricula"); // Aquí pones la matricula nueva
              pstmt2.setInt(2, 1); // aca pones el usuario que queres por id
              pstmt2.executeUpdate();


          PreparedStatement pstmtConsulta = connection.prepareStatement(SQL_CONSULTA);

              ResultSet rs = pstmtConsulta.executeQuery();

              // Mostrar los resultados de la consulta
              while (rs.next()) {
                  System.out.println("ID: " + rs.getInt("ID"));
                  System.out.println("Nombre: " + rs.getString("NOMBRE"));
                  System.out.println("Apellido: " + rs.getString("APELLIDO"));
                  System.out.println("Matricula: " + rs.getString("MATRICULA"));
              }
              connection.commit();
              connection.setAutoCommit(true);


      } catch (Exception e) {
          try {
              connection.rollback();
              System.out.println("todo mal");

          } catch (Exception e1) {
              e1.printStackTrace();
          }
          e.printStackTrace();
      } finally {
          try {
        connection.close();

          } catch (Exception e) {
              e.printStackTrace();
          }
      }

    }
    private static final String SQL_CONSULTA = "SELECT * FROM ODONTOLOGO";
    private static final String SQL_UPDATE_MATRICULA = "UPDATE ODONTOLOGO SET MATRICULA = ? WHERE ID = ?";
    private static final String SQL_INSERT = "INSERT INTO ODONTOLOGO (NOMBRE, APELLIDO, MATRICULA) VALUES (?,?,?)";
    private static final String STS_CREAR_TABLA = "DROP TABLE IF EXISTS ODONTOLOGO; CREATE TABLE ODONTOLOGO " +
            "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "NOMBRE VARCHAR(50) NOT NULL, " +
            "APELLIDO VARCHAR(50) NOT NULL, " +
            "MATRICULA VARCHAR(50) NOT NULL)";
    private static Connection getConnection() throws ClassNotFoundException, SQLException /* Exception tambien funciona */ {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:./mesas9","sa", "sa");
    }

}