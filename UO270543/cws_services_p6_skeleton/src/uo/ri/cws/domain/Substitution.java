package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Substitution {
	// natural attributes
	private int quantity;

	// accidental attributes
	private SparePart sparePart;
	private Intervention intervention;


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

	

}
