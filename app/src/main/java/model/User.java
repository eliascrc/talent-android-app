package model;

import java.util.Date;

/**
 * Class that represents a User within the Talent system.
 * It contains the username, password, first name, last name and the information inherited from
 * {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public abstract class User extends BasicEntity {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public enum Status {ACTIVE, INACTIVE, SUSPENDED}

    /**
     * Username can't be empty, null or duplicated. It represents the user's email address.
     */
    protected String username;

    /**
     * The user's first name.
     */
    protected String firstName;

    /**
     * The user's last name.
     */
    protected String lastName;

    /**
     * User password.
     */
    protected String password;

    /**
     * Flag to indicate if the user name has been enabled or disabled.
     */
    protected boolean enabled;

    /**
     * Flag to indicate if the account needs a change in the password.
     */
    protected boolean passwordNeedsChange;

    /**
     * Timestamp of the last login of the user.
     */
    private Date lastLoginTimestamp;

    /**
     * The status of the user account.
     */
    private Status status;

    /**
     * The user's token
     */
    private String token;

    public User (){}

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if (o instanceof User){
            User user = (User) o;
            result = (this.username == null ? user.getUsername() == null : this.username.equals(user.getUsername())
                    && this.password == null ? user.getPassword() == null : this.password.equals(user.getPassword()));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + ((this.username == null)? 0 : this.username.hashCode());
        result = prime * result + ((this.password == null)? 0 : this.password.hashCode());
        return result;
    }

    /**
     * @author Rodrigo Bartels
     * @return StringBuilder
     */
    protected StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("username = ").append(this.getUsername());
        sb.append(", enabled = ").append(this.isEnabled());
        sb.append(", passwordNeedsChange = ").append(this.getPasswordNeedsChange());
        return sb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getPasswordNeedsChange() {
        return passwordNeedsChange;
    }

    public void setPasswordNeedsChange(boolean passwordNeedsChange) {
        this.passwordNeedsChange = passwordNeedsChange;
    }

    public Date getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(Date lastLoginTimestamp) {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}