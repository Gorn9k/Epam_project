package dao;

import entity.Flight;

import java.util.ArrayList;
import java.util.List;

public interface FlightDao extends Dao<Flight>{
    List<Flight> readAll();
}
