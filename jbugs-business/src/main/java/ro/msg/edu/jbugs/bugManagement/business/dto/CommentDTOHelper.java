package ro.msg.edu.jbugs.bugManagement.business.dto;

import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Comment;

public class CommentDTOHelper {

    public static CommentDTO fromEntity(Comment comment){
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setBug(comment.getBug());
        commentDTO.setUser(comment.getUser());
        commentDTO.setDate(comment.getDate());
        commentDTO.setText(comment.getText());

        return commentDTO;
    }

    public static Comment toEntity(CommentDTO commentDTO){
        Comment comment = new Comment();

        comment.setBug(commentDTO.getBug());
        comment.setUser(commentDTO.getUser());
        comment.setDate(commentDTO.getDate());
        comment.setText(commentDTO.getText());

        return comment;

    }
}
