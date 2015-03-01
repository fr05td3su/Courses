"""
Clone of 2048 game.
"""

import poc_2048_gui
from random import randrange
# Directions, DO NOT MODIFY
UP = 1
DOWN = 2
LEFT = 3
RIGHT = 4

# Offsets for computing tile indices in each direction.
# DO NOT MODIFY this dictionary.
OFFSETS = {UP: (1, 0),
           DOWN: (-1, 0),
           LEFT: (0, 1),
           RIGHT: (0, -1)}

def merge(line):
    """
    Function that merges a single row or column in 2048.
    """
    new_line  = [x for x in line if x != 0]
    for idx in range(len(new_line)-1):
        if new_line[idx] == new_line[idx+1]:
            new_line[idx] *= 2
            new_line[idx+1] = 0
    result = [x for x in new_line if x != 0] 
    while len(result) != len(line): 
        result.append(0)   
    return result

class TwentyFortyEight:
    """
    Class to run the game logic.
    """

    def __init__(self, grid_height, grid_width):
        self._grid_height = grid_height
        self._grid_width  = grid_width
        self.reset()
                

        self._initial_tiles = {UP   : [(0,dummy_char) for dummy_char in range(grid_width)],
                              DOWN : [(grid_height-1, dummy_char) for dummy_char in range(grid_width)],
                              LEFT : [(dummy_char, 0) for dummy_char in range(grid_height)],
                              RIGHT: [(dummy_char, grid_width-1) for dummy_char in range(grid_height)]
                              }

    def reset(self):
        """
        Reset the game so the grid is empty except for two
        initial tiles.
        """
        self._grid = [[0 for dummy_x in range(self._grid_width)] 
                        for dummy_y in range(self._grid_height)]
        self.new_tile()
        self.new_tile()
        
    def __str__(self):
        """
        Return a string representation of the grid for debugging.
        """
        string_grid = ""
        for line in self._grid:
            string_grid += str(line)
        return string_grid
            

    def get_grid_height(self):
        """
        Get the height of the board.
        """
        return self._grid_height

    def get_grid_width(self):
        """
        Get the width of the board.
        """
        return self._grid_width

    def move(self, direction):
        """
        Move all tiles in the given direction and add
        a new tile if any tiles moved.
        """        
        changed = False
        for side_tile in self._initial_tiles[direction]:
            if direction == UP or direction == DOWN:
                over_range = self._grid_height
            elif direction == LEFT or direction == RIGHT:
                over_range = self._grid_width
            else:
                print "Error, incorrect direction!"
            operate_tiles = []
            for idx in range(over_range):
                operate_tiles.append(self.get_tile(side_tile[0] + OFFSETS[direction][0] * idx, side_tile[1] + OFFSETS[direction][1] * idx))
            new_tiles = merge(operate_tiles)
            if new_tiles != operate_tiles:
                changed = True
            for idx in range(over_range):
                self._grid[side_tile[0] + OFFSETS[direction][0] * idx][side_tile[1] + OFFSETS[direction][1] * idx] = new_tiles[idx]
        if changed ==True:
            self.new_tile()

    def new_tile(self):
        """
        Create a new tile in a randomly selected empty
        square.  The tile should be 2 90% of the time and
        4 10% of the time.
        """
        while True:  
            column = randrange(self._grid_width)
            row = randrange(self._grid_height)
            if self._grid[row][column] == 0:
                self._grid[row][column] = 2 if randrange(100) <= 90 else 4
                return

    def set_tile(self, row, col, value):
        """
        Set the tile at position row, col to have the given value.
        """
        self._grid[row][col] = value

    def get_tile(self, row, col):
        """
        Return the value of the tile at position row, col.
        """
        return self._grid[row][col]

poc_2048_gui.run_gui(TwentyFortyEight(4, 4))
