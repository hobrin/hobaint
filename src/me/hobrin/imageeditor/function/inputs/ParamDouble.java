package me.hobrin.imageeditor.function.inputs;

public class ParamDouble extends IParam<Double> {

	public ParamDouble(String label, Double value) {
		super(label, value);
	}
	public ParamDouble(String label) {
		super(label, 0D);
	}
	@Override
	public boolean isValid(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	@Override
	public Double fromString(String val) throws Exception {
		return Double.parseDouble(val);
	}
}
