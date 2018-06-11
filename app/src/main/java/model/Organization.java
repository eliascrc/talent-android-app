package model;

import java.util.Set;

/**
 * Class that represents an organization within the Talent system. It contains the organization's
 * unique identifier, name, logo and other important information for Talent. It also contains the information inherited from
 * {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class Organization extends BasicEntity {

    /**
     * Unique Identifier for every organization in the system.
     */
    private String uniqueIdentifier;

    /**
     * The name of the organization.
     */
    private String name;

    /**
     * Flag that indicates if the organization wants login with two step verification.
     */
    private boolean twoStepVerification;

    /**
     * The number of total technical resources involved in the organization.
     */
    private int totalUsers;

    /**
     * The state of the organization. It might be disabled, enabled or in some stage of the creation wizard.
     */
    private OrganizationState state;

    /**
     * An image with the logo of the organization.
     */
    private Image logo;

    /**
     * The invitation's list for users to join an organization
     */
    private Set<Invitation> invitationsList;

    /**
     * The organization's domain
     */
    private String domain;

    /**
     * A list with the resources that have joined the organization.
     */
    private Set<TechnicalResource> resources;

    /**
     * A list with the organization's capabilities that the administrators have selected.
     */
    private Set<Capability> capabilities;

    /**
     * A list with the categories of skills that the organization has registered.
     */
    private Set<SkillCategory> skillCategories;

    /**
     * A list with the projects that have been created in the organization.
     */
  	private Set<Project> projects;

    public Organization(){}

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof Organization){
            Organization organization = (Organization) o;
            result = (this.uniqueIdentifier == null ? organization.getUniqueIdentifier() == null :
                    this.uniqueIdentifier.equals(organization.getUniqueIdentifier()));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.uniqueIdentifier == null ? 0 : this.uniqueIdentifier.hashCode());
        return result;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getTwoStepVerification() {
        return twoStepVerification;
    }

    public void setTwoStepVerification(boolean twoStepVerification) {
        this.twoStepVerification = twoStepVerification;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public OrganizationState getState() {
        return state;
    }

    public void setState(OrganizationState state) {
        this.state = state;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Set getInvitationsList() {
        return invitationsList;
    }

    public void setInvitationsList(Set<Invitation> invitationsList) {
        this.invitationsList = invitationsList;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public Set<TechnicalResource> getResources() {
        return resources;
    }

    public void setResources(Set<TechnicalResource> resources) {
        this.resources = resources;
    }

    public Set<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Set<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    public Set<SkillCategory> getSkillCategories() {
        return skillCategories;
    }

    public void setSkillCategories(Set<SkillCategory> skillCategories) {
        this.skillCategories = skillCategories;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

}
