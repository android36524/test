package model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseLogconfig<M extends BaseLogconfig<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setCreateDate(java.util.Date createDate) {
		set("createDate", createDate);
	}

	public java.util.Date getCreateDate() {
		return get("createDate");
	}

	public void setModifyDate(java.util.Date modifyDate) {
		set("modifyDate", modifyDate);
	}

	public java.util.Date getModifyDate() {
		return get("modifyDate");
	}

	public void setActionClassName(java.lang.String actionClassName) {
		set("actionClassName", actionClassName);
	}

	public java.lang.String getActionClassName() {
		return get("actionClassName");
	}

	public void setActionMethodName(java.lang.String actionMethodName) {
		set("actionMethodName", actionMethodName);
	}

	public java.lang.String getActionMethodName() {
		return get("actionMethodName");
	}

	public void setDescription(java.lang.String description) {
		set("description", description);
	}

	public java.lang.String getDescription() {
		return get("description");
	}

	public void setOperationName(java.lang.String operationName) {
		set("operationName", operationName);
	}

	public java.lang.String getOperationName() {
		return get("operationName");
	}

}
