package in.education.college.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = -6820586948643587814L;

	@Id
	@Column(name="id", updatable = false, nullable = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long userId;

	@Column(name="user_name")
	@NotNull
	private String username;

	@Column(name="password")
	@NotNull
	private String password;

	@Column(name="disabled")
	private Boolean disabled;

	/*@Column(name="role_id")
	@NotNull
	private int roleId;*/

	@Column(name="user_desc")
	private String userDesc;

	/*@Transient
	private String newPassword;

	@Transient
	private String confirmPassword;*/

	/*@Column(name="student_id")
	private Long studentId;

	@Column(name="faculty_id")
	private Long facultyId;*/

	@Column(name="email")
	private String email;

	@Column(name="last_login")
	private Date lastLogin;

	@Column(name="failed_login_attempts")
	private Long failedLoginAttempts;

//	@OneToMany(fetch= FetchType.EAGER) //cascade= CascadeType.ALL,
	@ManyToMany(fetch= FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinTable(name="user_roles",
			joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
			inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName=
					"role_id", unique = false)}
	)
	private List<Role> roles;

	@Column(name="last_password_change")
	private Date lastPasswordChange;

	/*@ManyToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
	@JoinTable(name="user_roles",
			joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
			inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName=
					"role_id")}
	)
	private List<Role> roles;*/


	/*@ManyToMany
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;*/



	/*@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="role_id")
	private Role role;*/

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}*/

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	/*public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(long facultyId) {
		this.facultyId = facultyId;
	}*/

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Long getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(Long failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	/*public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}*/

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	/*public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}*/

	public Date getLastPasswordChange() {
		return lastPasswordChange;
	}

	public void setLastPasswordChange(Date lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public User() {

	}

	public User(long userId, String userName, String email, boolean disabled,
			String userDesc) {
		super();
		this.userId = userId;
		this.username = userName;
		this.email = email;
		this.disabled = disabled;
		this.userDesc = userDesc;
	}

	public User (User user) {
		this.userId = user.userId;
		this.username = user.username;
		this.password = user.password;
		this.email = user.email;
		this.disabled = user.disabled;
		this.userDesc = user.userDesc;
		this.lastLogin = user.lastLogin;
		this.failedLoginAttempts = user.failedLoginAttempts;
		this.roles = user.roles;
	}

	public User(long userId, String userName, String password) {
//		, String roles
		super();
		this.userId = userId;
		this.username = userName;
		this.password = password;
		//this.roles = Arrays.asList(roles.split(","));
	}
}
