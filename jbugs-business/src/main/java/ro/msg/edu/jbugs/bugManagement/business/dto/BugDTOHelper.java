package ro.msg.edu.jbugs.bugManagement.business.dto;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BugDTOHelper {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static BugDTO fromEntity(@NotNull Bug bug){
        BugDTO bugDTO = new BugDTO();

        bugDTO.setTitle(bug.getTitle());
        bugDTO.setTargetDate(bug.getTargetDate().format(formatter));
        bugDTO.setStatus(bug.getStatus());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setVersion(bug.getVersion());
        bugDTO.setSeverity(bug.getSeverity().toString());
        bugDTO.setCreatedBy(UserDTOHelper.fromEntity(bug.getCreatedBy()));
        bugDTO.setAssignedTo(UserDTOHelper.fromEntity(bug.getAssignedTo()));
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setId(bug.getId());
        bugDTO.setAttachment(bug.getAttachment());

        return bugDTO;
    }

    public static Bug toEntity(@NotNull BugDTO bugDTO,Bug oldBug){

        oldBug.setTitle(bugDTO.getTitle());
        oldBug.setTargetDate(LocalDate.parse(bugDTO.getTargetDate()));
        oldBug.setStatus(bugDTO.getStatus());
        oldBug.setFixedVersion(bugDTO.getFixedVersion());
        oldBug.setVersion(bugDTO.getVersion());
        oldBug.setSeverity(Severity.MEDIUM);

        oldBug.setCreatedBy(new User());
        oldBug.setCreatedBy(UserDTOHelper.toEntity(bugDTO.getCreatedBy(),oldBug.getCreatedBy()));
        oldBug.setAssignedTo(new User());
        oldBug.setAssignedTo(UserDTOHelper.toEntity(bugDTO.getAssignedTo(),oldBug.getAssignedTo()));

        oldBug.setDescription(bugDTO.getDescription());
        oldBug.setId(bugDTO.getId());
        oldBug.setAttachment(bugDTO.getAttachment());

        return oldBug;


    }
}
