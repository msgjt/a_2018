package ro.msg.edu.jbugs.bugManagement.business.control;

import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;

import java.util.List;


public interface BugManagement {


    /**
     * Gets all the bugs in the form of a list.
     *
     * @return the list of all the bugs, empty if no bugs found.
     */
    List<BugDTO> getAllBugs();


    /**
     * Adds a bug by receiving a bugDTO, which it will validate and persist in the DB.
     *
     * @param bugDTO an object of type BugDTO or null
     * @return the resulted bugDTO persisted in the DB (its id will be set)
     */
    BugDTO createBug(BugDTO bugDTO);


    /**
     * Updates a bug by receiving a bugDTO, which it will validate and persist in the DB.
     *
     * @param bugDTO an object of type BugDTO or null
     * @return the resulted bugDTO persisted in the DB (its id will be set)
     */
    BugDTO updateBug(BugDTO bugDTO);


    /**
     * Gets a bug by searching for its id. Will validate the id.
     *
     * @param id the id that the resulted bug must have.
     * @return a bugDTO representing the bug that contains that id
     * @throws BusinessException if id is null or not found
     */
    BugDTO getBugById(Long id);


    /**
     * Gets a bug by searching for its title. Will validate the title.
     *
     * @param title the title that the resulted bug must have.
     * @return a bugDTO representing the bug that contains that title
     * @throws BusinessException if title is null or not found
     */
    BugDTO getBugByTitle(String title);


}
