package Util;

public class Command {
	String type;
	Object content;
	
	public Command(String type, Object cont){
		setType(type);
		setContent(cont);
	}
	
	public String getType(){
		return this.type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public Object getContent(){
		return this.content;
	}
	
	public void setContent(Object cont){
		this.content = cont;
	}
}
