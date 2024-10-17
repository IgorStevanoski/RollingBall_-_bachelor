# RollingBall
RollingBall is a complete 3D video game developed using JavaFX library, which simulates ball motion and elastic collision using 3D graphics. 
Main goal of the game is to bring the ball to the right hole and gather as much points as possible while doing so. Project consists of: game with multiple levels, leaderboard, settings, and editor. 

## Main menu
![Screenshot (188)](https://github.com/user-attachments/assets/86ef77a4-5e80-4a3f-8a15-00aae41bd2f3)

## New Game
Before starting a new game, player can choose on which terrain he will play and may select one of three balls which have different speed and points multiplier. Before starting the game on a terrain, player can see a preview of the first level of the terrain.  

![Screenshot (189)](https://github.com/user-attachments/assets/30740c26-ed6d-467e-ab72-7148946944ec)

The main goal of the game is to bring the ball to the right hole by tilting the terrain. There can be multiple balls and holes on some levels, all balls must be put in right ones for a player to proceed to the next level. Picking up coins and putting balls in right holes grants player points. Game consists of positive and negative elements. 

![Screenshot (198)](https://github.com/user-attachments/assets/2e5b8a10-6e68-430a-bc40-9f6381291479)

### Game Interface
Remaining player lives are shown in the top left corner, total points in the top right corner, remaining time on the bottom and acceleration vector in the bottom left.

![Untitled-1](https://github.com/user-attachments/assets/eab8d132-108d-4fa0-b008-d466fa57da13)

At any time player can pause the game. While game is paused player can continue, start a new game or go to main menu then continue later at any time. After finishing the level, player can choose to go to the next level. Levels cycle repeatedly, making the game endless. Game is limited with player lives and time remaining.

<img src="https://github.com/user-attachments/assets/5fbfc4dd-2571-4887-a313-aba4cb88c99e" alt="" width="300"/>
<img src="https://github.com/user-attachments/assets/7690daa8-d7ae-4fc8-a0a3-ebac801ebe9f" alt="" width="300"/>

After all lives are lost or time is passed, the game is finished. Player can then enter their score on leaderboard.

![Screenshot (197)2](https://github.com/user-attachments/assets/da442264-029c-4da9-9012-ec9f6b02f80e)

## Leaderboard
For each existing terrain, there is a leaderboard which shows all players names and scores.

![Screenshot (190)](https://github.com/user-attachments/assets/5e0a8b33-8a72-4328-a18f-9b5b855efc22)

## Settings 
Player can change audio and video settings.

![Screenshot (191)](https://github.com/user-attachments/assets/2398fb87-d793-410d-b132-05e890fe8375)

## Choose player
There can be multiple users. For each user settings are saved separately.

![Screenshot (196)2](https://github.com/user-attachments/assets/0d611627-0f7a-44bc-b95e-aca8cd6b55bc)

## Editor
Before editing a terrain, player can choose an existing terrain to edit or create a new one.

![Screenshot (192)](https://github.com/user-attachments/assets/c2201bf7-230d-46fc-a24c-86f1271e9b0d)

While in editor, player can add any number of levels for the terrain and edit them separately, change terrain name or delete it entirely. On the right side of the screen is a palette with all terrain elements. A terrain can have any number of elements. Properties of elements can be changed using window on the bottom of the screen. Level must contain appropriate hole for each ball added, and vice versa. At any time player can change camera view or show grid.

![Screenshot (193)](https://github.com/user-attachments/assets/5265277d-8098-478b-b323-cf9c41dd2e21)

## Help
All game details are explained in the Help menu.

![Screenshot (194)](https://github.com/user-attachments/assets/e228ce1c-ee78-42e8-a758-2302be49c0cf)

## Commands
All game and editor commands are shown in the Commands menu.

![Screenshot (195)](https://github.com/user-attachments/assets/d111f502-b5ba-4611-b524-773a9ee2864f)


### Note:
After starting the game (RollingBall.jar) for the first time, folders data and config will be created for storing game data. 



