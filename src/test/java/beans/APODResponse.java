package beans;

public class APODResponse {
	int responseCode;
	
	String responseMessage;
	boolean multiple;
	APODObject singleAPOD;
	APODObject[] multipleAPODS;
	
	
	public APODResponse(){
		multiple = false;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public APODObject getSingleAPOD() {
		return singleAPOD;
	}
	public void setSingleAPOD(APODObject singleAPOD) {
		this.singleAPOD = singleAPOD;
	}
	public APODObject[] getMultipleAPODS() {
		return multipleAPODS;
	}
	public void setMultipleAPODS(APODObject[] multipleAPODS) {
		this.multipleAPODS = multipleAPODS;
	}
	
}
