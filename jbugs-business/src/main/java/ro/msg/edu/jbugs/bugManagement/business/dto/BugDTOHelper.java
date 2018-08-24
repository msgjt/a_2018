package ro.msg.edu.jbugs.bugManagement.business.dto;

import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;

public class BugDTOHelper {

    public static BugDTO fromEntity(Bug bug){
        BugDTO bugDTO = new BugDTO();

        bugDTO.setTitle(bug.getTitle());
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setStatus(bug.getStatus());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setVersion(bug.getVersion());
        bugDTO.setSeverity(bug.getSeverity());
        bugDTO.setCreatedBy(bug.getCreatedBy());
        bugDTO.setAssignedTo(bug.getAssignedTo());
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setId(bug.getId());


        return bugDTO;
    }

    public static Bug toEntity(BugDTO bugDTO){
        Bug bug = new Bug();

        bug.setTitle(bugDTO.getTitle());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setStatus(bugDTO.getStatus());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setVersion(bugDTO.getVersion());
        bug.setSeverity(bugDTO.getSeverity());
        bug.setCreatedBy(bugDTO.getCreatedBy());
        bug.setAssignedTo(bugDTO.getAssignedTo());
        bug.setDescription(bugDTO.getDescription());
        bug.setId(bugDTO.getId());

        return bug;

    }
}
