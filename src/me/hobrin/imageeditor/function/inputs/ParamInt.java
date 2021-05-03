package me.hobrin.imageeditor.function.inputs;

public class ParamInt extends IParam<Integer> {

	public ParamInt(String label, Integer value) {
		super(label, value);
	}
	public ParamInt(String label) {
		super(label, 0);
	}
	@Override
	public boolean isValid(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	@Override
	public Integer fromString(String val) throws Exception {
		return Integer.parseInt(val);
	}
}
