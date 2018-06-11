package model;

/**
 * Class that represents an invitation to join the Talent system.
 * It contains the link of the image and the information inherited from
 * {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class Invitation extends BasicEntity {

    /**
     * The name of the person receiving the invitation.
     */
    private String name;

    /**
     * The email of the person that is going to receive the invitation.
     */
    private String email;

    /**
     * The organization where the invitation came from.
     */
    private Organization organization;

    public Invitation (){}

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof Invitation){
            Invitation invitation = (Invitation) o;
            result = (this.name == null ? invitation.getName() == null : this.name.equals(invitation.getName()));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.name == null ? 0 : this.name.hashCode());
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}