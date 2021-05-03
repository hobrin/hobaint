package me.hobrin.imageeditor.function.inputs;

import javax.swing.JTextField;

public abstract class IParam<T> {
	private T value;
	public String label;

	protected JTextField field;
	
	public IParam(String label, T value) {
		this.label = label;
		this.value = value;
	}

	public String toString() {
		return label + ": " + this.stringValue();
	}

	public String stringValue() {
		return this.value.toString();
	}

	public T getValue() {
		try {
			this.value = fromString(field.getText());
		} catch (Exception e) {
			//text was formatted wrongly.
			this.field.setText(stringValue());
		}
		return this.value;
	}

	public void setValue(T value) {
		this.value = value;
		this.field.setText(stringValue());
	}


	public JTextField genGetJField() {
		if (field == null) {
			field = new JTextField(stringValue());
		}
		return field;
	}

	public abstract boolean isValid(String val);
	public abstract T fromString(String val) throws Exception;
}
