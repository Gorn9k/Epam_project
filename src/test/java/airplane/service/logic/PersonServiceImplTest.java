package airplane.service.logic;

import airplane.dao.DaoException;
import airplane.dao.postgresql.PersonDaoImpl;
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

// позволяет автоматизировать проверку использования средств Mockito, а также, ссылаться и рекомендовать варианты использования этих средств
// также позволяет использовать в тестах аннотации Mockito и его средства
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest  {
    // - creates a mock ("заглушка", возвращающая из методов оригинала значения по-умолчанию: void, default-ы для примитивов,
    // пустые коллекции и null для всех остальных объектов)
    @Mock
    private PersonDaoImpl personDao;
    // позволяет создать объект (внедряя вместо реальных зависимостей их эмуляции) на основании заглушек, помеченных аннотацией @Mock
    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    public void testFindById() throws ServiceException, DaoException {
        personService.findById(1L);
        verify(personDao).read(1L); // убеждаемся, что при вызове findById() на personDao будет вызван read()
    }

    @Test
    public void testFindAll() throws ServiceException, DaoException {
        personService.findAll();
        verify(personDao).readAll();// убеждаемся, что при вызове findAll() на personDao будет вызван readAll()
    }

    @Test(expected = ServiceException.class)
    public void testReadPersonByIdNotFound() throws ServiceException, DaoException {
        when(personDao.read(1L)).thenThrow(new DaoException());
        personService.findById(1L); // проверка выброса исключения при чтении из базы данных без id
    }

    @Test
    public void testCreate() throws ServiceException, DaoException {
        // создаем заглушку (mock) для объекта типа Person
        Person person = mock(Person.class);
        // создаем заглушку (spy) для списка с элементами типа Person с возможностью использования методов списка
        ArrayList<Person> persons = (ArrayList<Person>) spy(ArrayList.class);
        persons.add(person);
        personService.create(persons);
        verify(personDao).save(persons); // убеждаемся, что при вызове метода create() на personDao будет вызван save()
    }

    @Test
    public void testStorePersonError() {
        String msg = "There are no users to store";
        ArrayList<Person> persons = (ArrayList<Person>) spy(ArrayList.class);
        try {
            personService.create(persons);
        } catch (ServiceException e) {
            assertEquals(msg, e.getMessage()); // проверяем выброс исключения с соответствующим сообщением при передаче пустого списка
        }
    }

    @Test
    public void testEdit() throws DaoException, ServiceException {
        Person person = mock(Person.class);
        // при вызове метода getId() на объекте person возвращать id = 1L
        doReturn(1L).when(person).getId();
        // при вызове метода read() c id = 1L возвращать объект person
        doReturn(person).when(personDao).read(1L);
        personService.edit(person);
        verify(personDao).update(person); // убеждаемся, что при вызове метода edit() на personDao будет вызван метод update()
    }

    @Test
    public void testUpdateCustomerOnNonExistentId() throws DaoException {
        String msg = "No airplane.entity with this identifier was found";
        Person person = mock(Person.class);
        doReturn(1L).when(person).getId();
        doReturn(null).when(personDao).read(1L);
        try {
            personService.edit(person);
        } catch (ServiceException e) {
            assertEquals(msg, e.getMessage()); // проверяем выброс исключения с соответствующим сообщением, когда, при обращении к базе данных
            // с целью получения объекта по id, объект найден не будет (вернет null)
        }
    }

    @Test
    public void testDelete() throws DaoException, ServiceException {
        personService.delete(1L);
        verify(personDao).delete(1L); // убеждаемся, что при вызове метода delete() на personDao будет вызван метод delete()
    }

    @Test
    public void testDeleteCustomerError() throws DaoException {
        // при удалении объекта по любому id (метод anyLong() - маркер того, что в параметр может быть получено любое значение типа long)
        // будет выбращено исключение (проверка выброса исключения в методе)
        doThrow(DaoException.class).when(personDao).delete(anyLong());
        try {
            personService.delete(1L);
        } catch (ServiceException e) {}
        verify(personDao).delete(1L);
    }
}