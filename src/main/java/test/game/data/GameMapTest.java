package test.game.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import game.data.GameMap;
import game.data.GameMapJsonDeserializer;
import game.data.boardelements.BoardElementTypes;
import game.data.boardelements.CheckPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMapTest {

    private GameMap gameMap;

    /**
     *  Map 2x2:
     *
     *  E  E
     *  →  ⤴
     *
     *  CheckPoints on 0,0 and 1,0
     *  ConveyorBelts speed = 1
     *
     */
    private static final String TEST_MAP_JSON = """
            {
              "gameMap": [
                [
                  [
                    {
                      "type": "Empty",
                      "isOnBoard": "TEST"
                    },
                    {
                      "count": 1,
                      "type": "CheckPoint",
                      "isOnBoard": "TEST"
                    }
                  ],
                  [
                    {
                      "type": "Empty",
                      "isOnBoard": "TEST"
                    }
                  ]
                ],
                [
                  [
                    {
                      "speed": 1,
                      "orientations": [
                        "bottom",
                        "top"
                      ],
                      "type": "ConveyorBelt",
                      "isOnBoard": "TEST"
                    },
                    {
                      "count": 2,
                      "type": "CheckPoint",
                      "isOnBoard": "TEST"
                    }
                  ],
                  [
                    {
                      "speed": 1,
                      "orientations": [
                        "left",
                        "top"
                      ],
                      "type": "ConveyorBelt",
                      "isOnBoard": "TEST"
                    }
                  ]
                ]
              ]
            }
            """;

    @BeforeEach
    public void beforeEach() {
        //load test map
        //code copied from GameMapHandler.readJsonMap
        JsonParser parser = new JsonParser();
        JsonElement jsontree = parser.parse(TEST_MAP_JSON);
        JsonObject jsonmap = jsontree.getAsJsonObject();
        JsonArray jarray = jsonmap.getAsJsonArray("gameMap");
        this.gameMap = GameMapJsonDeserializer.deserializeArray(jarray);
    }

    @Test
    public void gameMap_jsonLoadedSuccessfully() {
        //verify CheckPoints
        assertEquals(BoardElementTypes.CheckPoint, gameMap.getMap().get(0).get(0).get(1).getType(), "Expected CheckPoint on 0,0");
        assertEquals(1, ((CheckPoint) gameMap.getMap().get(0).get(0).get(1)).getCount(), "Expected CheckPoint 1 on 0,0");

        assertEquals(BoardElementTypes.CheckPoint, gameMap.getMap().get(1).get(0).get(1).getType(), "Expected CheckPoint on 1,0");
        assertEquals(2, ((CheckPoint) gameMap.getMap().get(1).get(0).get(1)).getCount(), "Expected CheckPoint 2 on 1,0");

        //verify ConveyorBelts
        assertEquals(BoardElementTypes.ConveyorBelt, gameMap.getMap().get(1).get(0).get(0).getType(), "Expected ConveyorBelt on 1,0");
        assertEquals(BoardElementTypes.ConveyorBelt, gameMap.getMap().get(1).get(1).get(0).getType(), "Expected ConveyorBelt on 1,1");
    }

    @Test
    public void rotateCheckPoints_checkPointNotOnConveyorBeltDidntMove() {
        gameMap.rotateCheckPoints();
        assertEquals(BoardElementTypes.CheckPoint, gameMap.getMap().get(0).get(0).get(1).getType(), "Expected CheckPoint on 0,0");
        assertEquals(1, ((CheckPoint) gameMap.getMap().get(0).get(0).get(1)).getCount(), "Expected CheckPoint 1 on 0,0");
    }

    @Test
    public void rotateCheckPoints_checkPointOnConveyorBeltMoved() {
        gameMap.rotateCheckPoints();
        assertEquals(BoardElementTypes.CheckPoint, gameMap.getMap().get(1).get(1).get(1).getType(), "Expected CheckPoint on 1,1");
        assertEquals(2, ((CheckPoint) gameMap.getMap().get(1).get(1).get(1)).getCount(), "Expected CheckPoint 2 moved to 1,1");

        assertEquals(1, gameMap.getMap().get(1).get(0).size(), "Expected only one board element on 1,0");
        assertEquals(BoardElementTypes.ConveyorBelt, gameMap.getMap().get(1).get(0).get(0).getType(), "Expected only ConveyorBelt on 1,0");
    }

}
