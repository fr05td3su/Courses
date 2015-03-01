"""
Monte Carlo Tic-Tac-Toe Player
"""

import random
import poc_ttt_gui
import poc_ttt_provided as provided

# Constants for Monte Carlo simulator
NTRIALS = 10        # Number of trials to run
SCORE_CURRENT = 1.0 # Score for squares played by the current player
SCORE_OTHER = 1.0   # Score for squares played by the other player

def mc_trial(board, player): 
    """
    Plays a game on the given board by choosing squares
    from the list of empty squares randomly. Returns nothing, modifies
    the given board.
    """
    while not(board.check_win()):
        empty_squares = board.get_empty_squares()
        choice = random.choice(empty_squares)
        board.move(choice[0], choice[1], player)
        player = provided.switch_player(player)
    return 

def mc_update_scores(scores, board, player): 
    """
    Calculates scores for the given board and modifies
    the given scores list of lists.
    """
    dim = board.get_dim()
    winner = board.check_win()
    if winner == provided.PLAYERX or winner == provided.PLAYERO:
        for row in range(dim):
            for col in range(dim):
                if board.square(row,col) == winner:
                    scores[row][col] += SCORE_CURRENT
                elif board.square(row,col) == provided.EMPTY:
                    scores[row][col] += 0
                else:
                    scores[row][col] -= SCORE_OTHER
    return

def get_best_move(board, scores):  
    """
    Returns randomly chosen move(empty square) from calculated list of
    moves with maximum score
    """
    empty_squares = board.get_empty_squares() 
    scores_dict = dict((scores[x][y], (x, y)) for x, y in empty_squares)
    max_scores = [scores_dict[key] for key in scores_dict.keys() if key == max(scores_dict.keys())]
    return random.choice(max_scores)

def mc_move(board, player, trials): 
    """
    Takes a current board, which player the machine player is, and the 
    number of trials to run. Returns a move for the machine player in 
    the form of a (row, column) tuple. 
    """
    dim = board.get_dim()
    scores = [[0 for dummy_row in range(dim)] for dummy_col in range(dim)]
    for dummy_counter in range(trials):
        current = board.clone()
        mc_trial(current,player)
        mc_update_scores(scores,current,player)
    return get_best_move(board, scores)

#provided.play_game(mc_move, NTRIALS, False)        
#poc_ttt_gui.run_gui(3, provided.PLAYERX, mc_move, NTRIALS, False)
