import com.hpw.LocalMapperFactory;
import com.hpw.bean.GameList;
import com.hpw.bean.UserEntranceUnlockCost;
import com.hpw.dao.impl.GameListDao;
import com.hpw.dao.impl.UserEntranceUnlockCostDao;
import com.hpw.myenum.convert.EntranceStateEnum;
import com.hpw.myenum.convert.GameIdEnum;
import com.hpw.myenum.convert.SizeTypeEnum;
import com.hpw.myenum.convert.TagTypeEnum;

public class MapperTest {
    public static void main(String[] args) throws Throwable {
        LocalMapperFactory.getInstance().init("local");

        // getUserEntranceUnlockCost();
        // insertUserEntranceUnlockCost();
        // getUserEntranceUnlockCost();

        getGameList();
        // insertGameList();
        getGameList();


    }

    public static void getUserEntranceUnlockCost() {
        System.out.println(UserEntranceUnlockCostDao.getInstance().getUserEntranceUnlockCost());
    }

    public static void insertUserEntranceUnlockCost() {
        UserEntranceUnlockCost userEntranceUnlockCost = new UserEntranceUnlockCost();
        userEntranceUnlockCost.setUserId(910001L);
        userEntranceUnlockCost.setEntranceId(2);
        userEntranceUnlockCost.setUnlockCost("[1,2,3]");
        boolean b = UserEntranceUnlockCostDao.getInstance().addUserEntranceUnlockCost(userEntranceUnlockCost);
        if (b) {
            System.out.println("插入成功");
        }
    }

    public static void  getGameList() {
        System.out.println(GameListDao.getInstance().getGameList());
    }

    public static void insertGameList() {
        GameList gameList = new GameList();
        gameList.setGameId(GameIdEnum.GAME);
        gameList.setTagType(TagTypeEnum.NEW);
        gameList.setContainerId(1);
        gameList.setSizeType(SizeTypeEnum.BIG);
        gameList.setIndex(1);
        gameList.setState(EntranceStateEnum.ACTIVE);
        GameListDao.getInstance().addGameList(gameList);
    }

}
