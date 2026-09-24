package co.uk.byjoio.mvc.febe.entity;

import jakarta.persistence.*;

import java.util.Date;

/**
 * Table entity for users profiles stored in MySQL DB
 *
 * @author ojsy-hr
 * @version v1.0.0
 * @since 13-09-2026
 */
@Entity
@Table(name="users_profiles")
public class UserProfile{

    /**
     * The ID (pk) of the user's profile
     */
    @Id
    @Column(name="user_profile_id", nullable=false, unique=true)
    private int userProfileId;

    /**
     * The gender of the user to display on their profile
     */
    @Column(name="gender", length=6, nullable=false)
    private String gender;

    /**
     * The bio the user can set on their profile
     */
    @Column(name="bio", length=255, nullable=true)
    private String bio;

    /**
     * The hobby the user can set on their profile
     */
    @Column(name="hobby", length=64, nullable=true)
    private String hobby;

    /**
     * The age the user can set on their profile
     */
    @Column(name="age", nullable=false)
    private int age;

//    /**
//     * The date of birth the user can set on their profile
//     */
//    @Column(name="date_of_birth", nullable=false)
//    private Date dateOfBirth;

    public UserProfile(){

    }

    /**
     * @param gender of the user
     * @param bio of the user's profile
     * @param hobby of the user
     * @param age of the user
     * @param dateOfBirth of the user
     */
    public UserProfile(int userProfileId, String gender, String bio, String hobby, int age, Date dateOfBirth) {
        this.userProfileId = userProfileId;
        this.gender = gender;
        this.bio = bio;
        this.hobby = hobby;
        this.age = age;
        //this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return ID(pk) of the user profile
     */
    public int getUserProfileId() {
        return userProfileId;
    }

    /**
     * @param userProfileId of the user profile
     */
    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
    }

    /**
     * @return the gender of the user
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender of the user
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return bio of the user
     */
    public String getBio() {
        return bio;
    }

    /**
     * @param bio of the user
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * @return hobby of the user
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby of the user
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * @return age of the user
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age of the user
     */
    public void setAge(int age) {
        this.age = age;
    }

//    /**
//     * @return date of birth of the user
//     */
//    public Date getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    /**
//     * @param dateOfBirth of the user
//     */
//    public void setDateOfBirth(Date dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }

//    /**
//     * @return the user of the profile
//     */
//    public User getUser() {
//        return user;
//    }
//
//    /**
//     * @param user of the profile
//     */
//    public void setUser(User user) {
//        this.user = user;
//    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userProfileId=" + userProfileId +
                ", gender=" + gender +
                ", bio='" + bio + '\'' +
                ", hobby='" + hobby + '\'' +
                ", age=" + age +
                //", dateOfBirth=" + dateOfBirth +
                '}';
    }
}