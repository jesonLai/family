package sys.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * The persistent class for the financial database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="financial_cache")
@Entity
@NamedQuery(name="Financial.findAll", query="SELECT f FROM Financial f")
public class Financial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="income_money")
	private BigDecimal incomeMoney;

	@Column(name="income_type")
	private String incomeType;

	@Column(name="pay_money")
	private BigDecimal payMoney;

	@Column(name="pay_type")
	private String payType;

	@Lob
	private String remark;

	@Column(name="u_id")
	private int uId;

	public Financial() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getIncomeMoney() {
		return this.incomeMoney;
	}

	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public String getIncomeType() {
		return this.incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public BigDecimal getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getUId() {
		return this.uId;
	}

	public void setUId(int uId) {
		this.uId = uId;
	}

}