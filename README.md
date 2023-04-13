# CS210 Personal Project
## *LOL Teams Managing Tool*

## Introduction

**The main purpose of *LOL Teams Managing Tool* is to act as a system for recording and managing team and player 
information for a popular video game, League of Legends (LOL).** While its intended purpose is to record and display 
information for professional League of Legends e-sports, it can also be used for managing players and teams in casual 
play. Due to the nature of e-sports and quite simply, professional sports in general, it can be difficult to keep track 
of the teams in a league as well as their current roster of players. This application helps LOL e-sports fans to keep 
track of and easily visualize the players on each team in a league. This can also be useful for small tournament 
organizers to manage teams and team rosters for their tournaments. The application lets you enter statistics and 
information about players, view the list of teams in a region/league as well as the list of players on each team. 

Possible features could include a search bar that allows users to search for specific players by their name, and 
additionally, filtering players by categories (role they play, the region they play in, etc.) 


## Background information
### Why did I choose to do this specific project?

My idea for this project stems from my frustration with many pre-existing League of Legends e-sports wikis. Instead of 
being able to view all the current teams in a league along with their roster for the current season, many popular wikis 
are structured so that users must click on a team in order to view their roster. Furthermore, for those that do include 
the teams' current rosters, they are formatted in a table like a spreadsheet, making it impossible to select a player 
from the same page and view their statistics and information. I myself, like many of my friends, are huge fans of LOL 
e-sports, and we would often discuss and debate our predictions and rankings for each team, especially at the beginning 
of each competitive season. However, without being able to view the players on each roster at a glance, as well as being 
able to check their stats from the same application, it takes much more effort to be able to evaluate each team 
holistically. This is the main reason why I decided on *LOL Teams Managing Tool* as my CS210 personal project. 


## Additional information
If it is still not quite clear, for this application, X could be a player, and Y would be a team with a list of players, 
and X could also be a team, while Y would be a competitive region/league.



## User Stories:
- As a user, I want to create a player with specific stats and information
- As a user, I want to select a player and be able to change the stats and information of the player
- As a user, I want to add a player to a team
- As a user, I want to create a team and add it to a list of teams (competitive regions/leagues)
- As a user, I want to be able to select a competitive region/league and see a list of teams for that region as well as
the players on that team.
    - As a user, I want to be able to select a team and list all the players on that team. 
- As a user, I want to be able to save all players, teams and leagues to a file from the main application menu.
- As a user, when I select the quit option from the main application menu, I want to be reminded to save all players, 
teams and leagues and have the option to do so or not.
- As a user, when I start the LOL teams manager application, I want to be given the option to load all players, teams, 
and leagues from file.



## References:

I used CPSC210 JsonSerializationDemo project as reference for my JsonReader and JsonWriter classes. It can be found at:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.

I used Java Oracle's ListDemo project as reference for my AddTeamListener and AddLeagueListener class. It can be found
at: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing
/examples/components/ListDemoProject/src/components/ListDemo.java.

## Instructions for Grader: 

- You can generate the first required action related to adding Xs to a Y (Adding Teams to a league) by clicking on All 
Leagues Menu button, then clicking on Adding a League after entering a league name in the field, and then clicking on 
League Info button, then entering the name of the Team to add, then clicking the Add Team button. 
- You can generate the second required action related to adding Xs to a Y (Removing Teams from a league) after adding
teams in a league by accessing the League Menu, then selecting a team and then clicking remove.
- You can locate my visual component in the Main Menu.
- You can save the state of my application by clicking on the menu bar, then selecting the save option, and then
following the instructions of the pop-up to save.
- You can reload the state of my application by clicking on the menu bar, then selecting the load option, and then 
following the instructions of the pop-up to load. 

## Phase 4: Task 2
**Representative samples of the events that occur when my program runs and prints out to the console when the user
quits the application:**

Event Log:

Wed Apr 12 15:16:03 PDT 2023

Added team VIT to LEC

Wed Apr 12 15:16:06 PDT 2023

Added team 100T to LEC

Wed Apr 12 15:16:09 PDT 2023

Added team TSM to LEC

Wed Apr 12 15:16:14 PDT 2023

Removed team 100T from LEC

Wed Apr 12 15:16:23 PDT 2023

Added team C9 to LEC


Where, LEC represents a league (Y), and teams (X) are added and removed from the league.

## Phase 4: Task 3

One majoring refactoring I would change is to improve the Single Responsibility Principle by splitting up the 
TeamsManagerConsole and TeamsManagerGUI classes. Currently, both the TeamsManagerConsole class and the TeamsManagerGUI 
class manages all the menus as well as actions related to those menus, thereby breaking the single responsibility 
principle. 

One way I would refactor the TeamsManagerConsole class is to split it into 2 classes, one for managing user inputs 
from the console and the other for displaying menus to the console. One way I would refactor the TeamsManagerGUI class
is to add new classes or subclasses that implement ActionListener, ListSelectionListener, and WindowListener instead of 
the GUI class being the sole listener. 