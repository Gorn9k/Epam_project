package airplane.service.logic;

import airplane.dao.DaoException;
import airplane.dao.postgresql.BrigadeDaoImpl;
import airplane.dao.postgresql.PersonDaoImpl;
import airplane.entity.Brigade;
import airplane.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import airplane.service.ServiceException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.spy;

// большинство ситуаций описаны в тест классе PersonServiceImplTest, поэтому некоторые тесты будут помечены как "типичный тест", описание
// которого можно посмотреть в вышеуказанном тесте

@RunWith(MockitoJUnitRunner.class)
public class BrigadeServiceImplTest {
    @Mock
    private PersonDaoImpl personDao;
    @Mock
    private BrigadeDaoImpl brigadeDao;
    @InjectMocks
    private BrigadeServiceImpl brigadeService;

    @Test
    public void testFindById() throws ServiceException, DaoException {
        brigadeService.findById(1L);
        verify(brigadeDao).read(1L); // типичный тест
    }

    @Test
    public void testFindAll() throws ServiceException, DaoException {
        brigadeService.findAll();
        verify(brigadeDao).readAll(); // типичный тест
    }

    @Test(expected = ServiceException.class)
    public void testReadBrigadeByIdNotFound() throws ServiceException, DaoException {
        when(brigadeDao.read(1L)).thenThrow(new DaoException());
        brigadeService.findById(1L); // типичный тест
    }

    @Test
    public void testCreate() throws ServiceException, DaoException {
        Brigade brigade = mock(Brigade.class);
        ArrayList<Brigade> brigades = (ArrayList<Brigade>) spy(ArrayList.class);
        brigades.add(brigade);
        brigadeService.create(brigades);
        verify(brigadeDao).save(brigades); // типичный тест
    }

    @Test
    public void testStoreBrigadeError() {
        String msg = "There are no users to store";
        ArrayList<Brigade> brigades = (ArrayList<Brigade>) spy(ArrayList.class);
        try {
            brigadeService.create(brigades);
        } catch (ServiceException e) {
            assertEquals(msg, e.getMessage()); // типичный тест
        }
    }

    @Test
    public void testStoreBrigadeWithNonExistentPerson() throws DaoException {
        String msg = "No person was found";
        ArrayList<Brigade> brigades = (ArrayList<Brigade>) spy(ArrayList.class);
        ArrayList<Person> persons = (ArrayList<Person>) spy(ArrayList.class);
        Brigade brigade = mock(Brigade.class);
        Person person = mock(Person.class);
        persons.add(person);
        brigade.setPersons(persons);
        brigades.add(brigade);
        doReturn(persons).when(brigade).getPersons();
        doReturn(false).when(personDao).isPersonExist(person);
        try {
            brigadeService.create(brigades);
        } catch (ServiceException e) {
            assertEquals(msg, e.getMessage()); // проверяем выброс исключения с соответствующим сообщением, когда, при обращении к базе данных
            // с целью проверки (получения) наличия людей для бригады в базе, объект найден не будет (вернет null)
        }
    }

    @Test
    public void testEdit() throws DaoException, ServiceException {
        // создаем заглушку (spy) для объекта типа Flight
        Brigade brigade = spy(Brigade.class);
        // создаем заглушку (mock) для объекта типа Flight
        Person person = mock(Person.class);
        // создаем заглушку (spy) для списка с элементами типа Person с возможностью использования методов списка
        ArrayList<Person> persons = (ArrayList<Person>) spy(ArrayList.class);
        // при вызове метода getId() на объекте brigade возвращать id = 1L
        doReturn(1L).when(brigade).getId();
        // при вызове метода read() c id = 1L возвращать объект brigade
        doReturn(brigade).when(brigadeDao).read(1L);
        // при вызове метода getPersons() возвращать вышесозданный список persons
        doReturn(persons).when(brigade).getPersons();
        // при вызове метода get() с любым индексом (метод anyInt() - маркер того, что в параметр может быть получено любое значение типа int)
        // на объекте списка persons возвращать вышесозданный объект person
        doReturn(person).when(persons).get(anyInt());
        brigadeService.edit(brigade);
        verify(brigadeDao).update(brigade); // убеждаемся, что при вызове edit() на brigadeDao будет вызван update()
    }

    @Test
    public void testUpdateBrigadeError() throws DaoException {
        String msg = "No airplane.entity with this identifier was found";
        Brigade brigade = mock(Brigade.class);
        doReturn(1L).when(brigade).getId();
        doReturn(null).when(brigadeDao).read(1L);
        try {
            brigadeService.edit(brigade);
        } catch (ServiceException e) {
            assertEquals(msg, e.getMessage()); // типичный тест
        }
    }

    @Test
    public void testUpdateBrigadeWithNonExistentPerson() throws DaoException {
        String msg = "No person was found";
        ArrayList<Person> persons = (ArrayList<Person>) spy(ArrayList.class);
        ArrayList<Person> personsFromDB = (ArrayList<Person>) spy(ArrayList.class);
        Brigade brigade = mock(Brigade.class);
        Brigade brigadeFromDB = mock(Brigade.class);
        Person person = mock(Person.class);
        Person personFromDB = mock(Person.class);
        persons.add(person);
        brigade.setPersons(persons);
        personsFromDB.add(personFromDB);
        brigadeFromDB.setPersons(personsFromDB);
        doReturn(1L).when(brigade).getId();
        doReturn(brigadeFromDB).when(brigadeDao).read(1L);
        doReturn(personsFromDB).when(brigadeFromDB).getPersons();
        doReturn(persons).when(brigade).getPersons();
        doReturn(false).when(personDao).isPersonExist(person);
        try {
            brigadeService.edit(brigade);
        } catch (ServiceException e) {
            assertEquals(msg, e.getMessage()); // проверяем выброс исключения с соответствующим сообщением, когда, при обращении к базе данных
            // с целью обновления бригады в базе, людей, которые вошли бы в нее, найдено не будет
        }
    }

    @Test
    public void testDelete() throws DaoException, ServiceException {
        brigadeService.delete(1L);
        verify(brigadeDao).delete(1L); // типичный тест
    }

    @Test
    public void testDeleteBrigadeError() throws DaoException {
        doThrow(DaoException.class).when(brigadeDao).delete(anyLong());
        try {
            brigadeService.delete(1L);
        } catch (ServiceException e) {
        }
        verify(brigadeDao).delete(1L); // типичный тест
    }
}