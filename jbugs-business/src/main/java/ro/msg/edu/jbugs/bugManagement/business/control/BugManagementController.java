package ro.msg.edu.jbugs.bugManagement.business.control;

import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugsManagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class BugManagementController implements BugManagement {

    private static final Logger logger = LogManager.getLogger(BugManagementController.class);

    @EJB
    private BugPersistenceManager bugPersistenceManager;

    @Override
    public BugDTO createBug(BugDTO bugDTO) throws BusinessException {
        logger.log(Level.INFO, "In createBug method");
        Bug bug = BugDTOHelper.toEntity(bugDTO);
        validateBugForCreation(bugDTO);
        bugPersistenceManager.createBug(bug);

        return BugDTOHelper.fromEntity(bug);
    }

    @Override
    public List<BugDTO> getAllBugs() {
        return bugPersistenceManager.getAllBugs()
                .stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public BugDTO getBugById(Long id) {
        return BugDTOHelper.fromEntity(bugPersistenceManager.getBugById(id).get());
    }

    @Override
    public BugDTO getBugByTitle(String title) {
        return BugDTOHelper.fromEntity(bugPersistenceManager.getBugByTitle(title).get());
    }

    public boolean isValidForCreation(BugDTO bug) {
        return bug.getTitle() != null ||
                bug.getAssignedTo() != null ||
                bug.getCreatedBy() != null ||
                bug.getDescription() != null ||
                bug.getTargetDate() != null ||
                bug.getFixedVersion() != null ||
                bug.getVersion() != null ||
                bug.getSeverity() != null ||
                bug.getStatus() != null ;
    }

    public void validateBugForCreation(BugDTO bugDTO) throws BusinessException {
        if (!isValidForCreation(bugDTO)) {
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }
    }
}
