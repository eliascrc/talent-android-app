package model;

/**
 * Class that represents a technical's resource emergency contact within the Talent system.
 * It contains the email, name, telephone and the information inherited from
 * {@link BasicEntity} class.
 *
 * @author Elías Calderon
 */
public class EmergencyContact extends BasicEntity{

    /**
     * email of the emergency contact.
     */
    private String email;

    /**
     * Name of the emergency contact.
     */
    private String name;

    /**
     * Telephone of the emergency contact.
     */
    private String telephone;

    /**
     * User that has the emergency contact.
     */
    private TechnicalResource technicalResource;

    public EmergencyContact(){}

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof EmergencyContact){
            EmergencyContact emergencyContact = (EmergencyContact) o;
            result = (this.technicalResource == null ? emergencyContact.getTechnicalResource() == null : this.technicalResource.equals(emergencyContact.getTechnicalResource())
                    && this.email == null ? emergencyContact.getEmail() == null : this.email.equals(emergencyContact.getEmail()));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.technicalResource == null ? 0 : this.technicalResource.hashCode());
        result = prime * result + (this.email == null ? 0 : this.email.hashCode());
        return result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public TechnicalResource getTechnicalResource() {
        return technicalResource;
    }

    public void setTechnicalResource(TechnicalResource technicalResource) {
        this.technicalResource = technicalResource;
    }
}
