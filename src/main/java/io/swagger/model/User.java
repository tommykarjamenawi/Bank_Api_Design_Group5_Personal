package io.swagger.model;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * User
 */

@Entity
public class User   {
  @Id
  private Long userId;
  private String fullName;
  private String email;
  private String role;
  private String password;
  private Double dayLimit;
  private Double transactionLimit;
  private Double remainingDayLimit;

  public User() {
  }

  public User(Long userId, String fullName, String email, String role, Double dayLimit, Double transactionLimit, Double remainingDayLimit) {
    this.userId = userId;
    this.fullName = fullName;
    this.email = email;
    this.role = role;
    this.dayLimit = dayLimit;
    this.transactionLimit = transactionLimit;
    this.remainingDayLimit = remainingDayLimit;
  }

  public User userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/

  
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public User fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Get fullName
   * @return fullName
   **/

  
    public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public User email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/

  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User role(String role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
   **/

  
    public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public User dayLimit(Double dayLimit) {
    this.dayLimit = dayLimit;
    return this;
  }

  /**
   * Get dayLimit
   * @return dayLimit
   **/

  
    public Double getDayLimit() {
    return dayLimit;
  }

  public void setDayLimit(Double dayLimit) {
    this.dayLimit = dayLimit;
  }

  public User transactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
    return this;
  }

  /**
   * Get transactionLimit
   * @return transactionLimit
   **/

  
    public Double getTransactionLimit() {
    return transactionLimit;
  }

  public void setTransactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
  }

  public User remainingDayLimit(Double remainingDayLimit) {
    this.remainingDayLimit = remainingDayLimit;
    return this;
  }

  /**
   * Get remainingDayLimit
   * @return remainingDayLimit
   **/

  
    public Double getRemainingDayLimit() {
    return remainingDayLimit;
  }

  public void setRemainingDayLimit(Double remainingDayLimit) {
    this.remainingDayLimit = remainingDayLimit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.userId, user.userId) &&
        Objects.equals(this.fullName, user.fullName) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.role, user.role) &&
        Objects.equals(this.dayLimit, user.dayLimit) &&
        Objects.equals(this.transactionLimit, user.transactionLimit) &&
        Objects.equals(this.remainingDayLimit, user.remainingDayLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, fullName, email, role, dayLimit, transactionLimit, remainingDayLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    dayLimit: ").append(toIndentedString(dayLimit)).append("\n");
    sb.append("    transactionLimit: ").append(toIndentedString(transactionLimit)).append("\n");
    sb.append("    remainingDayLimit: ").append(toIndentedString(remainingDayLimit)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
