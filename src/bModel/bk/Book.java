package bModel.bk;

public class Book {
	int BOOKNUM;
	String GENRE;
	String TITLE;
	String WRITER;
	String PUBLISHER;
	String KEYWORD;
	String SUMMARY;
	
	String tfmessage;
	Object tfHowMany;
	
	
	public Object getTfHowMany() {
		return tfHowMany;
	}
	public void setTfHowMany(Object tfHowMany) {
		this.tfHowMany = tfHowMany;
	}
	
	
	public String getTfmessage() {
		return tfmessage;
	}
	public void setTfmessage(String tfmessage) {
		this.tfmessage = tfmessage;
	}
	
	
	
	public int getBOOKNUM() {
		return BOOKNUM;
	}
	public void setBOOKNUM(int bOOKNUM) {
		BOOKNUM = bOOKNUM;
	}
	public String getGENRE() {
		return GENRE;
	}
	public void setGENRE(String gENRE) {
		GENRE = gENRE;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public String getWRITER() {
		return WRITER;
	}
	public void setWRITER(String wRITER) {
		WRITER = wRITER;
	}
	public String getPUBLISHER() {
		return PUBLISHER;
	}
	public void setPUBLISHER(String pUBLISHER) {
		PUBLISHER = pUBLISHER;
	}
	public String getSUMMARY() {
		return SUMMARY;
	}
	public void setSUMMARY(String sUMMARY) {
		SUMMARY = sUMMARY;
	}
	public String getKEYWORD() {
		return KEYWORD;
	}
	public void setKEYWORD(String kEYWORD) {
		KEYWORD = kEYWORD;
	}	
	

}
