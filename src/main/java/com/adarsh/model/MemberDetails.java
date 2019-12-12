package com.adarsh.model;

import com.adarsh.generics.model.GenericModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Ram M
 */
@Entity
@Table(name = "member_details")
public class MemberDetails extends GenericModel<Serializable> {

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "mobile_no")
    private Integer mobileNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;

    @Column(name = "reference")
    private String referenceName;

    public MemberDetails(String name, String dateOfBirth, Integer mobileNumber,
            String gender, String address, String referenceName) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.address = address;
        this.referenceName = referenceName;
    }

    public MemberDetails() {
    }

    public String getName() {
        return name;
    }

    public Integer getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

}
