package heavenhr.model;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import heavenhr.common.Messages;

/**
 * Represents an Application
 * 
 * @author  Deepthi KV
 * 
 *
 */
public class Application {

	/**
	 * Resume text
	 */
	private String resumeTxt;

	/**
	 * Email
	 */
	@NotNull(message = Messages.EMPTY_EMAIL)
	@NotBlank(message = Messages.EMPTY_EMAIL)
	@Email(message = Messages.INVALID_EMAIL, regexp = Messages.EMAIL_PATTERN)
	private String email;

	/**
	 * Application status
	 */
	private ApplicationStatus status;

	/**
	 * Offer
	 */
	
	//@NotNull(message = AppConstants.EMPTY_EMAIL)
	//@NotBlank(message = AppConstants.EMPTY_EMAIL)
	@Valid
	//@JsonBackReference
	private Offer offer;

	/**
	 * Sets the applications status
	 * 
	 * @param status
	 */
	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	/**
	 * Gets the application status
	 * 
	 * @return application status
	 */
	public ApplicationStatus getStatus() {
		return status;
	}

	/**
	 * Gets the offer
	 * 
	 * @return offer
	 */
	public Offer getOffer() {
		return offer;
	}

	/**
	 * Sets the offer
	 * 
	 * @param offer
	 */
	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	/**
	 * Gets the email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the resume text
	 * 
	 * @return resume text
	 */
	public String getResumeTxt() {
		return resumeTxt;
	}

	/**
	 * Sets the resume text
	 * 
	 * @param resumeTxt
	 */
	public void setResumeTxt(String resumeTxt) {
		this.resumeTxt = resumeTxt;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Application other = (Application) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Application [resumeTxt=" + resumeTxt + ", email=" + email + ", status=" + status + ", offer=" + offer
				+ "]";
	}

}
