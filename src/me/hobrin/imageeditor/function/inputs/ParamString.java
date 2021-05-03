package me.hobrin.imageeditor.function.inputs;

public class ParamString extends IParam<String> {

	public ParamString(String label, String value) {
		super(label, value);
	}
	public ParamString(String label) {
		super(label, "");
	}
	@Override
	public boolean isValid(String val) {
		return true;
	}
	@Override
	public String fromString(String val) throws Exception {
		return val;
	}
}
