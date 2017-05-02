package DAL;

import Contracts.AddressContract;
import address.Address;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlAddressStorage implements AddressContract{

    static final String DBMS_CONN_STRING = "jdbc:mysql://localhost:3306/personcharacteristics?zeroDateTimeBehavior=convertToNull";
    static final String DBMS_USERNAME = "root";
    static final String DBMS_PASSWORD = "123456";

    public ArrayList<Address> getAddress(int id) {
        Address address = null;
        ArrayList<Address> addresses = new ArrayList();

        String sqlquery = "SELECT * FROM personcharacteristics.address "
                + "JOIN personcharacteristics.address_person "
                + "ON personcharacteristics.address.id = personcharacteristics.address_person.addressID "
                + "where personcharacteristics.address_person.personID = ?";

        try (Connection con = DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
                PreparedStatement statement = con.prepareStatement(sqlquery)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {

                    address = new Address(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getInt(9));
                    addresses.add(address);

                }
            } catch (SQLException ex) {

                while (ex != null) {
                    System.out.println(ex.getSQLState());
                    System.out.println(ex.getMessage());
                    System.out.println(ex.getErrorCode());
                    ex = ex.getNextException();
                }

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MysqlAddressStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addresses;
    }

    public int insertAddress(Address address, boolean full) {
        int addressID = 0;
        try (Connection con = DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
                CallableStatement statement = con.prepareCall("{call insert_address(?, ?, ?, ?, ?, ?, ?,?,?)}")) {

            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getMunicipality());
            statement.setString(4, address.getPostalCode());
            statement.setString(5, address.getStreet());
            statement.setString(6, address.getNumber());
            if (full == true) {
                statement.setInt(7, address.getFloor());
                statement.setInt(8, address.getApartmentNo());
            } else {
                statement.setInt(7, 0);
                statement.setInt(8, 0);
            }
            statement.registerOutParameter(9, java.sql.Types.INTEGER);
            statement.executeQuery();

            addressID = statement.getInt("new_id");

        } catch (SQLException ex) {

            while (ex != null) {
                System.out.println(ex.getSQLState());
                System.out.println(ex.getMessage());
                System.out.println(ex.getErrorCode());
                ex = ex.getNextException();
            }
        }
        return addressID;
    }

    public void insertRelations(int personID, int addressID){
        String sqlquery = "INSERT INTO personcharacteristics.address_person "
                + "(personID, addressID)"
                + "VALUES (?,?)";
        try (Connection con = DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
                PreparedStatement statement = con.prepareStatement(sqlquery)) {

            statement.setInt(1, personID);
            statement.setInt(2, addressID);

            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MysqlAddressStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
