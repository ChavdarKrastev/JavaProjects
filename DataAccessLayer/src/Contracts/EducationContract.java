
package Contracts;

import java.util.ArrayList;

public interface EducationContract {
    
    void insertEducation(education.Education education, int PersonID);
    
    ArrayList<education.Education> getEducation(int id);
    
}
