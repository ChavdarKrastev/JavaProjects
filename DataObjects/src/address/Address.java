package address;

import java.util.Objects;

public class Address {

    private String _country;
    private String _city;
    private String _municipality;
    private String _postalCode;
    private String _street;
    private String _number;
    private Integer _floor;
    private Integer _apartmentNo;

    public Address(String country, String city, String municipality, String postalCode,
            String street, String number, Integer floor, Integer apartmentNo) {

        if (country.isEmpty()) {
            throw new IllegalArgumentException("Country field cannot be empty.");
        }
        if (city.isEmpty()) {
            throw new IllegalArgumentException("City field cannot be empty.");
        }
        if (postalCode.isEmpty()) {
            throw new IllegalArgumentException("Postal code field cannot be empty.");
        }
        if (street.isEmpty()) {
            throw new IllegalArgumentException("Street field cannot be empty.");
        }
        if (number.isEmpty()) {
            throw new IllegalArgumentException("Number field cannot be empty.");
        }
        if (_floor != null && _floor < 0) {
            throw new IllegalArgumentException("Floor field cannot be negative.");
        }
        if (_apartmentNo != null && _apartmentNo <= 0) {
            throw new IllegalArgumentException("Apartment number field must be positive.");
        }

        _country = country;
        _city = city;
        _municipality = municipality;
        _postalCode = postalCode;
        _street = street;
        _number = number;
        _floor = floor;
        _apartmentNo = apartmentNo;
    }

    public Address(String country, String city, String municipality, String postalCode,
            String street, String number) {
        this(country, city, municipality, postalCode, street, number, null, null);
    }

    @Override
    public String toString() {
        String result = String.format("%s %s Street%n", _number, _street);
        if (_floor != null && _apartmentNo != null) {
            result += String.format("fl. %d, ap. %d%n", _floor, _apartmentNo);
        }
        result += String.format("%s %s%n", _postalCode, _municipality);
        result += String.format("%s, %s", _city, _country);

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Address) {
            Address other = (Address) obj;
            if (!this._country.equalsIgnoreCase(other._country)) {
                return false;
            }
            if (!this._city.equalsIgnoreCase(other._city)) {
                return false;
            }
            if (!this._municipality.equalsIgnoreCase(other._municipality)) {
                return false;
            }
            if (!this._postalCode.equalsIgnoreCase(other._postalCode)) {
                return false;
            }
            if (!this._street.equalsIgnoreCase(other._street)) {
                return false;
            }
            if (!this._number.equalsIgnoreCase(other._number)) {
                return false;
            }
            if (!Objects.equals(this._floor, other._floor)) {
                return false;
            }
            if (!Objects.equals(this._apartmentNo, other._apartmentNo)) {
                return false;
            }

            return true;
        }

        return false;
    }

    public String getCountry() {
        return _country;
    }

    public String getCity() {
        return _city;
    }

    public String getMunicipality() {
        return _municipality;
    }

    public String getPostalCode() {
        return _postalCode;
    }

    public String getStreet() {
        return _street;
    }

    public Integer getFloor() {
        return _floor;
    }

    public Integer getApartmentNo() {
        return _apartmentNo;
    }

    public String getNumber() {
        return _number;
    }

}
