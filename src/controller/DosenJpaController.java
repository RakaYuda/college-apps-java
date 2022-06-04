/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Dosen;

/**
 *
 * @author raka
 */
public class DosenJpaController implements Serializable {

    public DosenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dosen dosen) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(dosen);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDosen(dosen.getNidn()) != null) {
                throw new PreexistingEntityException("Dosen " + dosen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dosen dosen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            dosen = em.merge(dosen);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dosen.getNidn();
                if (findDosen(id) == null) {
                    throw new NonexistentEntityException("The dosen with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dosen dosen;
            try {
                dosen = em.getReference(Dosen.class, id);
                dosen.getNidn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dosen with id " + id + " no longer exists.", enfe);
            }
            em.remove(dosen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dosen> findDosenEntities() {
        return findDosenEntities(true, -1, -1);
    }

    public List<Dosen> findDosenEntities(int maxResults, int firstResult) {
        return findDosenEntities(false, maxResults, firstResult);
    }

    private List<Dosen> findDosenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dosen.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Dosen findDosen(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dosen.class, id);
        } finally {
            em.close();
        }
    }

    public int getDosenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dosen> rt = cq.from(Dosen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
