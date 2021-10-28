package dao;

import entity.Brigade;

import java.util.List;

public interface BrigadeDao extends Dao<Brigade>{
    List<Brigade> readAll();
}
