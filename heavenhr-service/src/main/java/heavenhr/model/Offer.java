package heavenhr.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import heavenhr.common.Messages;

/**
 * Represents an Offer
 * 
 * @author  Deepthi KV
 * 
 *
 */
public class Offer {

	/**
	 * Job title
	 */
	@NotNull(message = Messages.EMPTY_JOBTITLE)
	@NotBlank(message = Messages.EMPTY_JOBTITLE)
	private String jobTitle;

	/**
	 * Start date
	 */
	private String startDate;

	/**
	 * Number of application
	 */
	private int noOfApplications;

	/**
	 * Set of applications
	 */
	//@JsonManagedReference
	private Set<Application> applications = new HashSet<>();

	/**
	 * Sets the applications
	 * 
	 * @param applications
	 */
	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}

	/**
	 * Gets all the applications
	 * 
	 * @return set of applications
	 */
	public Set<Application> getApplications() {
		return applications;
	}

	/**
	 * Gets the job title
	 * 
	 * @return job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title
	 * 
	 * @param jobTitle
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Gets the start date
	 * 
	 * @return
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date
	 * 
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get number of applications
	 * 
	 * @return number of applications
	 */
	public int getNoOfApplications() {
		return noOfApplications;
	}

	/**
	 * Sets the number of applications
	 * 
	 * @param noOfApplicagtions
	 */
	public void setNoOfApplicagtions(int noOfApplicagtions) {
		this.noOfApplications = noOfApplicagtions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offer other = (Offer) obj;
		if (jobTitle == null) {
			if (other.jobTitle != null)
				return false;
		} else if (!jobTitle.equals(other.jobTitle))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Offer [jobTitle=" + jobTitle + ", startDate=" + startDate + ", noOfApplications=" + noOfApplications
				+ ", applications=" + applications + "]";
	}

}
