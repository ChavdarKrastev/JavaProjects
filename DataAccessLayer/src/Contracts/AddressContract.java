
package Contracts;

import java.util.ArrayList;


public interface AddressContract {
    
   int insertAddress(address.Address address, boolean full);
    
    void insertRelations(int personID, int addressID);
    
    ArrayList<address.Address> getAddress(int id);
    
}
