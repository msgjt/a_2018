package ro.msg.edu.jbugs.bugsManagement.persistence.dao;

import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Stateless
public class BugPersistenceManager {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;

    /**
     * Method adds a bug to the DB.
     *
     * @param bug not null
     * @return the created bug with its ID set
     */
    public Bug createBug(@NotNull Bug bug) {
        CustomLogger.logEnter(this.getClass(), "createBug", bug.toString());
        em.persist(bug);
        em.flush();
        CustomLogger.logExit(this.getClass(), "createBug", bug.toString());
        return bug;
    }

    /**
     * Method updates a bug from the DB. <h1> IMPORTANT: READ PARAM DESCRIPTION </h1>
     *
     * @param bug the desired state of the bug entity, MUST BE A DETACHED ENTITY FROM THE
     *            PERSISTENCE CONTEXT (CAN GET IT BY CALLING THE @getUserById or @getUserByUsername METHODS)
     *            !! MAKE SURE YOU DON'T CREATE A NEW OBJECT AND SEND IT TO THIS METHOD !!
     * @return the updated bug
     */
    public Bug updateBug(@NotNull Bug bug) {
        CustomLogger.logEnter(this.getClass(), "updateBug", bug.toString());

        Bug old = em.find(Bug.class,bug.getId());
        old.copyFieldsFrom(bug);
        em.merge(old);

        CustomLogger.logExit(this.getClass(), "updateBug", bug.toString());
        return bug;
    }


    /**
     * Method to get all the bugs from the DB.
     *
     * @return the list of all the bugs from the DB, empty if no bugs found
     */
    public List<Bug> getAllBugs() {
        CustomLogger.logEnter(this.getClass(), "getAllBugs", "");
        List<Bug> result = em.createNamedQuery(Bug.GET_ALL_BUGS, Bug.class)
                .getResultList();
        CustomLogger.logExit(this.getClass(), "getAllBugs", result.toString());
        return result;
    }

    /**
     * Method returns a bug by its title by using a NamedQuery defined in the Bug class.
     *
     * @param title the search key, not null
     * @return an optional of the required bug or an empty optional if no bug found
     */
    public Optional<Bug> getBugByTitle(@NotNull String title) {
        CustomLogger.logEnter(this.getClass(), "getBugByTitle", title);

        TypedQuery<Bug> q = em.createNamedQuery(Bug.GET_BUG_BY_TITLE, Bug.class)
                .setParameter("title", title);
        Optional<Bug> result;
        try {
            result = Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            result = Optional.empty();
        }
        CustomLogger.logExit(this.getClass(), "getBugByTitle", result.toString());
        return result;
    }

    /**
     * Method returns a bug by its id by using a NamedQuery defined in the Bug class.
     *
     * @param id the search key, not null
     * @return an optional of the required bug or an empty optional if no such bug has been found
     */
    public Optional<Bug> getBugById(@NotNull Long id) {
        CustomLogger.logEnter(this.getClass(), "getBugById", String.valueOf(id));
        TypedQuery<Bug> q = em.createNamedQuery(Bug.GET_BUG_BY_ID, Bug.class)
                .setParameter("id", id);
        Optional<Bug> result;
        try {
            result = Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            result = Optional.empty();
        }
        CustomLogger.logExit(this.getClass(), "getBugById", result.toString());
        return result;
    }
}