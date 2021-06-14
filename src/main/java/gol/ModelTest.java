package gol;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ModelTest {

    Model m;

    @Before
    public void setUp() throws Exception {
        m = new Model(10);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void mapXCoordinateEqualsSize() {
        int arraySizeX = m.setupBoard().length;
        assertEquals(m.getCols(),arraySizeX);
    }

    @Test
    public void mapYCoordinateEqualsSize() {
        int arraySizeY = m.setupBoard()[0].length;
        assertEquals(m.getRows(),arraySizeY);
    }

    @Test
    public void mapYCoordinateEqualsXCoordinate() {
        boolean same = false;
        int arraySizeX = m.setupBoard().length;
        int arraySizeY = m.setupBoard()[0].length;
        if(arraySizeX==arraySizeY)
            same = true;
        assertTrue(same);
    }

    public Field[][] setupMap(){
        Field[][] map = new Field[m.getCols()][m.getRows()];
        for(int i = 0; i< m.getCols();i++){
            for(int j = 0; j< m.getRows();j++){
                map[i][j] = new Field(i,j,false,m);
            }
        }
        return map;
    }
}