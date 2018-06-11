package model;

import java.util.Set;

/**
 * Class that represents a Capability within the Talent system. It contains the capability name,
 * level hierarchy and the information inherited from {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class Capability extends BasicEntity {

    /**
     * The name of the capability.
     */
    private String name;

    /**
     * The level hierarchy registered for the capability. The hierarchyPosition attribute in the {@link CapabilityLevel}
     * indicates the position in the hierarchy (capability levels for a capability).
     */
    private Set<CapabilityLevel> levelHierarchy;

    /**
     * The associated technologies for the respective capability
     */
    private Set<String> associatedTechnologies;

    /**
     * The organization of the capability. If it points no organization, the capability is taken as a
     * predefined capability.
     */
    private Organization organization;

    public Capability(){}

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof Capability){
            Capability capability = (Capability) o;
            result = ((this.name == null ? capability.getName() == null : this.name.equals(capability.getName()))
                    && (this.organization == null ? capability.getOrganization() == null : this.organization.equals(capability.getOrganization())));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + (this.organization == null ? 0 : this.organization.hashCode());
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CapabilityLevel> getLevelHierarchy() {
        return levelHierarchy;
    }

    public void setLevelHierarchy(Set<CapabilityLevel> levelHierarchy) {
        this.levelHierarchy = levelHierarchy;
    }

    public Set<String> getAssociatedTechnologies() {
        return associatedTechnologies;
    }

    public void setAssociatedTechnologies(Set<String> associatedTechnologies) {
        this.associatedTechnologies = associatedTechnologies;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}