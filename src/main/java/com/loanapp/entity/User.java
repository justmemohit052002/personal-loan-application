package com.loanapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import com.loanapp.enums.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ fixed
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String password;

    // ✅ ENUM instead of String
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Integer age;

    private String gender;

    private LocalDate dateOfBirth;

    private String address;

    private String city;

    private String state;

    private String pincode;

    @Column(unique = true)
    private String panNumber;

    @Column(unique = true)
    private String aadhaarNumber;

    private String bankAccount;

    private String ifscCode;

    private Double monthlyIncome;

    private String employmentType;

    private String companyName;

    @Builder.Default
    private Boolean isDefaulted = false;

    @Builder.Default
    private Boolean isDeleted = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ✅ Auto timestamps
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public Double getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(Double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Boolean getIsDefaulted() {
		return isDefaulted;
	}

	public void setIsDefaulted(Boolean isDefaulted) {
		this.isDefaulted = isDefaulted;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User(Long id, String fullName, String email, String mobileNumber, String password, Role role, Integer age,
			String gender, LocalDate dateOfBirth, String address, String city, String state, String pincode,
			String panNumber, String aadhaarNumber, String bankAccount, String ifscCode, Double monthlyIncome,
			String employmentType, String companyName, Boolean isDefaulted, Boolean isDeleted, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.role = role;
		this.age = age;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.panNumber = panNumber;
		this.aadhaarNumber = aadhaarNumber;
		this.bankAccount = bankAccount;
		this.ifscCode = ifscCode;
		this.monthlyIncome = monthlyIncome;
		this.employmentType = employmentType;
		this.companyName = companyName;
		this.isDefaulted = isDefaulted;
		this.isDeleted = isDeleted;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", email=" + email + ", mobileNumber=" + mobileNumber
				+ ", password=" + password + ", role=" + role + ", age=" + age + ", gender=" + gender + ", dateOfBirth="
				+ dateOfBirth + ", address=" + address + ", city=" + city + ", state=" + state + ", pincode=" + pincode
				+ ", panNumber=" + panNumber + ", aadhaarNumber=" + aadhaarNumber + ", bankAccount=" + bankAccount
				+ ", ifscCode=" + ifscCode + ", monthlyIncome=" + monthlyIncome + ", employmentType=" + employmentType
				+ ", companyName=" + companyName + ", isDefaulted=" + isDefaulted + ", isDeleted=" + isDeleted
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(aadhaarNumber, address, age, bankAccount, city, companyName, createdAt, dateOfBirth, email,
				employmentType, fullName, gender, id, ifscCode, isDefaulted, isDeleted, mobileNumber, monthlyIncome,
				panNumber, password, pincode, role, state, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(aadhaarNumber, other.aadhaarNumber) && Objects.equals(address, other.address)
				&& Objects.equals(age, other.age) && Objects.equals(bankAccount, other.bankAccount)
				&& Objects.equals(city, other.city) && Objects.equals(companyName, other.companyName)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(email, other.email) && Objects.equals(employmentType, other.employmentType)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(gender, other.gender)
				&& Objects.equals(id, other.id) && Objects.equals(ifscCode, other.ifscCode)
				&& Objects.equals(isDefaulted, other.isDefaulted) && Objects.equals(isDeleted, other.isDeleted)
				&& Objects.equals(mobileNumber, other.mobileNumber)
				&& Objects.equals(monthlyIncome, other.monthlyIncome) && Objects.equals(panNumber, other.panNumber)
				&& Objects.equals(password, other.password) && Objects.equals(pincode, other.pincode)
				&& role == other.role && Objects.equals(state, other.state)
				&& Objects.equals(updatedAt, other.updatedAt);
	}
    
    
    
    
}