package citizenstoragemanagercli;

import DAL.MysqlAddressStorage;
import DAL.MysqlEducationStorage;
import DAL.MysqlInsuranceStorage;
import DAL.MysqlPersonStorage;
import address.Address;
import education.Education;
import education.EducationDegree;
import education.GradedEducation;
import education.HigherEducation;
import education.PrimaryEducation;
import education.SecondaryEducation;
import insurance.SocialInsuranceRecord;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import personaldetails.Citizen;
import personaldetails.Gender;

public class CitizenStorageManagerCLI {

    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {

        String line = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        Citizen citizen = null;
        Gender gender = null;
        SocialInsuranceRecord record = null;
        Education education = null;
        Address address = null;

        MysqlPersonStorage person1 = new MysqlPersonStorage();
        person1.truncateTables();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        LocalDate date = null;

        int count = 0;
        int PersonID = 0;
        int AddressID = 0;
        String[] split = null;

        Scanner sc;
        if (args.length > 0) {
            sc = new Scanner(new FileInputStream(args[0]));//read from file
        } else {
            sc = new Scanner(System.in);
        }

        line = sc.nextLine();

        while (line != null) {
            line = sc.nextLine();

            split = line.split(";");
            if (split[0].charAt(0) > 65) {

                if (split[3].equals("M")) {
                    gender = Gender.valueOf("Male");
                } else {
                    gender = Gender.valueOf("Female");
                }

                int height = Integer.parseInt(split[5]);
                date = LocalDate.parse(split[4], formatter);

                citizen = new Citizen(split[0], split[1], split[2], gender, height, date);

                PersonID = person1.insertPerson(citizen);

                MysqlAddressStorage address1 = new MysqlAddressStorage();
                System.out.println(split[0]);
                boolean fullAddress;
                if (split.length < 13 || split[12].equals("")) {
                    address = new Address(split[6], split[7], split[8], split[9], split[10], split[11]);
                    fullAddress = false;
                } else {
                    int floor = Integer.parseInt(split[12].trim());
                    int apt = Integer.parseInt(split[13].trim());
                    fullAddress = true;
                    address = new Address(split[6], split[7], split[8], split[9], split[10], split[11], floor, apt);
                }
                AddressID = address1.insertAddress(address, fullAddress);

                address1.insertRelations(PersonID, AddressID);

                MysqlEducationStorage educationMysql = new MysqlEducationStorage();

                if (split.length > 14) {
                    if (split[14].equals("P")) {
                        Education primary = new PrimaryEducation(split[15], LocalDate.parse(split[16], formatter), LocalDate.parse(split[17], formatter));
                        educationMysql.insertEducation(primary, PersonID);
                    }
                    if (split.length > 18) {
                        Education secondary = new SecondaryEducation(split[19], LocalDate.parse(split[20], formatter), LocalDate.parse(split[21], formatter));
                        //MysqlEducationStorage.insertEducation(secondary, PersonID);
                        if (LocalDate.parse(split[21], formatter).isBefore(LocalDate.now())) {
                            ((GradedEducation) secondary).gotGraduated(Float.parseFloat(split[22]));
                            educationMysql.insertEducation(secondary, PersonID);
                        } else {
                            educationMysql.insertEducation(secondary, PersonID);
                        }

                    }
                    if (split.length > 23) {
                        Education higher = new HigherEducation(split[24], LocalDate.parse(split[25], formatter), LocalDate.parse(split[26], formatter), EducationDegree.valueOf("Bachelor"));
                        if (LocalDate.parse(split[26], formatter).isBefore(LocalDate.now())) {
                            ((GradedEducation) higher).gotGraduated(Float.parseFloat(split[27]));
                            educationMysql.insertEducation(higher, PersonID);
                        } else {
                            educationMysql.insertEducation(higher, PersonID);
                        }
                    }
                }

                if (split.length > 28) {
                    System.out.println("split.length>28");
                }

            } else {
                for (int i = 0; i < split.length; i += 3) {
                    int year = Integer.parseInt(split[i]);
                    int month = Integer.parseInt(split[i + 1]);
                    double amount = Double.parseDouble(split[i + 2]);
                    record = new SocialInsuranceRecord(year, month, amount);
                    MysqlInsuranceStorage insurance1 = new MysqlInsuranceStorage();
                    insurance1.insertInsurance(record, PersonID);
                }
            }
        }
        /*} else {
            Scanner sc = new Scanner(System.in);

            String str = sc.nextLine();
            int n = Integer.parseInt(str);

            for (int i = 0; i < n; i++) {
                System.out.println("Enter person");
                line = sc.nextLine();
                split = line.split(";");

                if (split[3].equals("M")) {
                    gender = Gender.valueOf("Male");
                } else {
                    gender = Gender.valueOf("Female");
                }

                int height = Integer.parseInt(split[5]);
                date = LocalDate.parse(split[4], formatter);

                citizen = new Citizen(split[0], split[1], split[2], gender, height, date);

                PersonID = person1.insertPerson(citizen);

                MysqlAddressStorage address1 = new MysqlAddressStorage();
                //System.out.println(split[0]);
                boolean fullAddress;
                if (split.length < 13 || split[12].equals("")) {
                    address = new Address(split[6], split[7], split[8], split[9], split[10], split[11]);
                    fullAddress = false;
                    AddressID = address1.insertAddress(address, fullAddress);
                } else {
                    int floor = Integer.parseInt(split[12].trim());
                    int apt = Integer.parseInt(split[13].trim());
                    fullAddress = true;
                    address = new Address(split[6], split[7], split[8], split[9], split[10], split[11], floor, apt);
                    AddressID = address1.insertAddress(address, fullAddress);
                }

                address1.insertRelations(PersonID, AddressID);

                MysqlEducationStorage educationMysql = new MysqlEducationStorage();

                if (split.length > 14) {
                    if (split[14].equals("P")) {
                        Education primary = new PrimaryEducation(split[15], LocalDate.parse(split[16], formatter), LocalDate.parse(split[17], formatter));
                        educationMysql.insertEducation(primary, PersonID);
                    }
                    if (split.length > 18) {
                        Education secondary = new SecondaryEducation(split[19], LocalDate.parse(split[20], formatter), LocalDate.parse(split[21], formatter));
                        //MysqlEducationStorage.insertEducation(secondary, PersonID);
                        if (LocalDate.parse(split[21], formatter).isBefore(LocalDate.now())) {
                            ((GradedEducation) secondary).gotGraduated(Float.parseFloat(split[22]));
                            educationMysql.insertEducation(secondary, PersonID);
                        } else {
                            educationMysql.insertEducation(secondary, PersonID);
                        }

                    }
                    if (split.length > 23) {
                        Education higher = new HigherEducation(split[24], LocalDate.parse(split[25], formatter), LocalDate.parse(split[26], formatter), EducationDegree.valueOf("Bachelor"));
                        if (LocalDate.parse(split[26], formatter).isBefore(LocalDate.now())) {
                            ((GradedEducation) higher).gotGraduated(Float.parseFloat(split[27]));
                            educationMysql.insertEducation(higher, PersonID);
                        } else {
                            educationMysql.insertEducation(higher, PersonID);
                        }
                    }
                }

                if (split.length > 28) {
                    System.out.println("split.length>28");
                }
                System.out.println("Enter insurance");

                line = sc.nextLine();
                split = line.split(";");

                for (int j = 0; j < split.length; j += 3) {
                    int year = Integer.parseInt(split[j]);
                    int month = Integer.parseInt(split[j + 1]);
                    double amount = Double.parseDouble(split[j + 2]);
                    record = new SocialInsuranceRecord(year, month, amount);
                    MysqlInsuranceStorage insurance1 = new MysqlInsuranceStorage();
                    insurance1.insertInsurance(record, PersonID);
                }

            }
        }*/

    }
}
