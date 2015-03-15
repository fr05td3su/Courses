"""
Cookie Clicker Simulator
"""

import simpleplot
import math

# Used to increase the timeout, if necessary
import codeskulptor
codeskulptor.set_timeout(20)

import poc_clicker_provided as provided

# Constants
SIM_TIME = 10000000000.0

class ClickerState:
    """
    Simple class to keep track of the game state.
    """
    
    def __init__(self):
        self._total_amount_of_cookies = 0.0
        self._current_amount_of_cookies = 0.0
        self._current_time = 0.0
        self._current_cps = 1.0
        self._history = [(0.0, None, 0.0, 0.0)]
        
    def __str__(self):
        """
        Return human readable state
        """
        return "Time: " + str(self._current_time) + " Current Cookies: " + str(self._current_amount_of_cookies) + " CPS: " + str(self._current_cps) + " Total Cookies: " + str(self._total_amount_of_cookies) + " History (length: " + str(len(self._history)) + "): " + str(self._history)

    def get_cookies(self):
        """
        Return current number of cookies 
        (not total number of cookies)
        
        Should return a float
        """
        return self._current_amount_of_cookies
    
    def get_cps(self):
        """
        Get current CPS

        Should return a float
        """
        return self._current_cps
    
    def get_time(self):
        """
        Get current time

        Should return a float
        """
        return self._current_time
    
    def get_history(self):
        """
        Return history list

        History list should be a list of tuples of the form:
        (time, item, cost of item, total cookies)

        For example: [(0.0, None, 0.0, 0.0)]

        Should return a copy of any internal data structures,
        so that they will not be modified outside of the class.
        """
        return self._history

    def time_until(self, cookies):
        """
        Return time until you have the given number of cookies
        (could be 0.0 if you already have enough cookies)

        Should return a float with no fractional part
        """
        return 0.0 if self._current_amount_of_cookies >= cookies else math.ceil((cookies - self._current_amount_of_cookies) / self._current_cps)
    
    def wait(self, time):
        """
        Wait for given amount of time and update state

        Should do nothing if time <= 0.0
        """
        if time <= 0:
            return
        self._current_time += time
        self._current_amount_of_cookies += time * self._current_cps
        self._total_amount_of_cookies += time * self._current_cps
    
    def buy_item(self, item_name, cost, additional_cps):
        """
        Buy an item and update state

        Should do nothing if you cannot afford the item
        """
        if cost > self._current_amount_of_cookies:
            pass
        else:
            self._current_amount_of_cookies -= cost
            self._current_cps += additional_cps
            self._history.append((self._current_time, item_name, cost, self._total_amount_of_cookies)) 

def simulate_clicker(build_info, duration, strategy):
    """
    Function to run a Cookie Clicker game for the given
    duration with the given strategy.  Returns a ClickerState
    object corresponding to the final state of the game.
    """

    build_info_clone = build_info.clone()
    clicker_state_obj = ClickerState()
    cont = ""
    while clicker_state_obj.get_time() <= duration and cont != None:
        cont = strategy(clicker_state_obj.get_cookies(), clicker_state_obj.get_cps(), clicker_state_obj.get_history(), duration-clicker_state_obj.get_time(), build_info_clone)
        if cont != None:
            time_wait = clicker_state_obj.time_until(build_info_clone.get_cost(cont))
            if time_wait <= duration-clicker_state_obj.get_time():
                clicker_state_obj.wait(time_wait)
                clicker_state_obj.buy_item(cont, build_info_clone.get_cost(cont), build_info_clone.get_cps(cont))
                build_info_clone.update_item(cont)
            else:
                cont = None
        if clicker_state_obj.get_time() <= duration and cont == None:
            clicker_state_obj.wait(duration - clicker_state_obj.get_time())   
    return clicker_state_obj


def strategy_cursor_broken(cookies, cps, history, time_left, build_info):
    """
    Always pick Cursor!

    Note that this simplistic (and broken) strategy does not properly
    check whether it can actually buy a Cursor in the time left.  Your
    simulate_clicker function must be able to deal with such broken
    strategies.  Further, your strategy functions must correctly check
    if you can buy the item in the time left and return None if you
    can't.
    """
    return "Cursor"

def strategy_none(cookies, cps, history, time_left, build_info):
    """
    Always return None

    This is a pointless strategy that will never buy anything, but
    that you can use to help debug your simulate_clicker function.
    """
    return None

def strategy_cheap(cookies, cps, history, time_left, build_info):
    """
    Always buy the cheapest item you can afford in the time left.
    """
    cookies_left = cookies + time_left * cps
    cheap_name = ""
    build_info_clone = build_info.clone()
    build_items = build_info_clone.build_items()
    cheap_price = build_info_clone.get_cost(build_items[len(build_items)-1])
    for dummy_index in build_items:
        build_cost = build_info_clone.get_cost(dummy_index)
        if build_cost <= cheap_price:
            cheap_price = build_cost
            cheap_name = dummy_index
    return cheap_name if cookies_left >= cheap_price else None

def strategy_expensive(cookies, cps, history, time_left, build_info):
    """
    Always buy the most expensive item you can afford in the time left.
    """
    chosen = False
    cookies_left = cookies + time_left * cps
    build_info_clone = build_info.clone()
    build_items = build_info_clone.build_items()
    exp_name = build_items[0]
    for dummy_index in build_items:
        index_cost = build_info_clone.get_cost(dummy_index)
        exp_price = build_info_clone.get_cost(exp_name)
        if cookies_left >= index_cost and index_cost >= exp_price:
            exp_name = dummy_index
            chosen = True
    return exp_name if chosen else None

def strategy_best(cookies, cps, history, time_left, build_info):
    """
    The best strategy that you are able to implement.
    """
    chosen = False
    cookies_left = cookies + time_left * cps
    build_info_clone = build_info.clone()
    build_items = build_info_clone.build_items()
    best_name = build_items[0]
    max_cps_p = 0
    for dummy_index in build_items:
        index_cost = build_info_clone.get_cost(dummy_index)
        best_cps = build_info_clone.get_cps(dummy_index)
        if cookies_left*0.9 >= index_cost and max_cps_p < best_cps / index_cost:
            max_cps_p = best_cps/index_cost
            best_name = dummy_index
            chosen = True
    return best_name if chosen else None 
        
def run_strategy(strategy_name, time, strategy):
    """
    Run a simulation for the given time with one strategy.
    """
    state = simulate_clicker(provided.BuildInfo(), time, strategy)
    print strategy_name, ":", state

    # Plot total cookies over time

    # Uncomment out the lines below to see a plot of total cookies vs. time
    # Be sure to allow popups, if you do want to see it

    #history = state.get_history()
    #history = [(item[0], item[3]) for item in history]
    #simpleplot.plot_lines(strategy_name, 1000, 400, 'Time', 'Total Cookies', [history], True)

def run():
    """
    Run the simulator.
    """    
    run_strategy("Cursor", SIM_TIME, strategy_cursor_broken)

    # Add calls to run_strategy to run additional strategies
    #run_strategy("Cheap", SIM_TIME, strategy_cheap)
    #run_strategy("Expensive", SIM_TIME, strategy_expensive)
    #run_strategy("Best", SIM_TIME, strategy_best)
    
run()
    

