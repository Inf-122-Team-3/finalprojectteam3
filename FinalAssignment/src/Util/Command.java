package Util;

public class Command {
	String type;
	String content;
	boolean fail;
	
	public Command(String type, String cont_json){
		setType(type);
		setContent(cont_json);
	}
	
	public Command(String type, String cont_json, boolean fail){
		setType(type);
		setContent(cont_json);
		setFail(fail);
	}
	
	public void setFail(boolean fail){
		this.fail = fail;
	}
	
	public boolean getFail(){
		return this.fail;
	}
	
	public String getType(){
		return this.type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public void setContent(String cont){
		this.content = cont;
	}
}
