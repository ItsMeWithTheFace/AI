import com.orbischallenge.ctz.Constants;
import com.orbischallenge.ctz.objects.EnemyUnit;
import com.orbischallenge.ctz.objects.FriendlyUnit;
import com.orbischallenge.ctz.objects.World;
import com.orbischallenge.ctz.objects.enums.Direction;
import com.orbischallenge.game.engine.Point;
// import java.lang.Math;

public class PlayerAI {
  public EnemyUnit enemyToKill;

  public PlayerAI() {
    // Any initialization code goes here.
  }

  /**
   * This method will get called every turn.
   *
   * @param world The latest state of the world.
   * @param enemyUnits An array of all 4 units on the enemy team. Their order won't change.
   * @param friendlyUnits An array of all 4 units on your team. Their order won't change.
   */
  public void doMove(World world, EnemyUnit[] enemyUnits, FriendlyUnit[] friendlyUnits) {
    // Your glorious AI code goes here.
    //Here it goes
    // Alpha moves to control point nearest to it, others follow
    if (world.getNearestControlPoint(friendlyUnits[0].getPosition()).getControllingTeam() != Team.AMBER) {
      friendlyUnits[0].move(world.getNearestControlPoint(friendlyUnits[0].getPosition()).getPosition());
      for(int i = 0; i <= 3; i++){
        friendlyUnits[i].move(friendlyUnits[0].getPosition());
      }
    }
    else {
      // check for any enemies nearby any friendly units. if so, gang up on them
      for (int i = 0; i <= 3; i++) {
        for (int j = 0; j<=3; j++) {
          if(friendlyUnits[i].checkShotAgainstEnemy(enemyUnits[j]) == ShotResult.CAN_HIT_ENEMY) {
            enemyToKill = enemyUnits[j];
            break;
          }
        }
        // if there exists an enemy to kill, get it's position and go after/kill it
        if (enemyToKill) {
          if (friendlyUnits[i].checkShotAgainstEnemy(enemyToKill) == ShotResult.CAN_HIT_ENEMY)
            friendlyUnits[i].shootAt(enemyToKill);
          else
            friendlyUnits[i].move(enemyToKill.getPosition());
        }
      }

    }

  }
}