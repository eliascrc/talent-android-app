package model;

import java.util.Date;

/**
 * Class that represents a job position within the Talent system.
 * It contains the start date, end date and the information inherited from
 * {@link Position} class.
 *
 * @author Elías Calderón
 */
public class JobPosition extends Position {

    /**
     * Start date of the job position.
     */
    private Date startDate;

    /**
     * End date of the job position.
     */
    private Date endDate;

    /**
     * Job position invitation.
     */
    private Invitation invitation;

    public JobPosition (){}

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

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }
}
