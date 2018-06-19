package model;

import java.util.Set;

/**
 * Class that represents a Capability Level within the Talent system.
 * It contains the capability level name, the level hierarchy position and the information inherited
 * from {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class CapabilityLevel extends BasicEntity {

    /**
     * The name of the capability level
     */
    private String name;

    /**
     * The position in the level hierarchy for the capability, that the level possesses.
     */
    private int hierarchyPosition;

    /**
     * The parent capability of the level.
     */
    private Capability capability;

    /**
     * The required skills to achieve the capability level.
     */
    private Set<Skill> requiredSkills;

    /**
     * The organization of the capability level. If it points no organization, the capability level is taken as a
     * predefined capability level.
     */
    private Organization organization;

    /**
     * The projects where the capability is in use
     */
    private Set<Project> projects;

    public CapabilityLevel(){}

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof CapabilityLevel){
            CapabilityLevel capabilityLevel = (CapabilityLevel) o;
            result = ((this.name == null ? capabilityLevel.getName() == null : this.name.equals(capabilityLevel.getName()))
                    && (this.organization == null ? capabilityLevel.getOrganization() == null : this.organization.equals(capabilityLevel.getOrganization()))
                    && (this.capability == null ? capabilityLevel.getCapability() == null : this.capability.equals(capabilityLevel.getCapability())));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + (this.organization == null ? 0 : this.organization.hashCode());
        result = prime * result + (this.capability == null ? 0 : this.capability.hashCode());
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHierarchyPosition() {
        return hierarchyPosition;
    }

    public void setHierarchyPosition(int hierarchyPosition) {
        this.hierarchyPosition = hierarchyPosition;
    }

    public Capability getCapability() {
        return capability;
    }

    public void setCapability(Capability capability) {
        this.capability = capability;
    }

    public Set<Project> getProject() {
        return projects;
    }

    public void setProject(Set<Project> project) {
        this.projects = project;
    }

    public Set<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(Set<Skill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}