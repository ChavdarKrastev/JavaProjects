package education;

import java.time.LocalDate;

public abstract class Education{

    boolean graduated;
    private final LocalDate enrollmentDate;
    LocalDate graduationDate;
    private final String institutionName;

    public abstract EducationDegree getDegree();

    Education(String institution, LocalDate enrollmentDate, LocalDate graduationDate) {
        this.institutionName = institution;
        this.enrollmentDate = enrollmentDate;
        this.graduationDate = graduationDate;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        if (graduationDate == null || graduationDate.isBefore(enrollmentDate)) {
            throw new IllegalArgumentException("Graduation date is expected to be after enrollment date.");
        }
        this.graduationDate = graduationDate;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    void gotGraduated() {
        if (graduationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Graduation date is expected to be a date in the past.");
        }

        graduated = true;
    }
}
