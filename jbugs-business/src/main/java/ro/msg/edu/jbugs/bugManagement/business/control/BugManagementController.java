package ro.msg.edu.jbugs.bugManagement.business.control;

import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.validator.BugValidator;
import ro.msg.edu.jbugs.bugsManagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.DetailedExceptionCode;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class BugManagementController implements BugManagement {

    @EJB
    private BugPersistenceManager bugPersistenceManager;

    @EJB
    private BugValidator bugValidator;

    /**
     * Gets all the bugs in the form of a list.
     * @return the list of all the bugs, empty if no bugs found.
     */
    @Override
    public List<BugDTO> getAllBugs() {
        CustomLogger.logEnter(this.getClass(),"getAllBugs","");

        List<BugDTO> result = bugPersistenceManager.getAllBugs()
                .stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());

        CustomLogger.logExit(this.getClass(),"getAllBugs",result.toString());
        return result;
    }

    /**
     * Adds a bug by receiving a bugDTO, which it will validate and persist in the DB.
     * @param bugDTO an object of type BugDTO or null
     * @return the resulted bugDTO persisted in the DB (its id will be set)
     */
    @Override
    public BugDTO createBug(BugDTO bugDTO) {
        CustomLogger.logEnter(this.getClass(),"createBug",bugDTO.toString());

        bugValidator.validateCreate(bugDTO);
        Bug bug = BugDTOHelper.toEntity(bugDTO);
        bug = bugPersistenceManager.createBug(bug);
        BugDTO result = BugDTOHelper.fromEntity(bug);

        CustomLogger.logExit(this.getClass(),"createBug",result.toString());
        return result;
    }

    /**
     * Updates a bug by receiving a bugDTO, which it will validate and persist in the DB.
     * @param bugDTO an object of type BugDTO or null
     * @return the resulted bugDTO persisted in the DB (its id will be set)
     */
    @Override
    public BugDTO updateBug(BugDTO bugDTO) {
        CustomLogger.logEnter(this.getClass(),"updateBug",bugDTO.toString());

        bugValidator.validateUpdate(bugDTO);
        Bug bug = BugDTOHelper.toEntity(bugDTO);

        if( ! canChangeStatus(bug) ){
            CustomLogger.logException(this.getClass(),"updateBug","STATUS_INCOMPATIBLE");
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,
                    DetailedExceptionCode.BUG_STATUS_INCOMPATIBLE);
        }

        bug = bugPersistenceManager.updateBug(bug);
        BugDTO result = BugDTOHelper.fromEntity(bug);
        
        CustomLogger.logExit(this.getClass(),"updateBug",result.toString());
        return result;
    }

    /**
     * Checks if the desired update of the status can be done.
     * @param bug not null, the updated bug
     * @return true if the status can be changed, false otherwise
     */
    private boolean canChangeStatus(@NotNull Bug bug) {
        CustomLogger.logEnter(this.getClass(),"canChangeStatus",bug.toString());

        Map<String,Set<String>> allowedTransitions = new HashMap<String,Set<String>>() {{
            put("Open",new HashSet<>(Arrays.asList("Open","InProgress")));
            put("InProgress",new HashSet<>(Arrays.asList("InProgress","Fixed","Rejected","InfoNeeded")));
            put("Fixed",new HashSet<>(Arrays.asList("Fixed","Open","Closed")));
            put("Rejected",new HashSet<>(Arrays.asList("Rejected","Closed")));
            put("InfoNeeded",new HashSet<>(Arrays.asList("InfoNeeded","InProgress")));
            put("Closed",new HashSet<>(Collections.singletonList("Closed")));
        }};

        Optional<Bug> oldBug = bugPersistenceManager.getBugById(bug.getId());
        boolean result = oldBug.map( b -> allowedTransitions.get(b.getStatus()).contains(bug.getStatus()))
                .orElseThrow(() -> {
                    CustomLogger.logException(this.getClass(),"canChangeStatus","BUG_NOT_FOUND");
                    return new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,
                            DetailedExceptionCode.BUG_NOT_FOUND);
                });

        CustomLogger.logExit(this.getClass(),"canChangeStatus",String.valueOf(result));
        return result;
    }

    /**
     * Gets a bug by searching for its title. Will validate the title.
     * @param title the title that the resulted bug must have.
     * @return a bugDTO representing the bug that contains that title
     * @throws BusinessException if title is null or not found
     */
    @Override
    public BugDTO getBugByTitle(String title) {
        CustomLogger.logEnter(this.getClass(),"getBugByTitle",title);

        if(title == null){
            CustomLogger.logException(this.getClass(),"getBugByTitle","TITLE_NULL");
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,
                    DetailedExceptionCode.BUG_TITLE_NULL);
        }
        
        Optional<Bug> optionalBug = bugPersistenceManager.getBugByTitle(title);
        BugDTO result = optionalBug.map(BugDTOHelper::fromEntity)
                .orElseThrow(() -> {
                    CustomLogger.logException(this.getClass(),"getBugByTitle","TITLE_NOT_FOUND");
                    return new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,
                            DetailedExceptionCode.BUG_NOT_FOUND);
                });

        CustomLogger.logExit(this.getClass(),"getBugByTitle",result.toString());
        return result;
    }

    /**
     * Gets a bug by searching for its id. Will validate the id.
     * @param id the id that the resulted bug must have.
     * @return a bugDTO representing the bug that contains that id
     * @throws BusinessException if id is null or not found 
     */
    @Override
    public BugDTO getBugById(Long id) {
        CustomLogger.logEnter(this.getClass(),"getBugById",String.valueOf(id));

        if(id == null){
            CustomLogger.logException(this.getClass(),"getBugById","ID_NULL");
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,
                    DetailedExceptionCode.BUG_ID_NULL);
        }
        
        Optional<Bug> optionalBug = bugPersistenceManager.getBugById(id);
        BugDTO result = optionalBug.map(BugDTOHelper::fromEntity)
                .orElseThrow(() -> {
                    CustomLogger.logException(this.getClass(),"getBugById","ID_NOT_FOUND");
                    return new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,
                            DetailedExceptionCode.BUG_NOT_FOUND);
                });

        CustomLogger.logExit(this.getClass(),"getBugById",result.toString());
        return result;
    }

}
