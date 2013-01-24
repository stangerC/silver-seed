package com.silver.seed.dao.jpa;

import java.util.Collection;
import javax.persistence.EntityManager;
import junit.framework.TestCase;

/**
 *
 * @author Liaojian
 */
public class BaseJpaDaoTest extends TestCase {
    
    public BaseJpaDaoTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getEntityManager method, of class BaseJpaDao.
     */
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        BaseJpaDao instance = new BaseJpaDaoImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEntityManager method, of class BaseJpaDao.
     */
    public void testSetEntityManager() {
        System.out.println("setEntityManager");
        EntityManager entityManager = null;
        BaseJpaDao instance = new BaseJpaDaoImpl();
        instance.setEntityManager(entityManager);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class BaseJpaDao.
     */
//    public void testCreate_GenericType() {
//        System.out.println("create");
//        Object entity = null;
//        BaseJpaDao instance = new BaseJpaDaoImpl();
//        instance.create(entity);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of create method, of class BaseJpaDao.
//     */
//    public void testCreate_Collection() {
//        System.out.println("create");
//        BaseJpaDao instance = new BaseJpaDaoImpl();
//        instance.create(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class BaseJpaDao.
//     */
//    public void testDelete_1args_1() {
//        System.out.println("delete");
//        Object id = null;
//        BaseJpaDao instance = new BaseJpaDaoImpl();
//        instance.delete(id);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class BaseJpaDao.
//     */
//    public void testDelete_1args_2() {
//        System.out.println("delete");
//        Object entity = null;
//        BaseJpaDao instance = new BaseJpaDaoImpl();
//        instance.delete(entity);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class BaseJpaDao.
//     */
//    public void testDelete_Collection() {
//        System.out.println("delete");
//        BaseJpaDao instance = new BaseJpaDaoImpl();
//        instance.delete(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of update method, of class BaseJpaDao.
//     */
//    public void testUpdate_GenericType() {
//        System.out.println("update");
//        Object entity = null;
//        BaseJpaDao instance = new BaseJpaDaoImpl();
//        instance.update(entity);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of update method, of class BaseJpaDao.
//     */
//    public void testUpdate_Collection() {
//        System.out.println("update");
//        BaseJpaDao instance = new BaseJpaDaoImpl();
//        instance.update(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of retrieve method, of class BaseJpaDao.
//     */
//    public void testRetrieve() {
//        System.out.println("retrieve");
//        Object id = null;
//        BaseJpaDao instance = new BaseJpaDaoImpl();
//        Object expResult = null;
//        Object result = instance.retrieve(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    public class BaseJpaDaoImpl extends BaseJpaDao {

        public Collection retrieveAll() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public long count() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
