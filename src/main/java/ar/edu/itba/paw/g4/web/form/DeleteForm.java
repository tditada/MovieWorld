package ar.edu.itba.paw.g4.web.form;

public class DeleteForm {
	int commentId;
	int userId;
	
	public DeleteForm() {
	}

	public int getCommentId(){
		return commentId;
	}
	
	public int getUserId(){
		return userId;
	}
	
	public void setCommentId(int commentId){
		this.commentId=commentId;
	}
	
	public void setUserId(int userId){
		this.userId=userId;
	}
}
