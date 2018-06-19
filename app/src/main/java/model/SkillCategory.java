package model;

import java.util.Set;

/**
 * Class that represents a category for a set of skills within the Talent system.
 * It contains the name of the category and the information inherited from
 * {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class SkillCategory extends BasicEntity {

    /**
     * The name of the skill category.
     */
    private String name;

    /**
     * The organization to whom the category belongs. If it's null the category is a predefined one in the system.
     */
    private Organization organization;

    /**
     * The skills that belong to the category.
     */
    private Set<Skill> skills;

    public SkillCategory(){}

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof SkillCategory){
            SkillCategory skillCategory = (SkillCategory) o;
            result = (this.name == null ? skillCategory.getName() == null : this.name.equals(skillCategory.getName()))
                    && (this.organization == null ? skillCategory.getOrganization() == null : this.organization.equals(skillCategory.getOrganization()));
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
}
