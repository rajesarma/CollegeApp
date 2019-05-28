package in.education.college.dto;

import java.io.Serializable;

public class ServiceDto implements Serializable { // , Comparable<Service>
	private Long serviceId;
	private String serviceUrl;
	private String serviceName;
	private Boolean disabled;
	private Long parentId;
	private Long displayOrder;
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
}
