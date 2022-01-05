package airplane.service.logic;

import airplane.dao.DaoException;
import airplane.dao.postgresql.BrigadeDaoImpl;
import airplane.dao.postgresql.FlightDaoImpl;
import airplane.entity.Brigade;
import airplane.entity.Flight;
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
public class FlightServiceImplTest {
    @Mock
    private BrigadeDaoImpl brigadeDao;
    @Mock
    private FlightDaoImpl flightDao;
    @InjectMocks
    private FlightServiceImpl flightService;

    @Test
    public void testFindById() throws ServiceException, DaoException {
        flightService.findById(1L);
        verify(flightDao).read(1L); // типичный тест
    }

    @Test
    public void testFindAll() throws ServiceException, DaoException {
        flightService.findAll();
        verify(flightDao).readAll(); // типичный тест
    }

    @Test(expected = ServiceException.class)
    public void testReadFlightByIdNotFound() throws ServiceException, DaoException {
        when(flightDao.read(1L)).thenThrow(new DaoException());
        flightService.findById(1L); // типичный тест
    }

    @Test
    public void testCreate() throws ServiceException, DaoException {
        Flight flight = mock(Flight.class);
        ArrayList<Flight> flights = (ArrayList<Flight>) spy(ArrayList.class);
        flights.add(flight);
        flightService.create(flights);
        verify(flightDao).save(flights); // типичный тест
    }

    @Test
    public void testStoreFlightError() {
        String msg = "There are no users to store";
        ArrayList<Flight> flights = (ArrayList<Flight>) spy(ArrayList.class);
        try {
            flightService.create(flights);
        } catch (ServiceException e) {
            assertEquals(msg, e.getMessage()); // типичный тест
        }
    }

    @Test
    public void testStoreFlightWithNonExistentBrigade() throws DaoException {
        String msg = "No brigade was found";
        ArrayList<Flight> flights = (ArrayList<Flight>) spy(ArrayList.class);
        Brigade brigade = mock(Brigade.class);
        Flight flight = mock(Flight.class);
        flights.add(flight);
        doReturn(brigade).when(flight).getBrigade();
        doReturn(1L).when(brigade).getId();
        doReturn(null).when(brigadeDao).read(1L);
        try {
            flightService.create(flights);
        } catch (ServiceException e) {
            assertEquals(msg, e.getMessage()); // проверяем выброс исключения с соответствующим сообщением, когда, при обращении к базе данных
            // с целью проверки (получения) наличия бригады в базе, объект найден не будет (вернет null)
        }
    }

    @Test
    public void testEdit() throws DaoException, ServiceException {
        // создаем заглушку (mock) для объекта типа Flight
        Flight flight = mock(Flight.class);
        // создаем заглушку (mock) для объекта типа Brigade
        Brigade brigade = mock(Brigade.class);
        // при вызове метода getId() на объекте flight возвращать id = 1L
        doReturn(1L).when(flight).getId();
        // при вызове метода read() c id = 1L возвращать объект person
        doReturn(flight).when(flightDao).read(1L);
        // при вызове метода getBrigade() возвращать вышесозданный объект типа Brigade
        doReturn(brigade).when(flight).getBrigade();
        // при вызове метода getId() на объекте brigade возвращать id = 1L
        doReturn(1L).when(brigade).getId();
        // при вызове метода read() c id = 1L возвращать объект brigade
        doReturn(brigade).when(brigadeDao).read(1L);
        flightService.edit(flight);
        verify(flightDao).update(flight); // убеждаемся, что при вызове edit() на flightDao будет вызван update()
    }

    @Test
    public void testUpdateFlightError() throws DaoException {
        String msg = "No airplane.entity with this identifier was found";
        Flight flight = mock(Flight.class);
        doReturn(1L).when(flight).getId();
        doReturn(null).when(flightDao).read(1L);
        try {
            flightService.edit(flight);
        } catch (ServiceException e) {
            assertEquals(msg, e.getMessage()); // типичный тест
        }
    }

    @Test
    public void testUpdateFlightWithNonExistentBrigade() throws DaoException {
        String msg = "No brigade with this identifier was found";
        Flight flight = mock(Flight.class);
        Brigade brigade = mock(Brigade.class);
        doReturn(1L).when(flight).getId();
        doReturn(flight).when(flightDao).read(1L);
        doReturn(brigade).when(flight).getBrigade();
        doReturn(1L).when(brigade).getId();
        doReturn(null).when(brigadeDao).read(1L);
        try {
            flightService.edit(flight);
        } catch (ServiceException e) {
            assertEquals(msg, e.getMessage()); // проверяем выброс исключения с соответствующим сообщением, когда, при обращении к базе данных
            // с целью обновления рейса с новой бригадой в базе, объект бригады в базе найден не будет
        }
    }

    @Test
    public void testDelete() throws DaoException, ServiceException {
        flightService.delete(1L);
        verify(flightDao).delete(1L); // типичный тест
    }

    @Test
    public void testDeleteFlightError() throws DaoException {
        doThrow(DaoException.class).when(flightDao).delete(anyLong());
        try {
            flightService.delete(1L);
        } catch (ServiceException e) {
        }
        verify(flightDao).delete(1L); // типичный тест
    }
}
