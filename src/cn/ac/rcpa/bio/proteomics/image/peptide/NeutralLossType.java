package cn.ac.rcpa.bio.proteomics.image.peptide;

public class NeutralLossType implements INeutralLossType {
	public NeutralLossType(double mass, String name, boolean allowMultipleLoss) {
		this.mass = mass;
		this.name = name;
		this.allowMultipleLoss = allowMultipleLoss;
	}

	private double mass;
	private String name;
	private boolean allowMultipleLoss;

	public double getMass() {
		return mass;
	}

	public String getName() {
		return name;
	}

	public boolean canMultipleLoss() {
		return allowMultipleLoss;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(mass);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NeutralLossType other = (NeutralLossType) obj;
		if (Double.doubleToLongBits(mass) != Double
				.doubleToLongBits(other.mass))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int getCount() {
		return 1;
	}
}
