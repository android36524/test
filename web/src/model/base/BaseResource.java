package model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseResource<M extends BaseResource<M>> extends Model<M> implements IBean {

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

	public void setDescription(java.lang.String description) {
		set("description", description);
	}

	public java.lang.String getDescription() {
		return get("description");
	}

	public void setIsSystem(java.lang.Boolean isSystem) {
		set("isSystem", isSystem);
	}

	public java.lang.Boolean getIsSystem() {
		return get("isSystem");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setOrderList(java.lang.Integer orderList) {
		set("orderList", orderList);
	}

	public java.lang.Integer getOrderList() {
		return get("orderList");
	}

	public void setValue(java.lang.String value) {
		set("value", value);
	}

	public java.lang.String getValue() {
		return get("value");
	}

}