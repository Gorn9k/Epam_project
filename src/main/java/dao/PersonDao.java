package dao;

import entity.Person;

import java.util.ArrayList;
import java.util.List;

public interface PersonDao extends Dao<Person> {
    List<Person> readAll();
}
