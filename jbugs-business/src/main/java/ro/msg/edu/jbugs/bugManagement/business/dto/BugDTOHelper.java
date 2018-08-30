package ro.msg.edu.jbugs.bugManagement.business.dto;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.DetailedExceptionCode;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BugDTOHelper {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Convers a bug entity to a bugDTO.
     *
     * @param bug not null
     * @return the resulted bugDTO
     */
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

    /**
     * Converts a bugDTO to a Bug entity. Will call UserDTOHelper to convert the user fields.
     *
     * @see BugDTOHelper
     * @param bugDTO not null
     * @param oldBug not null
     * @return the resulted Bug entity
     */
    public static Bug toEntity(@NotNull BugDTO bugDTO,Bug oldBug){

        oldBug.setTitle(bugDTO.getTitle());
        try {
            oldBug.setTargetDate(LocalDate.parse(bugDTO.getTargetDate()));
        }
        catch(Exception e){
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION, DetailedExceptionCode.BUG_TARGET_DATE_NOT_VALID);
        }
        oldBug.setStatus(bugDTO.getStatus());
        oldBug.setFixedVersion(bugDTO.getFixedVersion());
        oldBug.setVersion(bugDTO.getVersion());
        if (isValidSeverity(bugDTO.getSeverity())) {
            oldBug.setSeverity(Severity.valueOf(bugDTO.getSeverity()));
        }
        else {
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,DetailedExceptionCode.BUG_SEVERITY_NOT_VALID);
        }
        oldBug.setCreatedBy(UserDTOHelper.toEntity(bugDTO.getCreatedBy(),oldBug.getCreatedBy()));
        oldBug.setAssignedTo(UserDTOHelper.toEntity(bugDTO.getAssignedTo(),oldBug.getAssignedTo()));

        oldBug.setDescription(bugDTO.getDescription());
        oldBug.setId(bugDTO.getId());
        oldBug.setAttachment(bugDTO.getAttachment());

        return oldBug;


    }

    private static boolean isValidSeverity(String severity){
        for(Severity s : Severity.values()){
            if (s.name().equals(severity)){
                return true;
            }
        }
        return false;
    }
}
