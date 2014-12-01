package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.util.ArrayList;
import java.util.List;

public class CombinedNeutralLossType implements INeutralLossType {
	private List<INeutralLossType> combinedTypes;

	public CombinedNeutralLossType(List<INeutralLossType> combinedTypes) {
		this.combinedTypes = new ArrayList<INeutralLossType>(combinedTypes);
		initialize();
	}

	public CombinedNeutralLossType(INeutralLossType aType) {
		if (aType instanceof CombinedNeutralLossType) {
			CombinedNeutralLossType oldType = (CombinedNeutralLossType) aType;
			this.combinedTypes = new ArrayList<INeutralLossType>(
					oldType.combinedTypes);
		} else {
			this.combinedTypes = new ArrayList<INeutralLossType>();
			this.combinedTypes.add(aType);
		}
		initialize();
	}

	public boolean insertNeutralLossType(INeutralLossType aType) {
		if (!aType.canMultipleLoss()) {
			for (INeutralLossType oldType : combinedTypes) {
				if (oldType.equals(aType)) {
					return false;
				}
			}
		}
		combinedTypes.add(0, aType);
		initialize();
		return true;
	}

	private double mass;

	private String name;

	private void initialize() {
		mass = 0.0;

		StringBuilder sb = new StringBuilder();
		for (INeutralLossType oldType : combinedTypes) {
			mass += oldType.getMass();
			if (sb.length() == 0) {
				sb.append(oldType.getName());
			} else {
				sb.append('-').append(oldType.getName());
			}
		}
		name = sb.toString();
	}

	public int getCount() {
		return combinedTypes.size();
	}

	@Override
	public boolean canMultipleLoss() {
		return false;
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public String getName() {
		return name;
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
		CombinedNeutralLossType other = (CombinedNeutralLossType) obj;
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

}
