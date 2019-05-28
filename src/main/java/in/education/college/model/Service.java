package in.education.college.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "services")
public class Service implements Serializable {

	private static final long serialVersionUID = 7444887444468987431L; // , Comparable<Service>

	@Id
	@Column(name = "service_id")
	private Long serviceId;

	@Column(name="service_url")
	private String serviceUrl;

	@Column(name="service_name")
	private String serviceName;

	@Column(name="disabled")
	private Boolean disabled;

	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "display_order")
	private Long displayOrder;

	@Column(name = "menu_display")
	private Long menuDisplay;

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Long getMenuDisplay() {
		return menuDisplay;
	}

	public void setMenuDisplay(Long menuDisplay) {
		this.menuDisplay = menuDisplay;
	}

	/*@Override
	public int compareTo(Service s) {
		return getParentId().compareTo(s.getParentId());
	}*/

	/*@Override
	public String toString() {
		return "Service{" +
				"serviceId=" + serviceId +
				", serviceUrl='" + serviceUrl + '\'' +
				", serviceName='" + serviceName + '\'' +
				", disabled=" + disabled +
				", parentId=" + parentId +
				", displayOrder=" + displayOrder +
				", menuDisplay=" + menuDisplay +
				'}';
	}*/
}
