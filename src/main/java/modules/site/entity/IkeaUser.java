/**
 * 
 */
/**
 * @author hadong
 *
 */
package modules.site.entity;

import java.io.Serializable;

public class IkeaUser implements Serializable {
	private static final long serialVersionUID = -8765640214072557201L;
	private String mmbMembershipnum;
	private String cstName;
	private String cstMobile;
	private String xcsIdcardnum;
	private String preferStore;

	public IkeaUser() {

	}

	public IkeaUser(String mmbMembershipnum, String cstName, String cstMobile, String xcsIdcardnum, String preferStore) {
		this.mmbMembershipnum = mmbMembershipnum;
		this.cstName = cstName;
		this.cstMobile = cstMobile;
		this.xcsIdcardnum = xcsIdcardnum;
		this.preferStore = preferStore;
	}

	public String getMmbMembershipnum() {
		return mmbMembershipnum;
	}

	public String getCstName() {
		return cstName;
	}

	public String getCstMobile() {
		return cstMobile;
	}

	public String getXcsIdcardnum() {
		return xcsIdcardnum;
	}

	public String getPreferStore() {
		return preferStore;
	}

	public void setMmbMembershipnum(String mmbMembershipnum) {
		this.mmbMembershipnum = mmbMembershipnum;
	}

	public void setCstName(String cstName) {
		this.cstName = cstName;
	}

	public void setCstMobile(String cstMobile) {
		this.cstMobile = cstMobile;
	}

	public void setXcsIdcardnum(String xcsIdcardnum) {
		this.xcsIdcardnum = xcsIdcardnum;
	}

	public void setPreferStore(String preferStore) {
		this.preferStore = preferStore;
	}

}