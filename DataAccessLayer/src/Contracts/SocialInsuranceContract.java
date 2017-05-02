
package Contracts;

import insurance.SocialInsuranceRecord;
import java.util.ArrayList;


public interface SocialInsuranceContract {
    
    void insertInsurance(SocialInsuranceRecord record, int personID);
    
    ArrayList<SocialInsuranceRecord> getInsurance(int id);
    
    boolean checkSocialInsurance(int personID);
    
}
