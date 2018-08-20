package ro.msg.edu.jbugs.bugsManagement.persistence.dao;

import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Stateless
public class BugPersistenceManager {


        private static final long serialVersionUID = 1L;

        @PersistenceContext(unitName = "jbugs-persistence")
        private EntityManager em;

        public Bug createBug(@NotNull Bug bug) {
            em.persist(bug);
            em.flush();
            return bug;
        }

        public Bug updateBug(@NotNull Bug bug) {

            return em.merge(bug);
        }


        public List<Bug> getAllBugs() {
            return em.createNamedQuery(Bug.GET_ALL_BUGS, Bug.class)
                    .getResultList();
        }


        public Optional<Bug> getBugByTitle(@NotNull String title) {
            TypedQuery<Bug> q = em.createNamedQuery(Bug.GET_BUG_BY_TITLE, Bug.class)
                    .setParameter("title",title);
            try {
                return Optional.of(q.getSingleResult());
            } catch (NoResultException ex) {
                return Optional.empty();
            }
        }
}