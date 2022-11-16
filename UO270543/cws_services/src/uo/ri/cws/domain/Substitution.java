package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TSubstitutions", uniqueConstraints = {@UniqueConstraint(columnNames = {"SPAREPART_ID","INTERVENTION_ID"})})
public class Substitution extends BaseEntity{
	// natural attributes
	private int quantity;

	// accidental attributes
	@ManyToOne
	private SparePart sparePart;
	@ManyToOne
	private Intervention intervention;

	Substitution(){}

	public Substitution(SparePart sp, Intervention i, int quantity) {
		checkArguments(sp, i, quantity);
		this.quantity = quantity;
		Associations.Substitute.link(sp,this,i);
		
	}

	private void checkArguments(SparePart sp, Intervention i, int quantity) {
		ArgumentChecks.isTrue(quantity > 0);
		ArgumentChecks.isNotNull(sp);
		ArgumentChecks.isNotNull(i);
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public double getAmount() {
		return sparePart.getPrice() * quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}

	

}
