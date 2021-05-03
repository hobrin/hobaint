package me.hobrin.imageeditor.function.inputs;

public class ParamBoolean extends IParam<Boolean> {

	public ParamBoolean(String label, boolean value) {
		super(label, value);
	}
	@Override
	public boolean isValid(String val) {
		//normal Boolean.parseBoolean just does .equalsIgnoreCase("true");
		val = val.toLowerCase();
		return val.equals("true") || val.equals("false");
	}

	@Override
	public Boolean fromString(String val) throws Exception {
		return Boolean.parseBoolean(val);
	}

}
