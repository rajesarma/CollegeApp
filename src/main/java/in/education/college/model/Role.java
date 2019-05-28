package in.education.college.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="roles")
//@SecondaryTable(name="roles")
public class Role implements Serializable {

	private static final long serialVersionUID = -5034389007096787876L;

	@Id
	@Column(name="role_id", unique=true, nullable = false)
	@GeneratedValue
	private long roleId;

	@Column(name="role_name")
	private String roleName;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/*@ManyToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
	@JoinTable(name="role_services",
			joinColumns = {@JoinColumn(name="role_id", referencedColumnName="role_id")},
			inverseJoinColumns = {@JoinColumn(name="service_id", referencedColumnName=
					"service_id")}
	)
	private List<Service> services;*/

	//	@Column(insertable=false, updatable=false)
	@OneToMany(fetch= FetchType.EAGER) //cascade= CascadeType.ALL ,
	@JoinTable(name="role_services",
			   joinColumns = {@JoinColumn(name="role_id", referencedColumnName="role_id")}, // ,insertable = false, updatable = false
			   inverseJoinColumns = {@JoinColumn(name="service_id",referencedColumnName="service_id")}//,insertable = false, updatable = false
			  )
	private List<Service> services;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public String getRole() {
		return "ROLE_" + this.getRoleName();
	}
}
