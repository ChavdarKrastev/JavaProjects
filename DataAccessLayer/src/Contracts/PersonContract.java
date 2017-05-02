
package Contracts;

import personaldetails.Citizen;


public interface PersonContract {
    
    Citizen getPersonDetails(int id);
    
    void truncateTables();
    
    int insertPerson(Citizen citizen);
       
}
