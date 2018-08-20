package ro.msg.edu.jbugs.bugManagement.business.control;

import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import java.util.List;


public interface BugManagement {

    BugDTO createBug(BugDTO bugDTO) throws BusinessException;
    List<BugDTO> getAllBugs();

}