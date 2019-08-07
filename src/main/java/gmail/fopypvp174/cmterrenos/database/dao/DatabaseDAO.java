package gmail.fopypvp174.cmterrenos.database.dao;

import gmail.fopypvp174.cmterrenos.entities.HouseEntity;

import java.util.Collection;
import java.util.Set;

public interface DatabaseDAO {

    void setTerrain(HouseEntity house);

    void saveTerrains(Collection<HouseEntity> houses);

    void deleteTerrain(HouseEntity house);

    Set<HouseEntity> getTerrains();

}
