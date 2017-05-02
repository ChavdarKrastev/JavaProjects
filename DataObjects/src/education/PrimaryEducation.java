package education;

import java.time.LocalDate;

public class PrimaryEducation extends Education {

    public PrimaryEducation(String institution, LocalDate enrollmentDate, LocalDate graduationDate) {
        super(institution, enrollmentDate, graduationDate);
    }

    @Override
    public EducationDegree getDegree() {
        return EducationDegree.Primary;
    }

    @Override
    public void gotGraduated() {
        super.gotGraduated();
    }
}
