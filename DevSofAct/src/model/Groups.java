package model;
// Generated 12-jun-2018 19:07:14 by Hibernate Tools 5.2.8.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Groups generated by hbm2java
 */
@Entity
@Table(name = "groups", schema = "sfe")
public class Groups implements java.io.Serializable {

	private String groupname;

	public Groups() {
	}

	public Groups(String groupname) {
		this.groupname = groupname;
	}

	@Id

	@Column(name = "groupname", unique = true, nullable = false, length = 250)
	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

}
