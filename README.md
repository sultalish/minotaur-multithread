# minotaur-multithread

# Problem 1: Minotaur’s Birthday Party (50 points)

The Minotaur invited N guests to his birthday party. When the guests arrived, he made the following announcement.

The guests may enter his labyrinth, one at a time and only when he invites them to do so. At the end of the labyrinth, the Minotaur placed a birthday cupcake on a plate. When a guest finds a way out of the labyrinth, he or she may decide to eat the birthday cupcake or leave it. If the cupcake is eaten by the previous guest, the next guest will find the cupcake plate empty and may request another cupcake by asking the Minotaur’s servants. When the servants bring a new cupcake the guest may decide to eat it or leave it on the plate.

The Minotaur’s only request for each guest is to not talk to the other guests about her or his visit to the labyrinth after the game has started. The guests are allowed to come up with a strategy prior to the beginning of the game. There are many birthday cupcakes, so the Minotaur may pick the same guests multiple times and ask them to enter the labyrinth. Before the party is over, the Minotaur wants to know if all of his guests have had the chance to enter his labyrinth. To do so, the guests must announce that they have all visited the labyrinth at least once.

Now the guests must come up with a strategy to let the Minotaur know that every guest entered the Minotaur’s labyrinth. It is known that there is already a birthday cupcake left at the labyrinth’s exit at the start of the game. How would the guests do this and not disappoint his generous and a bit temperamental host?

Create a program to simulate the winning strategy (protocol) where each guest is represented by one running thread. In your program you can choose a concrete number for N or ask the user to specify N at the start.
-
The way guest will let the Minotaur know that every guest without them talking with each other is by reaching an agreement:
- First guest is the leader and he will not eat the cake until every other guest eats it
- Each guest can only eat the cake once
- Each time the leader comes to the labyrinth, he checks if the cake is present(if it's not, that means one of the guests ate the cake). That means, each time there's no cake, he orders a new cake and memorizes the number of times the cake was not present.
- Well, guests know how many of them would participate, so if the leader notices that he's the only one who have not eaten the cake (the number of time cake was not present == number of guests - 1), then he now can happily eat the cake and end the game.

## How to run the program:
- Go to the project folder (replace project-folder-name with the path to project folder)
```
cd project-folder-name
```
- Compile and Run the code
```
javac birthday.java
java birthday.java
```
- Finally, Follow the program's instructions

# Problem 2: Minotaur’s Crystal Vase (50 points)

The Minotaur decided to show his favorite crystal vase to his guests in a dedicated showroom with a single door. He did not want many guests to gather around the vase and accidentally break it. For this reason, he would allow only one guest at a time into the showroom. He asked his guests to choose from one of three possible strategies for viewing the Minotaur’s favorite crystal vase:

1) Any guest could stop by and check whether the showroom’s door is open at any time and try to enter the room. While this would allow the guests to roam around the castle and enjoy the party, this strategy may also cause large crowds of eager guests to gather around the door. A particular guest wanting to see the vase would also have no guarantee that she or he will be able to do so and when.

2) The Minotaur’s second strategy allowed the guests to place a sign on the door indicating when the showroom is available. The sign would read “AVAILABLE” or “BUSY.” Every guest is responsible to set the sign to “BUSY” when entering the showroom and back to “AVAILABLE” upon exit. That way guests would not bother trying to go to the showroom if it is not available.

3) The third strategy would allow the quests to line in a queue. Every guest exiting the room was responsible to notify the guest standing in front of the queue that the showroom is available. Guests were allowed to queue multiple times.

Which of these three strategies should the guests choose? Please discuss the advantages and disadvantages.

Implement the strategy/protocol of your choice where each guest is represented by 1 running thread. You can choose a concrete number for the number of guests or ask the user to specify it at the start.
-
For this problem I have chosen the third approach to allow the guests to line in a queue with every guest exiting the room being responsible to notify the first guest in the queue that the room is available and he can enter. I think the guests would have chosen this strategy because it sounds fair because they would have a queue to enter and they will successfully meet the requirement the Minotaur set for them, which is 1 guest limit in the room. I don't see any disatvantages with that and also it is the best approach out of 3 approaches we have, since it gives the chance for every guest to enter at least once per 1 lap.

- There is one thing in my approach. The party will be over once each guest checked out the vase at least once. Since the guests go to queue randomly, some guests could visit the room multiple times, but it would be fair if each guest visits it *at least* once. Once we know that every guest visited the room once, we wait till the queue finishes and do not let other guests join the queue(with some exceptions). 

## How to run the program:
- Go to the project folder (replace project-folder-name with the path to project folder)
```
cd project-folder-name
```
- Compile and Run the code
```
javac vase.java
java vase.java
```
- Finally, Follow the program's instructions
