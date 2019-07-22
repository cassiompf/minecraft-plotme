package gmail.fopypvp174.cmterrenos.database.dao;

import gmail.fopypvp174.cmterrenos.entities.HouseEntity;

import java.util.Collection;

public interface DatabaseDAO {

    void saveTerrains(Collection<HouseEntity> houses);

    void startTerrains();

    void setTerrain(HouseEntity house);

}
