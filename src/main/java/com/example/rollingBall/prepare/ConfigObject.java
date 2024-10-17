package com.example.rollingBall.prepare;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.arena.SpikyBall;
import com.example.rollingBall.entities.Level;
import com.example.rollingBall.entities.Terrain;
import javafx.geometry.Point3D;

public class ConfigObject {

    public double time_limit;
    public double freeze_time;
    public double extra_time;
    public double max_lifepoints;
    public double max_ball_speed;
    public double spiky_ball_speed;
    public double spiky_ball_speed_increase;
    public double ball_damp;
    public double arena_damp;
    public double max_acceleration;
    public double points_coin;
    public double points_bad_hole;
    public double points_hole;
    public double bounce_factor;

    public ConfigObject() {
        time_limit = Const.TIME_LIMIT;
        freeze_time = Const.TIME_FREEZE;
        extra_time = Const.EXTRA_TIME;
        max_lifepoints = Const.MAX_LIFE_POINTS;
        max_ball_speed = Const.MAX_BALL_SPEED;
        spiky_ball_speed = Const.SPIKY_BALL_SPEED;
        spiky_ball_speed_increase = Const.SPIKY_BALL_SPEED_INCREASE;
        ball_damp = Const.BALL_DAMP;
        arena_damp = Const.ARENA_DAMP;
        max_acceleration = Const.MAX_ACCELERATION;
        points_coin = Const.POINTS_COIN;
        points_bad_hole = Const.POINTS_BAD_HOLE;
        points_hole = Const.POINTS_HOLE;
        bounce_factor = Const.BOUNCEWALL_FACTOR;
    }

    public void updateConstants() {
         Const.TIME_LIMIT = time_limit;
         Const.TIME_FREEZE = freeze_time;
         Const.EXTRA_TIME = (int)extra_time;
         Const.MAX_LIFE_POINTS = (int)max_lifepoints;
         Const.MAX_BALL_SPEED = (int)max_ball_speed;
         Const.SPIKY_BALL_SPEED = (int)spiky_ball_speed;
         Const.SPIKY_BALL_SPEED_INCREASE = spiky_ball_speed_increase;
         Const.BALL_DAMP = ball_damp;
         Const.ARENA_DAMP = arena_damp;
         Const.MAX_ACCELERATION = max_acceleration;
         Const.POINTS_COIN = (int)points_coin;
         Const.POINTS_BAD_HOLE = (int)points_bad_hole;
         Const.POINTS_HOLE = (int)points_hole;
         Const.BOUNCEWALL_FACTOR = (int)bounce_factor;

        for (Terrain t: Main.getTerrains()) {
            t.setRemainingTime(time_limit);
            for (Level level: t.getLevels()){
                for (SpikyBall sb: level.getSpikyBalls()){
                    double x = sb.getSpeed().getX();
                    double z = sb.getSpeed().getZ();
                    double factor = 1;
                    if ( Math.abs(x) > Math.abs(z)){
                        factor = spiky_ball_speed / x;
                    } else {
                        factor = spiky_ball_speed / z;
                    }
                    x *= factor;
                    z *= factor;
                    sb.setSpeed(new Point3D(x, 0, z));
                }
            }
        }
    }
}
