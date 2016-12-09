The source files are given (ProjectInput.java, Movie.java) they can be run in a compiler (dont forget to drop the sprites in the right location) or you can run
the game with Start.bat(or Project.jar for Mac)

libraries used that are outside of the Java standard library
-----ProjectInput.java-----
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
-----Animation.java-----
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;
-----Sprite.java-----
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.*;

Use the arrow keys to move the squares around in order to solve the puzzle.
You should see some confetti once the puzzle is solved.

(only issue I observed was very slight lag time when the puzzle is solved
when the background changes)
