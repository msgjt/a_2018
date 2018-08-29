package ro.msg.edu.jbugs.bugManagement.business.dto;

import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;

import java.sql.Date;

public class BugDTOHelper {

    public static BugDTO fromEntity(Bug bug){
        BugDTO bugDTO = new BugDTO();

        bugDTO.setTitle(bug.getTitle());
        bugDTO.setTargetDate(bug.getTargetDate().toString());
        bugDTO.setStatus(bug.getStatus());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setVersion(bug.getVersion());
        bugDTO.setSeverity(bug.getSeverity());
        bugDTO.setCreatedBy(UserDTOHelper.fromEntity(bug.getCreatedBy()));
        bugDTO.setAssignedTo(UserDTOHelper.fromEntity(bug.getAssignedTo()));
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setId(bug.getId());
        bugDTO.setAttachment(bug.getAttachment());

        return bugDTO;
    }

    public static Bug toEntity(BugDTO bugDTO){
        Bug bug = new Bug();

        bug.setTitle(bugDTO.getTitle());
        bug.setTargetDate(Date.valueOf(bugDTO.getTargetDate()));
        bug.setStatus(bugDTO.getStatus());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setVersion(bugDTO.getVersion());
        bug.setSeverity(bugDTO.getSeverity());
        bug.setCreatedBy(UserDTOHelper.toEntity(bugDTO.getCreatedBy()));
        bug.setAssignedTo(UserDTOHelper.toEntity(bugDTO.getAssignedTo()));
        bug.setDescription(bugDTO.getDescription());
        bug.setId(bugDTO.getId());
        bug.setAttachment(bugDTO.getAttachment());

        return bug;

    }
}
