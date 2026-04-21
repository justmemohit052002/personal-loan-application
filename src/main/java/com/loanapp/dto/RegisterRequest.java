package com.loanapp.dto;

import java.util.Objects;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid mobile number")
    private String mobileNumber;

    private Integer age;
    private String gender;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String panNumber;
    private String aadhaarNumber;
    private String bankAccount;
    private String ifscCode;
    private Double monthlyIncome;
    private String employmentType;
    private String companyName;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	@Override
	public String toString() {
		return "RegisterRequest [fullName=" + fullName + ", email=" + email + ", password=" + password
				+ ", mobileNumber=" + mobileNumber + ", age=" + age + ", gender=" + gender + ", address=" + address
				+ ", city=" + city + ", state=" + state + ", pincode=" + pincode + ", panNumber=" + panNumber
				+ ", aadhaarNumber=" + aadhaarNumber + ", bankAccount=" + bankAccount + ", ifscCode=" + ifscCode
				+ ", monthlyIncome=" + monthlyIncome + ", employmentType=" + employmentType + ", companyName="
				+ companyName + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(aadhaarNumber, address, age, bankAccount, city, companyName, email, employmentType,
				fullName, gender, ifscCode, mobileNumber, monthlyIncome, panNumber, password, pincode, state);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegisterRequest other = (RegisterRequest) obj;
		return Objects.equals(aadhaarNumber, other.aadhaarNumber) && Objects.equals(address, other.address)
				&& Objects.equals(age, other.age) && Objects.equals(bankAccount, other.bankAccount)
				&& Objects.equals(city, other.city) && Objects.equals(companyName, other.companyName)
				&& Objects.equals(email, other.email) && Objects.equals(employmentType, other.employmentType)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(gender, other.gender)
				&& Objects.equals(ifscCode, other.ifscCode) && Objects.equals(mobileNumber, other.mobileNumber)
				&& Objects.equals(monthlyIncome, other.monthlyIncome) && Objects.equals(panNumber, other.panNumber)
				&& Objects.equals(password, other.password) && Objects.equals(pincode, other.pincode)
				&& Objects.equals(state, other.state);
	}
    
    
    
}