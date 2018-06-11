package model;

import java.util.Date;

/**
 * Class that represents a lead position within the Talent system.
 * It contains the lead's start date, end date, active flag, the project, associated lead
 * and the information inherited from {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class LeadPosition extends BasicEntity {

    /**
     * Start date of the lead in the position.
     */
    private Date startDate;

    /**
     * End date of the lead in the position.
     */
    private Date endDate;

    /**
     * Indicates if the position is currently active.
     */
    private boolean active;

    /**
     * The project of the lead position.
     */
    private Project project;

    /**
     * The lead that holds this position.
     */
    private TechnicalResource lead;

    public LeadPosition(){}

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof LeadPosition){
            LeadPosition leadPosition = (LeadPosition) o;
            result = ((this.project == null ? leadPosition.getProject() == null : this.project.equals(leadPosition.getProject()))
                    && (this.lead == null ? leadPosition.getLead() == null : this.lead.equals(leadPosition.getLead())));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.project == null ? 0 : this.project.hashCode());
        result = prime * result + (this.lead == null ? 0 : this.lead.hashCode());
        return result;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TechnicalResource getLead() {
        return lead;
    }

    public void setLead(TechnicalResource lead) {
        this.lead = lead;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
