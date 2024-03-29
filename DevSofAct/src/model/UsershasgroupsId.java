package model;
// Generated 12-jun-2018 19:07:14 by Hibernate Tools 5.2.8.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UsershasgroupsId generated by hbm2java
 */
@Embeddable
public class UsershasgroupsId implements java.io.Serializable {

	private String username;
	private String groupname;

	public UsershasgroupsId() {
	}

	public UsershasgroupsId(String username, String groupname) {
		this.username = username;
		this.groupname = groupname;
	}

	@Column(name = "username", length = 250)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "groupname", length = 250)
	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UsershasgroupsId))
			return false;
		UsershasgroupsId castOther = (UsershasgroupsId) other;

		return ((this.getUsername() == castOther.getUsername()) || (this.getUsername() != null
				&& castOther.getUsername() != null && this.getUsername().equals(castOther.getUsername())))
				&& ((this.getGroupname() == castOther.getGroupname()) || (this.getGroupname() != null
						&& castOther.getGroupname() != null && this.getGroupname().equals(castOther.getGroupname())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUsername() == null ? 0 : this.getUsername().hashCode());
		result = 37 * result + (getGroupname() == null ? 0 : this.getGroupname().hashCode());
		return result;
	}

}
