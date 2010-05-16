package scrumter.model.ajax;

public class AjaxResponse {

	private boolean success;

	private String message;

	private Object data;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean b) {
		this.success = b;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
